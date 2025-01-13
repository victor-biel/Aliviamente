package projectspm.aliviamente.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import projectspm.aliviamente.ConsultaPacienteActivity
import projectspm.aliviamente.DetalhesPacientesActivity
import projectspm.aliviamente.R
import projectspm.aliviamente.model.User
import java.time.LocalDate


class PacientesAdapter (val lista_paciente: List<User>):RecyclerView.Adapter<PacientesAdapter.PacientesViewHolder>() {

    class PacientesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val txt_item_nome_paciente = itemView.findViewById<TextView>(R.id.item_nome_paciente)
        val txt_idade = itemView.findViewById<TextView>(R.id.txt_idade)
        val txt_email = itemView.findViewById<TextView>(R.id.txt_email)
        val card_paciente = itemView.findViewById<CardView>(R.id.card_paciente)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PacientesViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_pacientes, parent, false)
        return PacientesViewHolder(view)
    }

    override fun onBindViewHolder(holder: PacientesViewHolder, position: Int) {
        val list_p = lista_paciente[position]
        holder.txt_item_nome_paciente.text = list_p.nome
        holder.txt_idade.text = list_p.idade + " anos"
        holder.txt_email.text = list_p.email

        holder.card_paciente.setOnClickListener{
            val context = holder.card_paciente.context
            val intent = Intent(context, ConsultaPacienteActivity::class.java)
            intent.putExtra("nome", list_p.nome)
            intent.putExtra("id", list_p.id)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return lista_paciente.size
    }




}