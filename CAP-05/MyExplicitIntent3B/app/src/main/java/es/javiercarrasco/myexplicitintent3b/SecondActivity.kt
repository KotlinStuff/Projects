package es.javiercarrasco.myexplicitintent3b

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.myexplicitintent3b.databinding.ActivitySecondBinding
import es.javiercarrasco.myexplicitintent3b.MainActivity.Companion.EXTRA_NAME
import es.javiercarrasco.myexplicitintent3b.MainActivity.Companion.EXTRA_RESULT
import es.javiercarrasco.myexplicitintent3b.MainActivity.Companion.TAG_APP

class SecondActivity : AppCompatActivity() {
    // View Binding
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se recuperan los datos y se asignan al TextView.
        val nameReceived = intent.getStringExtra(EXTRA_NAME)

        binding.tvNameReceived.text = getString(
            R.string.msgAccept,
            nameReceived
        )

        binding.btAccept.setOnClickListener {

            val intentResult : Intent = Intent().apply {
                // Se añade el valor del rating.
                putExtra(EXTRA_RESULT, binding.ratingBar.rating)
            }

            Log.d(TAG_APP, "Se devuelve valor de rating")
            setResult(Activity.RESULT_OK, intentResult)
            finish()
        }

        binding.btCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            Log.d(TAG_APP, "Valor devuelto CANCELED")
            finish()
        }
    }
}
