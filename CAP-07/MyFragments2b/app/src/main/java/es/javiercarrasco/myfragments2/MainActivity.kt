package es.javiercarrasco.myfragments2

import android.graphics.Color.GREEN
import android.graphics.Color.RED
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.myfragments2.databinding.ActivityMainBinding

internal const val ARG_NUMFRAG = "numFrag"
internal const val ARG_COLORBACK = "colorBack"

class MainActivity : AppCompatActivity(), NewFragment.DatoDevuleto {

    override fun datoActualizado(dato: String) {
        binding.tvInfoRecibida.text = dato
    }

    private lateinit var binding: ActivityMainBinding

    private var numfrag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se muestra el primer fragment.
        showFragment()

        binding.btnChange.setOnClickListener {
            binding.tvInfoRecibida.text = null
            showFragment()
        }
    }

    private fun showFragment() {
        val transaction = supportFragmentManager.beginTransaction()

        // Declaración del Fragment mediante newInstance.
        val fragment = NewFragment.newInstance(
            ++numfrag,
            (if ((numfrag % 2) == 0) RED else GREEN)
        )

        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}