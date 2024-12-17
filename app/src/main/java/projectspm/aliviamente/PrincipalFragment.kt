package projectspm.aliviamente

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView


class PrincipalFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_principal, container, false)

        val card_consulta:CardView = view.findViewById(R.id.consultas_card)
        card_consulta.setOnClickListener{
            val intent = Intent(context, ConsultaActivity::class.java)
            startActivity(intent)

        }


        return view
    }






}