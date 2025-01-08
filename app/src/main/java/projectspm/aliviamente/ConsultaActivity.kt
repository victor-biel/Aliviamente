package projectspm.aliviamente

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
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

        binding.btnVoltar.setOnClickListener {
            finish()
        }

        binding.btnSolicitarConsulta.setOnClickListener {
            goNovaConsulta(it)
        }

        binding.recyclerViewConsulta.layoutManager =
            LinearLayoutManager(this)

        getConsultasInfo()


    }





    private fun getConsultasInfo () {
        val sharedPreferences = getSharedPreferences("aliviamente", Context.MODE_PRIVATE)
        val id_paciente = sharedPreferences.getInt("id", -1)


        if (id_paciente == -1) {
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
                    if (consultas.any{it.status == "Aceita" || it.status == "Realizada"} || consultas.size > 1 ) {
                        binding.btnSolicitarConsulta.visibility = View.GONE
                    } else {
                        binding.btnSolicitarConsulta.visibility = View.VISIBLE
                    }


                } else {
                    binding.recyclerViewConsulta.visibility = View.GONE
                    binding.textViewEmpty.visibility = View.VISIBLE
                }
        },
        onError =  {errorMessage ->
            Log.e("Error", errorMessage)
        })
    }

    private fun goMyProfile() {

    }

    fun goNovaConsulta (view: View) {
        val intent = Intent(this, NovaConsultaActivity::class.java)
        startActivity(intent)
    }


}