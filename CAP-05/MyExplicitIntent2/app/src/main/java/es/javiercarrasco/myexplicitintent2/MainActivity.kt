package es.javiercarrasco.myexplicitintent2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.myexplicitintent2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // View Binding
    private lateinit var binding: ActivityMainBinding
    private var REQUEST_CODE = 1234

    companion object {
        const val TAG_APP = "myExplicitIntent2"
        const val EXTRA_NAME = "userNAME"
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
        // Se lanza la activity.
        startActivityForResult(myIntent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                binding.tvResult.text = "Condiciones aceptadas."
                binding.tvResult.visibility = View.VISIBLE
            }

            if (resultCode == Activity.RESULT_CANCELED) {
                binding.tvResult.text = "Se canceló el contrato!"
                binding.tvResult.visibility = View.VISIBLE
            }
        }
    }
}
