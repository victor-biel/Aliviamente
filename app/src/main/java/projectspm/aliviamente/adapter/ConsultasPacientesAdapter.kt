package projectspm.aliviamente.adapter

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import projectspm.aliviamente.DetalhesPacientesActivity
import projectspm.aliviamente.NovaConsultaActivity
import projectspm.aliviamente.R
import projectspm.aliviamente.model.Consulta


class ConsultasPacientesAdapter(val consultas_pacientes: List<Consulta>): RecyclerView.Adapter<ConsultasPacientesAdapter.ConsultaPacientesViewHolder>() {

    class ConsultaPacientesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val card_consulta_i_c_p = itemView.findViewById<CardView>(R.id.card_consulta_i_c_p)
        val data_consulta_i_c_p = itemView.findViewById<TextView>(R.id.data_consulta_i_c_p)
        val hora_consulta_i_c_p = itemView.findViewById<TextView>(R.id.hora_consulta_i_c_p)
        val txt_status = itemView.findViewById<TextView>(R.id.status_i_c_p)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsultaPacientesViewHolder {

        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_consulta_paciente, parent, false)
        return ConsultaPacientesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConsultaPacientesViewHolder, position: Int) {
        val cons = consultas_pacientes[position]
        holder.data_consulta_i_c_p.text = cons.data_consulta
        holder.hora_consulta_i_c_p.text = cons.hora_consulta
        holder.txt_status.text = cons.status


        val radius = GradientDrawable()
        radius.cornerRadius = 16f
        radius.setColor(when (cons.status) {
            "Pendente" -> Color.parseColor("#E4C286")
            "Aceita" -> Color.parseColor("#3884d9")
            "Realizada" -> Color.parseColor("#A3D977")
            "Cancelada" -> Color.parseColor("#8e2626")
            else -> Color.TRANSPARENT
        })
        holder.txt_status.background = radius

        holder.card_consulta_i_c_p.setOnClickListener{
            val context = holder.card_consulta_i_c_p.context
            val intent = Intent(context, DetalhesPacientesActivity::class.java)
            intent.putExtra("nome", cons.nome_paciente)
            intent.putExtra("id_consulta", cons.id_consulta)
            context.startActivity(intent)
        }

    }
    override fun getItemCount(): Int {
        return consultas_pacientes.size
    }


}