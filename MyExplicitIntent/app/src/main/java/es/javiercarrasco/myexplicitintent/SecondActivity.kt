package es.javiercarrasco.myexplicitintent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.myexplicitintent.MainActivity.Companion.EXTRA_MESSAGE
import es.javiercarrasco.myexplicitintent.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se recuperan los datos y se asignan al TextView.
        val message = intent.getStringExtra(EXTRA_MESSAGE)
        binding.tvMsgReceived.text = message
    }
}