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
import projectspm.aliviamente.NovaConsultaActivity
import projectspm.aliviamente.R
import projectspm.aliviamente.model.Consulta


class ConsultasAdapter(val consultas: List<Consulta>): RecyclerView.Adapter<ConsultasAdapter.ConsultaViewHolder>() {

    class ConsultaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val card = itemView.findViewById<CardView>(R.id.card_consulta)
        val txt_data_consulta = itemView.findViewById<TextView>(R.id.data_consulta)
        val txt_hora_consulta = itemView.findViewById<TextView>(R.id.hora_consulta)
        val txt_status = itemView.findViewById<TextView>(R.id.status)
        val btn_sol_consulta = itemView.findViewById<TextView>(R.id.btn_solicitar_consulta)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsultaViewHolder {

        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_consulta, parent, false)
        return ConsultaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConsultaViewHolder, position: Int) {
        val cons = consultas[position]
        holder.txt_data_consulta.text = cons.data_consulta
        holder.txt_hora_consulta.text = cons.hora_consulta
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



        //devo fazer código aqui caso tenha clique no card
    }
    override fun getItemCount(): Int {
        return consultas.size
    }


}