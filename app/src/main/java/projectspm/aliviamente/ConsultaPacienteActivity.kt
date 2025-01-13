package projectspm.aliviamente

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import projectspm.aliviamente.adapter.ConsultasAdapter
import projectspm.aliviamente.adapter.ConsultasPacientesAdapter
import projectspm.aliviamente.databinding.ActivityConsultaPacienteBinding

class ConsultaPacienteActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityConsultaPacienteBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val btn_voltar = binding.btnVoltarCP
        btn_voltar.setOnClickListener {
            finish()
        }

        binding.recyclerViewCP.layoutManager =
            LinearLayoutManager(this)

        getConsultasP()

    }

    private fun getConsultasP () {

        val id = intent.getIntExtra("id", -1)

        val apiService = ApiService(this)
        apiService.getConsultasPaciente(id,
            onSuccess = { consultas ->
                if (consultas.isNotEmpty()) {
                    val adapter = ConsultasPacientesAdapter(consultas)
                    binding.recyclerViewCP.adapter = adapter

                }else {
                    Toast.makeText(this, "Nenhuma consulta encontrada", Toast.LENGTH_SHORT).show()
                    Log.d("Consultas", "Nenhuma consulta encontrada.")
                }
            }, onError = { errorMessage ->
                Log.e("Error", "Erro ao buscar consultas: $errorMessage")
            })
    }
}