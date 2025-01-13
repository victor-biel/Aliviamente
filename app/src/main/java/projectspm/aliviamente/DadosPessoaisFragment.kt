package projectspm.aliviamente

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider


class DadosPessoaisFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        val view =  inflater.inflate(R.layout.fragment_dados_pessoais, container, false)

        val viewModel = ViewModelProvider(requireActivity()).get(CadastroViewModel::class.java)

        val btn_continuar = view.findViewById<Button>(R.id.btn_continuar)
        val nome = view.findViewById<EditText>(R.id.texto_nome)
        val email = view.findViewById<EditText>(R.id.texto_email)
        val senha = view.findViewById<EditText>(R.id.texto_password)
        val confirmarSenha = view.findViewById<EditText>(R.id.texto_conf_password)
        val btn_voltar = view.findViewById<Button>(R.id.btn_voltar_f_c)


        btn_voltar.setOnClickListener {
            (activity as? RegistoActivity)?.finish()
        }


        verifyFields(view)


        nome.addTextChangedListener { verifyFields(view) }
        email.addTextChangedListener { verifyFields(view) }
        senha.addTextChangedListener { verifyFields(view) }
        confirmarSenha.addTextChangedListener {
            verifyFields(view)

        }



        btn_continuar.setOnClickListener {
            if (!verifyPassword(view)) return@setOnClickListener
            (activity as? RegistoActivity)?.replaceFragment(DadosEnderecoFragment())

            viewModel.nome.value = nome.text.toString()
            viewModel.email.value = email.text.toString()
            viewModel.senha.value = senha.text.toString()
            viewModel.confirmarSenha.value = confirmarSenha.text.toString()


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

    private fun verifyPassword(view: View): Boolean {
        val senha = view.findViewById<TextView>(R.id.texto_password)
        val confirmarSenha = view.findViewById<TextView>(R.id.texto_conf_password)
        val btn_continuar = view.findViewById<Button>(R.id.btn_continuar)

        if (confirmarSenha.text.toString() != senha.text.toString()) {
            Toast.makeText(requireContext(), "As senhas não se coincidem", Toast.LENGTH_SHORT).show()
            return false

        } else {
            return true
            btn_continuar.isEnabled = true
        }
    }


}