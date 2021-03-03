package es.javiercarrasco.mymenus4

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.mymenus4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // Se crea el PopUp y el listener sobre le menú.
    fun showPopup(v: View) {
        PopupMenu(this, v).apply {
            inflate(R.menu.acciones)
            setOnMenuItemClickListener {
                when (it!!.itemId) {
                    R.id.menu_archive -> {
                        Toast.makeText(this@MainActivity,
                                "Opción ${getString(R.string.menu_op_archive)}",
                                Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.menu_delete -> {
                        Toast.makeText(this@MainActivity,
                                "Opción ${getString(R.string.menu_op_delete)}",
                                Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.menu_save -> {
                        Toast.makeText(this@MainActivity,
                                "Opción ${getString(R.string.menu_op_save)}",
                                Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }
        }.show()
    }
}
