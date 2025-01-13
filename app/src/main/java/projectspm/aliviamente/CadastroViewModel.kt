package projectspm.aliviamente

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData


class CadastroViewModel: ViewModel() {
    val nome = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val senha = MutableLiveData<String>()
    val confirmarSenha = MutableLiveData<String>()
    val dataNascimento = MutableLiveData<String>()
    val morada = MutableLiveData<String>()
    val codigoPostal = MutableLiveData<String>()
    val tipoUsuario = MutableLiveData<String>()
    val cedulaProfissional = MutableLiveData<String>()
}