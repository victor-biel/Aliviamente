package projectspm.aliviamente

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import org.json.JSONException
import org.json.JSONObject
import java.util.Date


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

        val dt_nasc = view.findViewById<TextView>(R.id.data_nascimento)

        dt_nasc.setOnClickListener{
            val calendar = Calendar.getInstance()

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->

                    if (month < 10) {
                        val monthString = "0${month + 1}"
                        val selectedDate = "${year}-${monthString}-$dayOfMonth"
                        Log.d("Data selecionada: ", selectedDate)
                        Log.d("Type of selectedDate: ", selectedDate.javaClass.name)
                        dt_nasc.setText(selectedDate)
                        return@DatePickerDialog
                    }else {
                        val selectedDate = "${year}-${month + 1}-$dayOfMonth"
                        Log.d("Data selecionada: ", selectedDate)
                        Log.d("Type of selectedDate: ", selectedDate.javaClass.name)
                        dt_nasc.setText(selectedDate)
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            datePickerDialog.show()
        }


        val btn_cadastrar = view.findViewById<Button>(R.id.btn_cadastrar)
        val radio_paciente = view.findViewById<RadioButton>(R.id.radio_paciente)


        btn_cadastrar.setOnClickListener {

            if (radio_paciente.isChecked) {
                doRegister(view)
            }else {
                (activity as? RegistoActivity)?.replaceFragment(DadosArquivoFragment())
            }


        }

        return view
    }

    fun doRegister(view: View) {
        val texto_morada = view.findViewById<TextView>(R.id.texto_morada)
        val cod_postal = view.findViewById<TextView>(R.id.cod_postal)
        val dt_nascimento = view.findViewById<TextView>(R.id.data_nascimento)
        val radio_group = view.findViewById<RadioGroup>(R.id.radio_group)
        val tipo_user = when (radio_group.checkedRadioButtonId) {
            R.id.radio_paciente -> "Paciente"
            R.id.radio_psicologo -> "Psicólogo"
            R.id.radio_psiquiatra -> "Psiquiatra"
            else -> "Paciente"
        }

        val sharedPreferences = activity?.getSharedPreferences("aliviamente", Context.MODE_PRIVATE)
        val nome_registro = sharedPreferences?.getString("nome_registro", "Nome") ?: "Nome"
        val email_registro = sharedPreferences?.getString("email_registro", "Email") ?: "Email"
        val senha_registro = sharedPreferences?.getString("senha_registro", "Senha") ?: "Senha"
        val confirmar_senha_registro = sharedPreferences?.getString("confirmar_senha_registro", "Confirmar Senha") ?: "Confirmar Senha"


        val apiService = ApiService(requireContext())
        if (tipo_user == "Paciente") {
            apiService.doRegister(
                nome_registro.toString(),
                email_registro.toString(),
                senha_registro.toString(),
                confirmar_senha_registro.toString(),
                texto_morada.text.toString(),
                cod_postal.text.toString(),
                dt_nascimento.text.toString(),
                tipo_user,
                cedulaProfissional = null, onSuccess =  { jsonResponse ->
                    Log.e("SUCCESS", jsonResponse.toString()) // Verifique o que está sendo retornado


            }, onError =
                { errorMessage ->
                Log.e("Error", errorMessage)
            })
        }


    }




}

