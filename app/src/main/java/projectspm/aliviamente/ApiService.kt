package projectspm.aliviamente

import android.content.Context
import android.content.Intent
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.lang.Error

class ApiService (private val context: Context) {

    private val queue:RequestQueue = Volley.newRequestQueue(context)

    fun doLogin(email: String, password: String, onSuccess: (JSONObject)-> Unit, onError: (String) -> Unit) {
        val url = "https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g15/PAW/api/index.php"


        val postRequest = object : StringRequest(
            Method.POST, url,
            { response ->
                try {
                    val jsonResponse = JSONObject(response)
                    val status = jsonResponse.getString("status")
                    val message = jsonResponse.getString("message")


                    if (status == "SUCCESS") {
                        onSuccess(jsonResponse)
                    } else {
                        onError(message)
                    }
                }catch (e: JSONException) {
                    onError("Erro ao processar a resposta: ${e.message}")
                }
            },
            Response.ErrorListener { error ->
                onError("Erro: ${error.message}")
            }) {

            override fun getBody(): ByteArray {
                val jsonParams = JSONObject()
                jsonParams.put("email", email)
                jsonParams.put("password", password)
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