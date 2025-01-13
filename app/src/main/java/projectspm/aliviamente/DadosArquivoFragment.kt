package projectspm.aliviamente


import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import java.io.File
import android.Manifest
import android.content.Intent


class DadosArquivoFragment : Fragment() {

    private lateinit var imagePreview: ImageView
    private lateinit var photoUri: Uri

    private var selectedBitmap: Bitmap? = null

    private val requestCameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {

                capturePhoto()
            } else {

                Toast.makeText(requireContext(), "Permissão para câmera necessária.", Toast.LENGTH_SHORT).show()
            }
        }


    private val capturePhotoLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                Log.d("PhotoCapture", "Foto capturada com sucesso!")
                imagePreview.setImageURI(photoUri)

                val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, photoUri)
                selectedBitmap = bitmap
            }else {
                Log.d("PhotoCapture", "Falha ao tirar a foto")
                Toast.makeText(requireContext(), "Falha ao tirar a foto", Toast.LENGTH_SHORT).show()
            }
        }

    private val chooseImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imagePreview.setImageURI(it)

                val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, it)
                selectedBitmap = bitmap
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dados_arquivo, container, false)



        imagePreview = view.findViewById(R.id.image_preview)
        val btn_selectImage = view.findViewById<Button>(R.id.btn_select_image)
        val btn_takePhoto = view.findViewById<Button>(R.id.btn_take_photo)
        val btn_cadastrar = view.findViewById<Button>(R.id.btn_cadastrar_d_arq)

        btn_takePhoto.setOnClickListener {
            checkCameraPermissionAndCapturePhoto()
        }

        btn_selectImage.setOnClickListener {
            openImageFromGallery()
        }

        btn_cadastrar.setOnClickListener {
            doRegister(view)
        }






        return view
    }

    private fun checkCameraPermissionAndCapturePhoto() {
        when {
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                capturePhoto()
            }
            else -> {
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }



    private fun openImageFromGallery() {
        chooseImageLauncher.launch("image/*")
    }

    private fun capturePhoto() {
        val photoFile = File.createTempFile("IMG_", ".jpg", requireContext().cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }


        photoUri = FileProvider.getUriForFile(
            requireContext(),
            "projectspm.aliviamente.provider",
            photoFile
        )


        capturePhotoLauncher.launch(photoUri)
    }


    fun doRegister (view: View) {

        val viewModel = ViewModelProvider(requireActivity()).get(CadastroViewModel::class.java)


        val apiService = ApiService(requireContext())
        if (viewModel.tipoUsuario.value.toString() != "Paciente") {
            apiService.doRegister(
                viewModel.nome.value.toString(),
                viewModel.email.value.toString(),
                viewModel.senha.value.toString(),
                viewModel.confirmarSenha.value.toString(),
                viewModel.dataNascimento.value.toString(),
                viewModel.morada.value.toString(),
                viewModel.codigoPostal.value.toString(),
                viewModel.tipoUsuario.value.toString(),
                cedula_profissional = selectedBitmap, { jsonResponse ->
                    Log.e("SUCCESS", jsonResponse.toString())
                    Toast.makeText(requireContext(), "Registo realizado com sucesso", Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)

                }, onError =
                { errorMessage ->
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                })
        }
    }







}