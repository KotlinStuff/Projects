package es.javiercarrasco.myexplicitintent2b

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.myexplicitintent2b.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // View Binding
    private lateinit var binding: ActivityMainBinding

    companion object {
        const val TAG_APP = "myExplicitIntent2B"
        const val EXTRA_NAME = "userNAME"
    }

    // Objeto para recoger la respuesta de la activity.
    var resultadoActivity = registerForActivityResult(StartActivityForResult()) { result ->

        if (result.resultCode == Activity.RESULT_OK) {
            // No se usan los REQUEST_CODE
            val data: Intent? = result.data

            binding.tvResult.text = "Condiciones aceptadas."
            binding.tvResult.visibility = View.VISIBLE
        }

        if (result.resultCode == Activity.RESULT_CANCELED) {
            binding.tvResult.text = "Se canceló el contrato!"
            binding.tvResult.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se oculta el TextView que mostrará el resultado.
        binding.tvResult.visibility = View.INVISIBLE

        binding.btSend.setOnClickListener {
            if (validarTexto())
                askConditions()
        }
    }

    private fun validarTexto(): Boolean {
        var esValido = true

        if (TextUtils.isEmpty(binding.textToSend.text.toString())) {
            // Si la propiedad error tiene valor, se muestra el aviso.
            binding.textToSend.error = "Información requerida"
            esValido = false
        } else binding.textToSend.error = null

        return esValido
    }

    private fun askConditions() {
        Log.d(TAG_APP, "askConditions()")
        // Se vuelve a ocultar el TV que mostrará el resultado.
        binding.tvResult.visibility = View.INVISIBLE

        // Se crea un objeto de tipo Intent
        val myIntent = Intent(this, SecondActivity::class.java).apply {
            // Se añade la información a pasar por clave-valor.
            putExtra(EXTRA_NAME, binding.textToSend.text.toString())
        }

        resultadoActivity.launch(myIntent)
    }
}
