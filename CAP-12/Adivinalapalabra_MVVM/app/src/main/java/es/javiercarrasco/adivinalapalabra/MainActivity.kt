package es.javiercarrasco.adivinalapalabra

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var mContext: Context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = applicationContext
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        Toast.makeText(
            applicationContext,
            "onBackPressed",
            Toast.LENGTH_SHORT
        ).show()

        //super.onBackPressed()
    }
}