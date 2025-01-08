import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.HttpHeaderParser
import java.io.ByteArrayOutputStream

class MultipartRequest(
    method: Int,
    url: String,
    private val responseListener: Response.Listener<NetworkResponse>,
    private val errorListener: Response.ErrorListener
) : Request<NetworkResponse>(method, url, errorListener) {

    private val params: MutableMap<String, String> = HashMap()
    private val byteData: MutableMap<String, ByteArray> = HashMap()
    private val boundary: String = "apiclient-" + System.currentTimeMillis() // Definindo o boundary aqui

    fun addStringParam(key: String, value: String) {
        params[key] = value
    }

    fun addFileParam(key: String, fileData: ByteArray) {
        byteData[key] = fileData
    }

    override fun getBodyContentType(): String {
        return "multipart/form-data; boundary=$boundary"
    }

    override fun getBody(): ByteArray {
        val bodyBuilder = StringBuilder()
        for ((key, value) in params) {
            bodyBuilder.append("--$boundary\r\n")
            bodyBuilder.append("Content-Disposition: form-data; name=\"$key\"\r\n\r\n")
            bodyBuilder.append(value)
            bodyBuilder.append("\r\n")
        }

        for ((key, fileData) in byteData) {
            bodyBuilder.append("--$boundary\r\n")
            bodyBuilder.append("Content-Disposition: form-data; name=\"$key\"; filename=\"file.jpg\"\r\n")
            bodyBuilder.append("Content-Type: image/jpeg\r\n\r\n")
            bodyBuilder.append(String(fileData))
            bodyBuilder.append("\r\n")
        }

        bodyBuilder.append("--$boundary--\r\n")
        return bodyBuilder.toString().toByteArray(Charsets.UTF_8)
    }

    override fun parseNetworkResponse(response: NetworkResponse): Response<NetworkResponse> {
        return Response.success(response, HttpHeaderParser.parseCacheHeaders(response))
    }

    override fun deliverResponse(response: NetworkResponse) {
        responseListener.onResponse(response)
    }

    override fun deliverError(error: VolleyError) {
        errorListener.onErrorResponse(error)
    }
}
