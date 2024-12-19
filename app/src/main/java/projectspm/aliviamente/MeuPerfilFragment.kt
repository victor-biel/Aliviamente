package projectspm.aliviamente

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class MeuPerfilFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_meu_perfil, container, false)

        val btn_logout = view.findViewById<Button>(R.id.btn_logout)

        btn_logout.setOnClickListener {
            logout()
        }

        return view
    }

    private fun logout() {
        val sharedPreferences = activity?.getSharedPreferences("aliviamente", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()

        editor?.clear()
        editor?.apply()

        val Intent = Intent(activity, LoginActivity::class.java)
        startActivity(Intent)

        activity?.finish()

    }


}