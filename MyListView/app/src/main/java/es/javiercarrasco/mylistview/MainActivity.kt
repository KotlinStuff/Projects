package es.javiercarrasco.mylistview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // Fuente de datos para el ListView.
    private val nombres = arrayOf(
        "Javier", "Nacho", "Patricia",
        "Miguel", "Susana", "Rosa", "Juan",
        "Pedro", "Asunción", "Antonio",
        "Lorena", "Verónica", "Paola",
        "Esteban", "Andrea", "María"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nombres2 = resources.getStringArray(R.array.array_nombres)

        // Se crea el Adapter uniendo la vista y los datos.
        val adapter = ArrayAdapter(this, R.layout.listview_item, nombres2)

        // Se asigna el Adapter al Listview.
        myListView.adapter = adapter

        // Se utiliza un AdapterView para conocer el elemento pulsado.
        myListView.onItemClickListener =
            object : AdapterView.OnItemClickListener {
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Toast.makeText(
                        applicationContext,
                        "Pulsado ${myListView.getItemAtPosition(position)}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}
