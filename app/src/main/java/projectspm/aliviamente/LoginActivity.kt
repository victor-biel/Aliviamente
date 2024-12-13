package projectspm.aliviamente

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import projectspm.aliviamente.databinding.ActivityLoginBinding
import java.nio.charset.Charset

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    fun doLogin(view: View) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g15/PAW/api/index.php"


        val postRequest = object : StringRequest(
            Method.POST, url,
            { response ->
                try {
                    val jsonResponse = JSONObject(response)
                    val status = jsonResponse.getString("status")
                    val message = jsonResponse.getString("message")
                    if (status == "SUCCESS") {
                        if (binding.checkBox.isChecked) {
                            //guardar sharedPreference
                            getSharedPreferences("aliviamente", Context.MODE_PRIVATE)
                                .edit()
                                .putBoolean("login", true)
                                .putString("email", binding.email.text.toString())
                                .apply()
                        }
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                }catch (e:JSONException) {
                    Toast.makeText(this, "Erro ao processar a resposta: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Erro: ${error.message}", Toast.LENGTH_SHORT).show()
            }) {

            override fun getBody(): ByteArray {
                val jsonParams = JSONObject()
                jsonParams.put("email", binding.email.text.toString())
                jsonParams.put("password", binding.password.text.toString())
                return jsonParams.toString().toByteArray(Charsets.UTF_8)
            }

            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json; charset=utf-8"
                return headers
            }


        }

// Add the request to the RequestQueue.
        queue.add(postRequest)
    }
}

