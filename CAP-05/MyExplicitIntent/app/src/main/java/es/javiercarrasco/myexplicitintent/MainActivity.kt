package es.javiercarrasco.myexplicitintent

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.myexplicitintent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // View Binding
    private lateinit var binding: ActivityMainBinding

    companion object {
        const val EXTRA_MESSAGE = "myMessage"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btSend.setOnClickListener {

            if (validarTexto()) {
                // Se crea un objeto de tipo Intent.
                val myIntent = Intent(this, SecondActivity::class.java).apply {
                    // Se añade la información a pasar por clave-valor.
                    putExtra(EXTRA_MESSAGE, binding.textToSend.text.toString())
                }

                // Se lanza la activity
                startActivity(myIntent)
            }
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
}
