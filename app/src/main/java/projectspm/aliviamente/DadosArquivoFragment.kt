package projectspm.aliviamente

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DadosArquivoFragment : Fragment() {

    private lateinit var imagePreview: ImageView
    private var currentPhotoPath: String? = null

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            uri: Uri? ->
            uri?.let {
                imagePreview.setImageURI(it)
            }
        }

    private val captureImageLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { sucess ->
            if (sucess) {
                val bitmap = BitmapFactory.decodeFile(currentPhotoPath)
                imagePreview.setImageBitmap(bitmap)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dados_arquivo, container, false)

        imagePreview = view.findViewById(R.id.image_preview)
        val btn_selectImage = view.findViewById<Button>(R.id.btn_select_image)
        val btn_takePhoto = view.findViewById<Button>(R.id.btn_take_photo)

        btn_takePhoto.setOnClickListener {
            capturePhoto()
        }

        btn_selectImage.setOnClickListener {
            openImagePicker()
        }




        return view
    }

    private fun openImagePicker() {
        pickImageLauncher.launch("image/*")
    }

    private fun capturePhoto () {
        val arquivoFoto: File? = createImageFile()
        arquivoFoto?.let {
            val fotoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                "aliviamente",
                it
            )
            captureImageLauncher.launch(fotoURI)
        }
    }

    private fun createImageFile(): File? {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("IMG_${timestamp}_",".jpg", storageDir ).apply {
            currentPhotoPath = absolutePath
        }
    }



}