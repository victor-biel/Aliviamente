package projectspm.aliviamente

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import projectspm.aliviamente.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
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
            f1 = PrincipalFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.placeholder, f1!!)
                .commit()
        }



        f3 = MeuPerfilFragment()

        val navigation = binding.navigation

        navigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_principal -> {
                    if (f1 == null) f1 = PrincipalFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.placeholder, f1!!)
                        .commit()
                    true
                }

                R.id.menu_sos -> {
                    if (f2 == null) f2 = SOSFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.placeholder, f2!!)
                        .commit()
                    true
                }

                R.id.menu_perfil -> {
                    if (f3 == null) f3 = MeuPerfilFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.placeholder, f3!!)
                        .commit()
                    true
                }else -> false

            }
        }
    }
}