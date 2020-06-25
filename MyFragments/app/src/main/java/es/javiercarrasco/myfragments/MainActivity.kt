package es.javiercarrasco.myfragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.javiercarrasco.myfragments.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Controla si está cargado o no FragmentOne.
    var isFragmentOneLoaded = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se muestra en pantalla el primer Fragment.
        showFragmentOne()

        // Listener del botón.
        binding.btnChange.setOnClickListener {
            if (isFragmentOneLoaded)
                showFragmentTwo()
            else showFragmentOne()
        }
    }

    private fun showFragmentOne() {
        // Se establece la transacción de fragments, necesarios para añadir,
        // quitar o reemplazar fragments.
        val transaction = supportFragmentManager.beginTransaction()

        // Se instancia el fragment a mostrar.
        val fragment = FragmentOne()

        // Indicamos el elemento del layout donde haremos el cambio.
        transaction.replace(R.id.fragment_holder, fragment)

        // Se establece valor a null para indicar que no se está interesado
        // en volver a ese fragment más tarde, en caso contrario,
        // se indicaría el nombre del fragment, por ejemplo fragment.TAG,
        // aprovechando la variable creada en la clase.
        transaction.addToBackStack(null)

        // Se muestra el fragment.
        transaction.commit()
        isFragmentOneLoaded = true
    }

    // Igual que showFragmentOne() pero para el segundo.
    private fun showFragmentTwo() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = FragmentTwo()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
        isFragmentOneLoaded = false
    }
}