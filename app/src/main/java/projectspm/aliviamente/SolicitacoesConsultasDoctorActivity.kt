package projectspm.aliviamente

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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

    private lateinit var adapter: SolicitacoesConsultasAdapter

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
                    if (!::adapter.isInitialized) {
                        adapter = SolicitacoesConsultasAdapter(consultas) { id_pedido ->
                            aprovarConsulta(id_pedido)
                        }
                        binding.recyclerViewSoliConsDoctor.adapter = adapter
                    } else {

                        adapter.updateConsultas(consultas)
                    }

                } else {
                    binding.recyclerViewSoliConsDoctor.visibility = View.GONE
                    binding.textViewEmptyDoctor.visibility = View.VISIBLE
                }
            },
            onError =  {errorMessage ->
                Log.e("Error", errorMessage)
            })
    }

    private fun aprovarConsulta(id_pedido: Int) {
        val apiService = ApiService(this)

        val sharedPreferences = getSharedPreferences("aliviamente", Context.MODE_PRIVATE)
        val id_user = sharedPreferences.getInt("id", -1)

        apiService.approved(id_pedido, id_user,
            onSuccess = {
                Toast.makeText(this, "Solicitação aceita", Toast.LENGTH_SHORT).show()

                getConsultas()

            },
            onError = {errorMessage ->
                Log.e("Error", errorMessage)
                Toast.makeText(this, "Erro ao aprovar: $errorMessage", Toast.LENGTH_SHORT).show()
            })
    }
}