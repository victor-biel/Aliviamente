package projectspm.aliviamente

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import projectspm.aliviamente.databinding.ActivityRegistoBinding

class RegistoActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityRegistoBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)




        if (savedInstanceState == null) {
            replaceFragment(DadosPessoaisFragment())
        }
    }
    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
//            .addToBackStack(null)
            .commit()
    }



}