import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.HttpHeaderParser
import java.io.ByteArrayOutputStream

open class MultipartRequest(
    method: Int,
    url: String,
    private val responseListener: Response.Listener<NetworkResponse>,
    private val errorListener: Response.ErrorListener
) : Request<NetworkResponse>(method, url, errorListener) {

    private val params: MutableMap<String, String> = HashMap()
    private val byteData: MutableMap<String, ByteArray> = HashMap()
    private val boundary: String = "apiclient-" + System.currentTimeMillis()

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
        val outputStream = ByteArrayOutputStream()
        val lineEnd = "\r\n".toByteArray()
        val twoHyphens = "--".toByteArray()


        for ((key, value) in params) {
            outputStream.write(twoHyphens)
            outputStream.write(boundary.toByteArray())
            outputStream.write(lineEnd)
            outputStream.write("Content-Disposition: form-data; name=\"$key\"".toByteArray())
            outputStream.write(lineEnd)
            outputStream.write(lineEnd)
            outputStream.write(value.toByteArray())
            outputStream.write(lineEnd)
        }


        for ((key, fileData) in byteData) {
            outputStream.write(twoHyphens)
            outputStream.write(boundary.toByteArray())
            outputStream.write(lineEnd)
            outputStream.write("Content-Disposition: form-data; name=\"$key\"; filename=\"file.jpg\"".toByteArray())
            outputStream.write(lineEnd)
            outputStream.write("Content-Type: image/jpeg".toByteArray())
            outputStream.write(lineEnd)
            outputStream.write(lineEnd)
            outputStream.write(fileData)
            outputStream.write(lineEnd)
        }


        outputStream.write(twoHyphens)
        outputStream.write(boundary.toByteArray())
        outputStream.write(twoHyphens)
        outputStream.write(lineEnd)

        return outputStream.toByteArray()
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
