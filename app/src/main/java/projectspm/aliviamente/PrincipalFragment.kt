package projectspm.aliviamente

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.google.android.material.navigation.NavigationBarItemView
import com.google.android.material.navigation.NavigationView


class PrincipalFragment : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_principal, container, false)

        val card_consulta:CardView = view.findViewById(R.id.consultas_card)
        card_consulta.setOnClickListener{
            val intent = Intent(context, ConsultaActivity::class.java)
            startActivity(intent)

        }

        val meu_perfil_card:CardView = view.findViewById(R.id.meu_perfil_card)
        meu_perfil_card.setOnClickListener{
            navigateToPerfil()
        }

        val sos_card:CardView = view.findViewById(R.id.sos_card)
        sos_card.setOnClickListener{
            navigateToSos()
        }


        return view
    }

    private fun navigateToPerfil() {
        val meu_perfil_fragment = MeuPerfilFragment()

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.placeholder, meu_perfil_fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToSos() {
        val sos_fragment = SOSFragment()

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.placeholder, sos_fragment)
            .addToBackStack(null)
            .commit()
    }







}