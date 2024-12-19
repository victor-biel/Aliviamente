package projectspm.aliviamente.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import projectspm.aliviamente.R
import projectspm.aliviamente.model.Consulta
import kotlin.contracts.contract

class SolicitacoesConsultasAdapter (val solitacoes_consultas: List<Consulta>):RecyclerView.Adapter<SolicitacoesConsultasAdapter.SoliConsultasViewHolder>() {

    class SoliConsultasViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val txt_nome_paciente_doctor = itemView.findViewById<TextView>(R.id.nome_paciente_doctor)
        val txt_data_consulta_doctor = itemView.findViewById<TextView>(R.id.txt_data_consulta_doctor)
        val txt_hora_consulta_doctor = itemView.findViewById<TextView>(R.id.txt_hora_consulta_doctor)
        val txt_status_doctor = itemView.findViewById<TextView>(R.id.txt_status_doctor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoliConsultasViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_solicitacao_consulta, parent, false)
        return SoliConsultasViewHolder(view)
    }

    override fun onBindViewHolder(holder: SoliConsultasViewHolder, position: Int) {
        val soli_cons = solitacoes_consultas[position]
        holder.txt_nome_paciente_doctor.text = soli_cons.nome_paciente
        holder.txt_data_consulta_doctor.text = soli_cons.data_consulta
        holder.txt_hora_consulta_doctor.text = soli_cons.hora_consulta
        holder.txt_status_doctor.text = soli_cons.status
    }

    override fun getItemCount(): Int {
        return solitacoes_consultas.size
    }
}