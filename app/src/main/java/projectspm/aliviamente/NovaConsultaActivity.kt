package projectspm.aliviamente


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import projectspm.aliviamente.databinding.ActivityNovaConsultaBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NovaConsultaActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityNovaConsultaBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.btnVoltarSoli.setOnClickListener {
            finish()
        }

        val editTextDate = binding.editTextDate
        val editTextTime = binding.editTextTime

        editTextDate.setOnClickListener{
            val calendar = Calendar.getInstance()

            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->

                    if (month < 10) {
                        val monthString = "0${month + 1}"
                        val selectedDate = "${year}-${monthString}-$dayOfMonth"
                        Log.d("Data selecionada: ", selectedDate)
                        Log.d("Type of selectedDate: ", selectedDate.javaClass.name)
                        editTextDate.setText(selectedDate)
                        return@DatePickerDialog
                    }else {
                        val selectedDate = "${year}-${month + 1}-$dayOfMonth"
                        Log.d("Data selecionada: ", selectedDate)
                        Log.d("Type of selectedDate: ", selectedDate.javaClass.name)
                        editTextDate.setText(selectedDate)
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            datePickerDialog.show()
        }

        editTextTime.setOnClickListener{
            val calendar = Calendar.getInstance()

            val timePickerDialog = TimePickerDialog(
                this,
                { _, hourOfDay, minute ->


                    val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                    Log.d("Hora selecionada: ", selectedTime)
                    Log.d("Type of selectedTime: ", selectedTime.javaClass.name)

                    editTextTime.setText(selectedTime)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )
            timePickerDialog.show()
        }




    }

    fun requestConsultationActivity(view: View) {
        val id_user = getSharedPreferences("aliviamente", Context.MODE_PRIVATE)
            .getInt("id", -1)


        val data_consulta = binding.editTextDate.text.toString()
        Log.d("Valor de data da consulta: ", data_consulta)
        val hora_consulta = binding.editTextTime.text.toString()
        Log.d("Valor de hora da consulta: ", hora_consulta)


        if (data_consulta.isEmpty()) {
            Toast.makeText(this, "O campo data da consulta tem que ser preenchido", Toast.LENGTH_SHORT).show()
            return
        }

        if (hora_consulta.isEmpty()) {
            Toast.makeText(this, "O campo hora da consulta tem que ser preenchido", Toast.LENGTH_SHORT).show()
            return
        }

        val apiService = ApiService(this)
        apiService.requestConsultation(data_consulta, hora_consulta, id_user, { jsonResponse ->
            Toast.makeText(this, "Sucesso", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, ConsultaActivity::class.java)
            startActivity(intent)
            finish()
        }, { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        })
    }
}