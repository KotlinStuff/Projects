package es.javiercarrasco.myexplicitintent2b

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.myexplicitintent2b.databinding.ActivitySecondBinding
import es.javiercarrasco.myexplicitintent2b.MainActivity.Companion.EXTRA_NAME
import es.javiercarrasco.myexplicitintent2b.MainActivity.Companion.TAG_APP

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
            setResult(Activity.RESULT_OK)
            Log.d(TAG_APP, "Valor devuelto OK")
            finish()
        }

        binding.btCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            Log.d(TAG_APP, "Valor devuelto CANCELED")
            finish()
        }
    }
}
