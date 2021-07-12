package es.javiercarrasco.mypreferences

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.mypreferences.databinding.ActivitySettingsBinding

const val EMPTY_VALUE = ""

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se comprueba si existen propiedades creadas para cargarlas.
        configView()

        // Botón para eliminar las preferencias.
        binding.btnDeletePrefs.setOnClickListener {
            SharedApp.preferences.deletePrefs()
            onBackPressed()
        }
    }

    // Se controla si se pulsa el botón "Atrás" de la barra de app.
    // Se controla como una opción de menú más.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Método que indica si se pulsa el botón "Atrás" del
    // propio sistema operativo.
    override fun onBackPressed() {
        // Gracias a la clase Preferences, la asignación
        // se encarga de editar y guardar.
        if (!binding.etName.text.isEmpty())
            SharedApp.preferences.name = binding.etName.text.toString()

        super.onBackPressed()
    }

    // Muestra el valor de la propiedad en el EditText.
    fun showPrefs() {
        binding.etName.hint = SharedApp.preferences.name
    }

    // Comprueba si existe la propiedad.
    fun configView() {
        if (isSavedName()) showPrefs()
    }

    fun isSavedName(): Boolean {
        val myName = SharedApp.preferences.name
        return myName != EMPTY_VALUE
    }
}