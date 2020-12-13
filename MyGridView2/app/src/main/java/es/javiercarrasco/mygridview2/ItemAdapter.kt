package es.javiercarrasco.mygridview2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import es.javiercarrasco.mygridview2.databinding.GridviewItemBinding

// Clase ItemAdapter que hereda de la clase abstracta BaseAdapter.
class ItemAdapter : BaseAdapter {

    var context: Context? = null
    var itemsList = ArrayList<MyItems>()

    // Se crea un constructor al que le pasamos el contexto
    // y la lista de elementos.
    constructor(
        context: Context,
        itemsList: ArrayList<MyItems>
    ) : super() {
        this.context = context
        this.itemsList = itemsList
    }

    // Devuelve la vista de cada elemento al adaptador.
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View {

        val item = this.itemsList[position]

        val inflator = LayoutInflater.from(context)

        val binding = GridviewItemBinding.inflate(inflator)

        binding.image.setImageResource(item.image)
        binding.tvName.text = item.name

        // Pulsación sobre la vista.
        binding.root.setOnClickListener {
            Toast.makeText(
                context,
                "${binding.tvName.text}",
                Toast.LENGTH_LONG
            ).show()
        }

        return binding.root
    }

    override fun getItem(position: Int): Any {
        return itemsList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return itemsList.size
    }
}