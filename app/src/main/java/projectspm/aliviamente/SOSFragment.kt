package projectspm.aliviamente

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat


class SOSFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_s_o_s, container, false)

        val btn_chamada = view.findViewById<Button>(R.id.btn_chamada)
        btn_chamada.setOnClickListener {
            dialPhoneNumber("+351808242424")
        }
        return view
    }



    private fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }

        // Verificar se existe um app que pode tratar a intenção
        val packageManager = requireActivity().packageManager
        val activities = packageManager.queryIntentActivities(intent, 0)

        if (activities.isNotEmpty()) {
            Log.d("SOSFragment", "Iniciando discagem para: $phoneNumber")
            startActivity(intent) // Inicia a chamada para o discador
        } else {
            Toast.makeText(requireContext(), "Nenhum aplicativo de discagem encontrado.", Toast.LENGTH_SHORT).show()
        }
    }




}