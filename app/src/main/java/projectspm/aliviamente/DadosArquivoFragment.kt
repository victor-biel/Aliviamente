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
    private lateinit var photoUri: Uri

    private val capturePhotoLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                imagePreview.setImageURI(photoUri)

                val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, photoUri)
            }
        }

    private val chooseImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imagePreview.setImageURI(it)

                val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, it)
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
            openImageFromGallery()
        }




        return view
    }



    private fun openImageFromGallery() {
        chooseImageLauncher.launch("image/*")
    }

    private fun capturePhoto() {
        // Criar um arquivo temporário para armazenar a foto capturada
        val photoFile = File.createTempFile("IMG_", ".jpg", requireContext().cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }

        // Obter URI para o arquivo
        photoUri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            photoFile
        )

        // Iniciar a captura de foto
        capturePhotoLauncher.launch(photoUri)
    }

    fun doRegister (view: View) {

    }





}