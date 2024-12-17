package projectspm.aliviamente

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import projectspm.aliviamente.adapter.ConsultasAdapter
import projectspm.aliviamente.databinding.ActivityConsultaBinding

class ConsultaActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityConsultaBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.recyclerViewConsulta.layoutManager =
            LinearLayoutManager(this)

        getConsultasInfo()

    }

    private fun getConsultasInfo () {
        val sharedPreferences = getSharedPreferences("aliviamente", Context.MODE_PRIVATE)
        val id_paciente = sharedPreferences.getInt("id", -1)


        if (id_paciente == -1) {
            // Trate o caso em que o ID do paciente não está disponível
            Log.e("Error", "ID do paciente não encontrado.")
            return
        }
        val apiService = ApiService(this)

        apiService.getConsultasInfo(id_paciente,
            onSuccess = { consultas ->
                Log.d("Consultas", "Número de consultas: ${consultas.size}")
                if (consultas.isNotEmpty()) {
                    val adapter = ConsultasAdapter(consultas)
                    binding.recyclerViewConsulta.adapter = adapter
                } else {
                    Log.e("Error", "Nenhuma consulta encontrada.")
                }
        },
        onError =  {errorMessage ->
            Log.e("Error", errorMessage)
        })
    }
}