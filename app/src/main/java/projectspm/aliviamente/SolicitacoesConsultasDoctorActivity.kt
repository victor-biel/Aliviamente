package projectspm.aliviamente

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import projectspm.aliviamente.adapter.ConsultasAdapter
import projectspm.aliviamente.adapter.SolicitacoesConsultasAdapter
import projectspm.aliviamente.databinding.ActivitySolicitacoesConsultasDoctorBinding

class SolicitacoesConsultasDoctorActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySolicitacoesConsultasDoctorBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.btnVoltarDoctor.setOnClickListener {
            finish()
        }
        binding.recyclerViewSoliConsDoctor.layoutManager =
            LinearLayoutManager(this)

        getConsultas()

    }

    private fun getConsultas () {
        val apiService = ApiService(this)

        val sharedPreferences = getSharedPreferences("aliviamente", Context.MODE_PRIVATE)
        val id = sharedPreferences.getInt("id", -1)

        apiService.getConsultas(id,
            onSuccess = { consultas ->
                Log.d("Consultas", "Número de consultas: ${consultas.size}")
                if (consultas.isNotEmpty()) {
                    val adapter = SolicitacoesConsultasAdapter(consultas)
                    binding.recyclerViewSoliConsDoctor.adapter = adapter
                } else {
                    Log.e("Error", "Nenhuma consulta encontrada.")
                }
            },
            onError =  {errorMessage ->
                Log.e("Error", errorMessage)
            })
    }
}