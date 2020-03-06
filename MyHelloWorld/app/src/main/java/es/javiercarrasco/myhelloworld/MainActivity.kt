package es.javiercarrasco.myhelloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Pulsación sobre el botón.
         */
        bt_hello.setOnClickListener { sayHello() }
    }

    private fun sayHello() {
        if (et_yourName.text.toString().isEmpty()) {
            Toast.makeText(
                this, "I need your name",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                this, "Hi ${et_yourName.text}!!",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    fun Fragment.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(getActivity(), message, duration).show()
    }
}

