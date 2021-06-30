package es.javiercarrasco.myexplicitintent3b

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.myexplicitintent3b.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // View Binding
    private lateinit var binding: ActivityMainBinding

    companion object {
        const val TAG_APP = "myExplicitIntent3b"
        const val EXTRA_NAME = "userNAME"
        const val EXTRA_RESULT = "ratingVALUE"
    }

    val resultadoActivity = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val resultado = result.data?.getFloatExtra(EXTRA_RESULT, 0.0F).toString()

            binding.tvResult.text = getString(R.string.condicionesOk, resultado)
            binding.tvResult.visibility = View.VISIBLE
        }

        if (result.resultCode == Activity.RESULT_CANCELED) {
            binding.tvResult.text = getString(R.string.condicionesKO)
            binding.tvResult.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se oculta el TextView que mostrará el resultado.
        binding.tvResult.visibility = View.INVISIBLE

        binding.btSend.setOnClickListener { askConditions() }
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
        resultadoActivity.launch(myIntent)
    }

}
