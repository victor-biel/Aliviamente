package projectspm.aliviamente

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton


class DadosEnderecoFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dados_endereco, container, false)

        val btn_cadastrar = view.findViewById<Button>(R.id.btn_cadastrar)
        val radio_paciente = view.findViewById<RadioButton>(R.id.radio_paciente)


        btn_cadastrar.setOnClickListener {

            if (radio_paciente.isChecked) {
                val intent = Intent(context, HomeActivity::class.java)
                startActivity(intent)
            }else {
                (activity as? RegistoActivity)?.replaceFragment(DadosArquivoFragment())
            }


        }

        return view
    }




}

