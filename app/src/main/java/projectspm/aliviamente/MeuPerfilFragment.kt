package projectspm.aliviamente

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


class MeuPerfilFragment : Fragment() {

    private lateinit var apiService: ApiService


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


        apiService = ApiService(requireContext())
        getUser()

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

    private fun getUser () {
        val sharedPreferences = activity?.getSharedPreferences("aliviamente", Context.MODE_PRIVATE)
        val id_user = sharedPreferences?.getInt("id", -1)?: -1

        if (id_user == -1) {
            Log.e("Error", "ID do paciente não encontrado.")
            return
        }



        apiService.getUser(id_user,
            onSuccess = { user ->
                if (isAdded) {
                    requireView().findViewById<TextView>(R.id.nome_perfil).text = user.nome
                    requireView().findViewById<TextView>(R.id.email_perfil).text = user.email
                    requireView().findViewById<TextView>(R.id.dt_nasc_perfil).text = user.dt_nasc
                    requireView().findViewById<TextView>(R.id.morada_perfil).text = user.morada
                    requireView().findViewById<TextView>(R.id.cod_postal_perfil).text =
                        user.codPostal
                    requireView().findViewById<TextView>(R.id.tipo_user_perfil).text =
                        user.tipo_user
                    requireView().findViewById<TextView>(R.id.password_perfil).text = user.password
                    if (user.cedula_profissional == "null") {
                        requireView().findViewById<TextView>(R.id.text_cedula_profissional).visibility =
                            View.INVISIBLE
                        requireView().findViewById<TextView>(R.id.cedula_prof_perfil).visibility =
                            View.INVISIBLE

                    }else {
                        requireView().findViewById<TextView>(R.id.cedula_prof_perfil).text =
                            user.cedula_profissional
                    }

                }
            }, onError = { error ->
                Toast.makeText(requireContext(), "Erro: $error", Toast.LENGTH_LONG).show()

            }
        )
    }


}