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
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
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

        val viewModel = ViewModelProvider(requireActivity()).get(CadastroViewModel::class.java)

        val dt_nasc = view.findViewById<EditText>(R.id.data_nascimento)

        dt_nasc.setOnClickListener{
            val calendar = Calendar.getInstance()

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->

                    val monthString = if (month + 1 < 10) "0${month + 1}" else "${month + 1}"
                    val dayString = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                    val selectedDate = "$year-$monthString-$dayString"
                    Log.d("Data selecionada: ", selectedDate)
                    dt_nasc.setText(selectedDate)
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
                if (validateFields(view)) {

                    doRegister(view)
                }else {
                    Toast.makeText(requireContext(), "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
                }

            }else {
                viewModel.dataNascimento.value = dt_nasc.text.toString()
                viewModel.morada.value = view.findViewById<EditText>(R.id.texto_morada).text.toString()
                viewModel.codigoPostal.value = view.findViewById<EditText>(R.id.cod_postal).text.toString()
                viewModel.tipoUsuario.value = when (view.findViewById<RadioGroup>(R.id.radio_group).checkedRadioButtonId) {
                    R.id.radio_psicologo -> "Psicólogo"
                    R.id.radio_psiquiatra -> "Psiquiatra"
                    else -> "Paciente"
                }
                (activity as? RegistoActivity)?.replaceFragment(DadosArquivoFragment())
            }


        }

        return view
    }
    private fun validateFields(view: View): Boolean {
        val texto_morada = view.findViewById<EditText>(R.id.texto_morada)
        val cod_postal = view.findViewById<EditText>(R.id.cod_postal)
        val dt_nascimento = view.findViewById<TextView>(R.id.data_nascimento)

        return texto_morada.text.isNotEmpty() && cod_postal.text.isNotEmpty() && dt_nascimento.text.isNotEmpty()
    }


    fun doRegister(view: View) {
        val texto_morada = view.findViewById<EditText>(R.id.texto_morada)
        val cod_postal = view.findViewById<EditText>(R.id.cod_postal)
        val dt_nascimento = view.findViewById<EditText>(R.id.data_nascimento)
        println("Data de nascimento: ${dt_nascimento.text}")
        val radio_group = view.findViewById<RadioGroup>(R.id.radio_group)
        val tipo_user = when (radio_group.checkedRadioButtonId) {
            R.id.radio_paciente -> "Paciente"
            R.id.radio_psicologo -> "Psicólogo"
            R.id.radio_psiquiatra -> "Psiquiatra"
            else -> "Paciente"
        }

        val viewModel = ViewModelProvider(requireActivity()).get(CadastroViewModel::class.java)

        val apiService = ApiService(requireContext())
        if (tipo_user == "Paciente") {
            apiService.doRegister(
                viewModel.nome.value.toString(),
                viewModel.email.value.toString(),
                viewModel.senha.value.toString(),
                viewModel.confirmarSenha.value.toString(),
                dt_nascimento.text.toString(),
                texto_morada.text.toString(),
                cod_postal.text.toString(),
                tipo_user,
                cedula_profissional = null, { jsonResponse ->
                    Log.e("SUCCESS", jsonResponse.toString())
                    Toast.makeText(requireContext(), "Registo realizado com sucesso", Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)

            }, onError =
                { errorMessage ->
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            })
        }


    }




}



