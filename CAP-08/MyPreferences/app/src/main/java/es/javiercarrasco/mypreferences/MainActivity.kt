package es.javiercarrasco.mypreferences

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.mypreferences.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // Este método se ejecuta cada vez que la activity vuelve al primer plano.
    override fun onResume() {
        super.onResume()

        if (SharedApp.preferences.name != "")
            supportActionBar?.title = SharedApp.preferences.name
        else supportActionBar?.title = resources.getString(R.string.app_name)
    }

    // Se infla el menú para mostrarlo en la barra de la aplicación.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflate = menuInflater
        inflate.inflate(R.menu.menu, menu)
        return true
    }

    // Método encargado de gestionar las opciones pulsadas del menú.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.op_pref -> {
                // Se crea un objeto de tipo Intent
                val myIntent = Intent(this, SettingsActivity::class.java)

                // Se lanza la activity
                startActivity(myIntent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}