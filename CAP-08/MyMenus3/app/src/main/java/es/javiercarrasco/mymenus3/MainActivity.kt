package es.javiercarrasco.mymenus3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.AbsListView
import android.widget.ListView
import android.widget.Toast
import es.javiercarrasco.mymenus3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        val personas: MutableList<String> =
            mutableListOf(
                "Javier", "Pedro", "Nacho", "Patricia", "Miguel",
                "Susana", "Raquel", "Antonio", "Andrea", "Nicolás",
                "Juan José", "José Antonio", "Daniela", "María",
                "Verónica", "Juan", "Carlos", "Isabel", "Óscar", "Víctor"
            )
        var actionMode: ActionMode? = null
        var isActionMode: Boolean = false
        var seleccion: MutableList<String> = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ListViewAdapter(this, personas)
        with(binding) {
            myListView.adapter = adapter

            myListView.setOnItemClickListener { _, _, position, _ ->
                Toast.makeText(
                    applicationContext,
                    personas[position],
                    Toast.LENGTH_LONG
                ).show()
            }

            with(myListView) {
                // Se establece la selección múltiple en la lista.
                choiceMode = ListView.CHOICE_MODE_MULTIPLE_MODAL

                setMultiChoiceModeListener(object :AbsListView.MultiChoiceModeListener{
                    // Se llama cuando el usuario selecciona una opción del menú.
                    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                        return when (item!!.itemId) {
                            R.id.optionDelete -> {
                                Toast.makeText(
                                    context,
                                    R.string.menu_op_delete,
                                    Toast.LENGTH_LONG).show()
                                adapter.eliminarNombres(seleccion)
                                mode!!.finish()
                                return true
                            }
                            R.id.optionShare -> {
                                Toast.makeText(
                                    context,
                                    R.string.menu_op_share,
                                    Toast.LENGTH_LONG).show()
                                return true
                            }
                            else -> false
                        }
                    }

                    // Este método se invoca cuando la lista cambia a estado de selección.
                    override fun onItemCheckedStateChanged(
                        mode: ActionMode?,
                        position: Int,
                        id: Long,
                        checked: Boolean
                    ) {
                        // En este ejemplo no se realiza ninguna acción.
                    }

                    // Se llama cuando se crea el modo acción, en este caso,
                    // al crear setMultiChoiceModeListener().
                    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                        val inflater = menuInflater
                        inflater.inflate(R.menu.context_menu, menu)

                        actionMode = mode
                        isActionMode = true

                        return true
                    }

                    // Se llama cada vez que el modo acción se muestra, siempre
                    // después de onCreateActionMode().
                    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                        return false
                    }

                    // Se llama cuando se sale del modo de acción.
                    override fun onDestroyActionMode(mode: ActionMode?) {
                        // Se indica que no está activo el modo de acción.
                        isActionMode = false

                        // Se elimina el objeto ActionMode.
                        actionMode = null

                        // Se borra la selección del usuario.
                        seleccion.clear()
                    }
                })
            }
        }
    }
}