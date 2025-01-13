package projectspm.aliviamente

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import projectspm.aliviamente.databinding.ActivityLoginBinding



class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }


    fun doLogin(view: View) {
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        val erroLogin = binding.erroLogin


        val campos = when {
            email.isEmpty() && password.isEmpty() -> erroLogin.setText(R.string.campos_preenchidos)
            email.isEmpty() -> erroLogin.setText(R.string.campo_email)
            password.isEmpty() ->erroLogin.setText(R.string.campo_password)

            else -> null
        }

        if (campos != null) {
            erroLogin.visibility = View.VISIBLE
        }




        val apiService = ApiService(this)
        apiService.doLogin(email, password, { jsonResponse ->

            val nome = jsonResponse.getString("nome")
            val id = jsonResponse.getInt("id")
            val tipo_user = jsonResponse.getString("tipo_user")
            val aprovado = jsonResponse.getInt("aprovado")
            getSharedPreferences("aliviamente", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("login", true)
                .putString("email", email)
                .putString("nome", nome)
                .putString("tipo_user", tipo_user)
                .putInt("id",id)
                .apply()

            if (aprovado == 1) {
                val intent = when {
                    tipo_user == "Paciente" -> Intent(this, HomeActivity::class.java)
                    else -> Intent(this, HomeDoctorActivity::class.java)
                }
                startActivity(intent)
                finish()
            }else {
                Toast.makeText(this, "Conta aguarda aprovação", Toast.LENGTH_SHORT).show()
            }







        }, { errorMessage ->

            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()

        })

    }
}

