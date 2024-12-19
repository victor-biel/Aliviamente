package projectspm.aliviamente

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.HandlerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val sharedPref = getSharedPreferences("aliviamente", Context.MODE_PRIVATE)
        val login = sharedPref.getBoolean("login", false)
        val tipo_user = sharedPref.getString("tipo_user", null)

        HandlerCompat.createAsync(Looper.getMainLooper()).postDelayed({
            val intent = when {
                login && tipo_user == "Paciente" -> Intent(this, HomeActivity::class.java)
                login -> Intent(this, HomeDoctorActivity::class.java)
                else -> Intent(this, MainActivity::class.java)
            }
            startActivity(intent)
            finish()
            }, 3000)
        }
}