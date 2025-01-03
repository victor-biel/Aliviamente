package projectspm.aliviamente

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class DadosPessoaisFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        val view =  inflater.inflate(R.layout.fragment_dados_pessoais, container, false)

        val btn_continuar = view.findViewById<Button>(R.id.btn_continuar)



        btn_continuar.setOnClickListener {
            (activity as? RegistoActivity)?.replaceFragment(DadosEnderecoFragment())
        }

        return view
    }


}