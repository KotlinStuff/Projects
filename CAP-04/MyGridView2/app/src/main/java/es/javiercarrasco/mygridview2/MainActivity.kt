package es.javiercarrasco.mygridview2

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import es.javiercarrasco.mygridview2.databinding.ActivityMainBinding
import es.javiercarrasco.mygridview2.databinding.GridviewItemBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var adapter: ItemAdapter? = null
    var itemsList = ArrayList<MyItems>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        binding.myGridView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        binding.myGridView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val bindingItem = GridviewItemBinding.bind(view!!)

                Toast.makeText(
                    applicationContext,
                    "Pulsado ${bindingItem.tvName.text}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}
