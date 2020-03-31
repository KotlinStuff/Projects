package es.javiercarrasco.myexplicitintent

import android.content.Intent
import android.os.Bundle
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
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btSend.setOnClickListener {
            // Se crea un objeto de tipo Intent
            val myIntent = Intent(this, SecondActivity::class.java).apply {
                // Se añade la información a pasar por clave-valor
                putExtra(EXTRA_MESSAGE, binding.textToSend.text.toString())
            }

            // Se lanza la activity
            startActivity(myIntent)
        }
    }
}
