package projectspm.aliviamente

import android.content.Context

import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import projectspm.aliviamente.model.Consulta
import projectspm.aliviamente.model.User
import java.lang.Error

class ApiService (private val context: Context) {

    private val queue:RequestQueue = Volley.newRequestQueue(context)

    fun doLogin(email: String, password: String, onSuccess: (JSONObject)-> Unit, onError: (String) -> Unit) {
        val url = "https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g15/PAW/api/index.php"


        val postRequest = object : StringRequest(
            Method.POST, url,
            { response ->
                try {
                    val jsonResponse = JSONObject(response)
                    val status = jsonResponse.getString("status")
                    val message = jsonResponse.getString("message")

                    if (status == "SUCCESS") {
                        onSuccess(jsonResponse)
                    } else {
                        onError(message)
                    }
                }catch (e: JSONException) {
                    onError("Erro ao processar a resposta: ${e.message}")
                }
            },
            Response.ErrorListener { error ->
                onError("Erro: ${error.message}")
            }) {

            override fun getBody(): ByteArray {
                val jsonParams = JSONObject()
                jsonParams.put("email", email)
                jsonParams.put("password", password)
                return jsonParams.toString().toByteArray(Charsets.UTF_8)
            }

            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json; charset=utf-8"
                return headers
            }


        }

// Add the request to the RequestQueue.
        queue.add(postRequest)
    }

    fun getConsultasInfo(id_paciente: Int, onSuccess: (List<Consulta>) -> Unit, onError: (String) -> Unit) {
        val url = "https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g15/PAW/api/readConsulta.php?id_paciente=${id_paciente}"
        Log.d("ApiService", "Enviando id_paciente: $id_paciente")


        val postRequest = object: StringRequest(
            Method.GET, url,
            { response ->
                Log.d("Response", response.toString())
                try {
                    val jsonObject = JSONObject(response)
                    val responseStatus = jsonObject.getString("status")
                    val message = jsonObject.getString("message")

                    if (responseStatus == "SUCCESS") {
                        val jsonArray = jsonObject.getJSONArray("consultas")
                        val consultas = mutableListOf<Consulta>()

                        for (i in 0 until jsonArray.length()) {
                            val item = jsonArray.getJSONObject(i)
                            val cons = Consulta()
                            cons.data_consulta = item.getString("data_consulta")
                            cons.hora_consulta = item.getString("hora_consulta")
                            cons.status = item.getString("status_pedido")
                            consultas.add(cons)
                        }

                        onSuccess(consultas)
                    }else {
                        onError(message)
                    }
                } catch (e: JSONException) {
                    onError("Erro ao processar a resposta: ${e.message}")
                }
            },
            Response.ErrorListener { error ->
                onError("Erro: ${error.message}")
            }) {

        }
        queue.add(postRequest)

    }
    fun getConsultas(onSuccess: (List<Consulta>) -> Unit, onError: (String) -> Unit) {
        val url = "https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g15/PAW/api/read.php"
        Log.d("ApiService", "Olá")


        val postRequest = object: StringRequest(
            Method.GET, url,
            { response ->
                Log.d("Response", response.toString())
                try {
                    val jsonObject = JSONObject(response)
                    val responseStatus = jsonObject.getString("status")
                    val message = jsonObject.getString("message")

                    if (responseStatus == "SUCCESS") {
                        val jsonArray = jsonObject.getJSONArray("consultas")
                        val soli_consultas = mutableListOf<Consulta>()

                        for (i in 0 until jsonArray.length()) {
                            val item = jsonArray.getJSONObject(i)
                            val cons = Consulta()
                            cons.nome_paciente = item.getString("nome_paciente")
                            cons.data_consulta = item.getString("data_consulta")
                            cons.hora_consulta = item.getString("hora_consulta")
                            cons.status = item.getString("status_pedido")
                            soli_consultas.add(cons)
                        }

                        onSuccess(soli_consultas)
                    }else {
                        onError(message)
                    }
                } catch (e: JSONException) {
                    onError("Erro ao processar a resposta: ${e.message}")
                }
            },
            Response.ErrorListener { error ->
                onError("Erro: ${error.message}")
            }) {

        }
        queue.add(postRequest)

    }

//    fun getUser(id: Int, onSuccess: (User) -> Unit, onError: (String) -> Unit) {
//        val url = "https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g15/PAW/api/readConsulta.php?id=${id}"
//        Log.d("ApiService", "Enviando id: $id")
//
//
//        val postRequest = object: StringRequest(
//            Method.GET, url,
//            { response ->
//                Log.d("Response", response.toString())
//                try {
//                    val jsonObject = JSONObject(response)
//                    val responseStatus = jsonObject.getString("status")
//                    val message = jsonObject.getString("message")
//
//                    if (responseStatus == "SUCCESS") {
//                        val userJson = jsonObject.getJSONObject("usuario")
//                        val usuario = User(
//                            nome = userJson.getString("nome"),
//                            email = userJson.getString("email"),
//                            dt_nasc = userJson.getString("dt_nasc"),
//                            morada = userJson.getString("morada"),
//                            codPostal = userJson.getString("codPostal"),
//                            password = userJson.getString("password"),
//                            cedula_profissional = userJson.getString("cedula_profissional")
//                        )
//                        onSuccess(usuario)
//                    }else {
//                        onError(message)
//                    }
//                } catch (e: JSONException) {
//                    onError("Erro ao processar a resposta: ${e.message}")
//                }
//            },
//            Response.ErrorListener { error ->
//                onError("Erro: ${error.message}")
//            }) {
//
//        }
//        queue.add(postRequest)
//    }


}