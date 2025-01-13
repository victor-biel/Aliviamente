package projectspm.aliviamente

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView


class PrincipalDoctorFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_principal_doctor, container, false)

        val card_consulta: CardView = view.findViewById(R.id.consultas_card_doctor)
        card_consulta.setOnClickListener{
            val intent = Intent(context, ConsultaActivity::class.java)
            startActivity(intent)

        }
        val card_consulta_doctor: CardView = view.findViewById(R.id.solicitacoes_apoio)
        card_consulta_doctor.setOnClickListener{
            val intent = Intent(context, SolicitacoesConsultasDoctorActivity::class.java)
            startActivity(intent)
        }

        val card_paciente: CardView = view.findViewById(R.id.card_paciente_doctor)
        card_paciente.setOnClickListener{
            val intent = Intent(context, PacientesActivity::class.java)
            startActivity(intent)
        }
        return  view
    }


}