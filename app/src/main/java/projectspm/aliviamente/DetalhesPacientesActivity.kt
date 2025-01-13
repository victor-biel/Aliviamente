package projectspm.aliviamente

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import projectspm.aliviamente.databinding.ActivityDetalhesPacientesBinding

class DetalhesPacientesActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetalhesPacientesBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnVoltarDPaciente.setOnClickListener {
            finish()
        }

        val id = intent.getIntExtra("id", -1)
        val nome = intent.getStringExtra("nome")
        val id_consulta = intent.getIntExtra("id_consulta", -1).toString()

        binding.textNomePaciente.text = nome + " " + id_consulta
        if (nome == "") {
            binding.textNomePaciente.visibility = View.GONE
        }else {
            binding.textNomePaciente.text = nome
        }


        binding.cardIniciarChamada.setOnClickListener {
            val intent = Intent(this, VideoChamadaActivity::class.java)
            intent.putExtra("channelName", id_consulta)
            intent.putExtra("id", id)
            Log.d("DetalhesPacientesActivity", "id: $id, id_consulta: $id_consulta")
            startActivity(intent)
        }
    }
}