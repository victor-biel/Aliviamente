package projectspm.aliviamente

import MultipartRequest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.media.Image

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
import java.io.ByteArrayOutputStream


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
                            cons.id_consulta = item.getInt("id_consulta")
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
    fun getConsultas(id: Int, onSuccess: (List<Consulta>) -> Unit, onError: (String) -> Unit) {
        val url = "https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g15/PAW/api/read.php?id=$id"
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
                            cons.id_pedido = item.getInt("id_pedido")
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

    fun getUser(id_user: Int, onSuccess: (User) -> Unit, onError: (String) -> Unit) {
        val url = "https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g15/PAW/api/readUser.php?id_user=${id_user}"
        Log.d("ApiService", "Enviando id_user: $id_user")


        val postRequest = object: StringRequest(
            Method.GET, url,
            { response ->
                Log.d("Response", response.toString())
                try {
                    val jsonObject = JSONObject(response)
                    val responseStatus = jsonObject.getString("status")
                    val message = jsonObject.getString("message")

                    if (responseStatus == "SUCCESS") {
                        val userJson = jsonObject.getJSONObject("usuario")
                        val usuario = User().apply {
                            nome = userJson.getString("nome")
                            email = userJson.getString("email")
                            dt_nasc = userJson.getString("dt_nasc")
                            morada = userJson.getString("morada")
                            codPostal = userJson.getString("codPostal")
                            tipo_user = userJson.getString("tipo_user")
                            password = userJson.getString("password")
                            cedula_profissional = userJson.getString("cedula_profissional")
                        }

                        onSuccess(usuario)
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

    fun requestConsultation(data_consulta: String, hora_consulta: String, id_user: Int, onSuccess: (JSONObject)-> Unit, onError: (String) -> Unit) {
        val url =  "https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g15/PAW/api/create_1.php?id_user=${id_user}"




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
                jsonParams.put("data_consulta", data_consulta)
                jsonParams.put("hora_consulta", hora_consulta)
                return jsonParams.toString().toByteArray(Charsets.UTF_8)
            }

            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json; charset=utf-8"
                return headers
            }


        }

        queue.add(postRequest)
    }


    fun doRegister(
        nome: String,
        email: String,
        password: String,
        confirmar_password: String,
        dt_nasc: String,
        morada: String,
        codPostal: String,
        tipo_user: String,
        cedula_profissional: Bitmap?,
        onSuccess: (JSONObject) -> Unit,
        onError: (String) -> Unit
    ) {
        val url = "https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g15/PAW/api/registrar_1.php"

        val multipartRequest = object : MultipartRequest(Method.POST, url,
            Response.Listener { response ->
                try {
                    val responseString = String(response.data)
                    Log.d("API Response", responseString)

                    val jsonResponse = JSONObject(responseString)
                    val status = jsonResponse.getString("status")
                    val message = jsonResponse.getString("message")



                    if (status == "SUCCESS") {
                        onSuccess(jsonResponse)
                        Log.d("ApiService", "Resposta: $responseString")
                    } else {
                        onError(message)
                        Log.d("ApiService", "Erro: $message")
                    }
                } catch (e: JSONException) {
                    onError("Erro ao processar a resposta: ${e.message}")
                }
            },
            Response.ErrorListener { error ->
                onError("Erro: ${error.message}")
            }) {

            init {
                addStringParam("nome", nome)
                addStringParam("email", email)
                addStringParam("password", password)
                addStringParam("confirmar_password", confirmar_password)
                addStringParam("dt_nasc", dt_nasc)
                addStringParam("morada", morada)
                addStringParam("codPostal", codPostal)
                addStringParam("tipo_user", tipo_user)

                cedula_profissional?.let {
                    val stream = ByteArrayOutputStream()
                    it.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                    val byteArray = stream.toByteArray()
                    addFileParam("cedula_profissional", byteArray)
                }
            }
        }

        queue.add(multipartRequest)
    }

    fun approved(id_pedido: Int, id_user: Int, onSuccess: (JSONObject)-> Unit, onError: (String) -> Unit) {
        val url =  "https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g15/PAW/api/approved.php"

        val postRequest = object : StringRequest(
            Method.POST, url,
            { response ->
                Log.d("API Response", "Resposta recebida: $response")
                try {
                    val jsonResponse = JSONObject(response)
                    val status = jsonResponse.getString("status")
                    val message = jsonResponse.getString("message")

                    Log.d("API Response", "Status: $status, Message: $message")

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
                jsonParams.put("id_pedido", id_pedido)
                jsonParams.put("id_user", id_user)
                return jsonParams.toString().toByteArray(Charsets.UTF_8)
            }

            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json; charset=utf-8"
                return headers
            }


        }

        queue.add(postRequest)
    }

    fun getPacientes (id_user: Int, onSuccess: (List<User>) -> Unit, onError: (String) -> Unit) {
        val url =  "https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g15/PAW/api/readPacientes.php"

        val postRequest = object : StringRequest(
            Method.POST, url,
            { response ->
                Log.d("API Response", "Resposta recebida: $response")
                try {
                    val jsonResponse = JSONObject(response)
                    val status = jsonResponse.getString("status")
                    val message = jsonResponse.getString("message")

                    Log.d("API Response", "Status: $status, Message: $message")

                    if (status == "SUCCESS") {
                        val jsonArray = jsonResponse.getJSONArray("pacientes")
                        val pacientes = mutableListOf<User>()

                        for (i in 0 until jsonArray.length()) {
                            val item = jsonArray.getJSONObject(i)
                            val pac = User()
                            pac.nome = item.getString("nome")
                            pac.email = item.getString("email")
                            pac.idade = item.getString("idade")
                            pac.id = item.getInt("id")
                            pac.id_consulta = item.getInt("id_consulta")
                            pacientes.add(pac)
                        }
                        onSuccess(pacientes)
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
                jsonParams.put("id_user", id_user)
                return jsonParams.toString().toByteArray(Charsets.UTF_8)
            }

            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json; charset=utf-8"
                return headers
            }


        }

        queue.add(postRequest)
    }

    fun getConsultasPaciente(id: Int, onSuccess: (List<Consulta>) -> Unit, onError: (String) -> Unit) {
        val url = "https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g15/PAW/api/listConsultasPaciente.php"
        Log.d("ApiService", "Olá")


        val postRequest = object: StringRequest(
            Method.POST, url,
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
                            cons.data_consulta = item.getString("data_consulta")
                            cons.hora_consulta = item.getString("hora_consulta")
                            cons.status = item.getString("status_pedido")
                            cons.id_consulta = item.getInt("id_consulta")
                            cons.nome_paciente = item.getString("nome_paciente")
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

            override fun getBody(): ByteArray {
                val jsonParams = JSONObject()
                jsonParams.put("id", id)
                return jsonParams.toString().toByteArray(Charsets.UTF_8)
            }

            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json; charset=utf-8"
                return headers
            }

        }
        queue.add(postRequest)

    }




}