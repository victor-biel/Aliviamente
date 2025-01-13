package projectspm.aliviamente

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import projectspm.aliviamente.adapter.PacientesAdapter
import projectspm.aliviamente.databinding.ActivityPacientesBinding
import projectspm.aliviamente.model.User

class PacientesActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityPacientesBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerViewPaciente.layoutManager =
            LinearLayoutManager(this)

        binding.btnVoltarIPaciente.setOnClickListener {
            finish()
        }
        getPac()

    }

    private fun getPac() {

        val apiService = ApiService(this)

        val sharedPreferences = getSharedPreferences("aliviamente", Context.MODE_PRIVATE)
        val id_user = sharedPreferences.getInt("id", -1)

        if (id_user == -1) {
            Log.e("Error", "ID do paciente não encontrado.")
            return
        }

        apiService.getPacientes(id_user,
            onSuccess = { pacientes ->
                if (pacientes.isNotEmpty()) {
                    val adapter = PacientesAdapter(pacientes)
                    binding.recyclerViewPaciente.adapter = adapter
                }else {
                    Toast.makeText(this, "Nenhum paciente encontrado", Toast.LENGTH_SHORT).show()
                    Log.d("Pacientes", "Nenhum paciente encontrado.")
                }
        }, onError = { errorMessage ->
            Log.e("Error", "Erro ao buscar pacientes: $errorMessage")
        })
    }
}