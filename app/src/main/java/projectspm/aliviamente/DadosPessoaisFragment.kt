package projectspm.aliviamente

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener


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
        val nome = view.findViewById<TextView>(R.id.texto_nome)
        val email = view.findViewById<TextView>(R.id.texto_email)
        val senha = view.findViewById<TextView>(R.id.texto_password)
        val confirmarSenha = view.findViewById<TextView>(R.id.texto_conf_password)
        val sharedPreferences = activity?.getSharedPreferences("aliviamente", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString("nome_registro", nome.text.toString())
        editor?.putString("email_registro", email.text.toString())
        editor?.putString("senha_registro", senha.text.toString())
        editor?.putString("confirmar_senha_registro", confirmarSenha.text.toString())
        editor?.apply()


        verifyPassword(view)
        verifyFields(view)
        nome.addTextChangedListener { verifyFields(view) }
        email.addTextChangedListener { verifyFields(view) }
        senha.addTextChangedListener { verifyFields(view) }
        confirmarSenha.addTextChangedListener { verifyFields(view) }
        confirmarSenha.addTextChangedListener { verifyPassword(view) }




        btn_continuar.setOnClickListener {
            (activity as? RegistoActivity)?.replaceFragment(DadosEnderecoFragment())

        }




        return view

    }

    private fun verifyFields(view: View) {
        val nome = view.findViewById<TextView>(R.id.texto_nome)
        val email = view.findViewById<TextView>(R.id.texto_email)
        val senha = view.findViewById<TextView>(R.id.texto_password)
        val confirmarSenha = view.findViewById<TextView>(R.id.texto_conf_password)




        val btn_continuar = view.findViewById<Button>(R.id.btn_continuar)

        if (nome.text.isNotEmpty() && email.text.isNotEmpty() && senha.text.isNotEmpty() && confirmarSenha.text.isNotEmpty()) {
            btn_continuar.visibility = View.VISIBLE
        } else {
            btn_continuar.visibility = View.GONE
        }

    }

    private fun verifyPassword(view: View) {
        val senha = view.findViewById<TextView>(R.id.texto_password)
        val confirmarSenha = view.findViewById<TextView>(R.id.texto_conf_password)
        val btn_continuar = view.findViewById<Button>(R.id.btn_continuar)

        if (confirmarSenha.text.toString() != senha.text.toString()) {
            Toast.makeText(requireContext(), "As senhas não se coincidem", Toast.LENGTH_SHORT).show()
            btn_continuar.isEnabled = false
        } else {
            btn_continuar.isEnabled = true
        }
    }


}