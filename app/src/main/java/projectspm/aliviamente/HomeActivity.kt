package projectspm.aliviamente

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class HomeActivity : AppCompatActivity() {

    var f1: Fragment? = null
    var f2: Fragment? = null
    var f3: Fragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        f1 = ConsultaFragment()
        f2 = SOSFragment()
        f3 = MeuPerfilFragment()
    }
}