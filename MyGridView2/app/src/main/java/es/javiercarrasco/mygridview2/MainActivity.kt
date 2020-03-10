package es.javiercarrasco.mygridview2

import android.os.Bundle
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.gridview_item.view.*

class MainActivity : AppCompatActivity() {

    var adapter: ItemAdapter? = null
    var itemsList = ArrayList<MyItems>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Se crea la fuente de datos con imágenes de muestra.
        itemsList.add(
            MyItems(
                "Estrella", R.drawable.abc_ic_star_black_48dp
            )
        )
        itemsList.add(
            MyItems(
                "Botón Check", R.drawable.abc_btn_check_material
            )
        )
        itemsList.add(
            MyItems(
                "1/2 estrella", R.drawable.abc_ic_star_half_black_48dp
            )
        )
        itemsList.add(
            MyItems(
                "Launcher Back", R.drawable.ic_launcher_background
            )
        )
        itemsList.add(
            MyItems(
                "Launcher Icon", R.drawable.ic_launcher_foreground
            )
        )
        itemsList.add(
            MyItems(
                "Launcher", R.mipmap.ic_launcher
            )
        )

        // Se generamos el adaptador.
        adapter = ItemAdapter(this, itemsList)

        // Asignamos el adapter
        myGridView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        myGridView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                Toast.makeText(
                    applicationContext,
                    "Pulsado ${myGridView[position].tvName.text}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}
