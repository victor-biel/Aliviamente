package projectspm.aliviamente

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import projectspm.aliviamente.databinding.ActivityHomeDoctorBinding

class HomeDoctorActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityHomeDoctorBinding.inflate(layoutInflater)
    }

    private var f1: Fragment? = null
    private var f2: Fragment? = null
    private var f3: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        val nome = getSharedPreferences("aliviamente", Context.MODE_PRIVATE)
            .getString("nome", "Usuário desconhecinho")

        binding.textoNome.setText(nome)

        if (savedInstanceState == null) {
            f1 = PrincipalDoctorFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.placeholder_doctor, f1!!)
                .commit()
        }


        val navigation = binding.navigationDoctor

        navigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_principal_doctor -> {
                    if (f1 == null) f1 = PrincipalDoctorFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.placeholder_doctor, f1!!)
                        .commit()
                    true
                }

                R.id.menu_sos_doctor -> {
                    if (f2 == null) f2 = SOSFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.placeholder_doctor, f2!!)
                        .commit()
                    true
                }

                R.id.menu_perfil_doctor -> {
                    if (f3 == null) f3 = MeuPerfilFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.placeholder_doctor, f3!!)
                        .commit()
                    true
                }else -> false

            }
        }


    }
}