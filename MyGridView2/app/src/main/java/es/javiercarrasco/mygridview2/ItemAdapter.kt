package es.javiercarrasco.mygridview2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.gridview_item.view.*

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

        var inflator = LayoutInflater.from(context)

        var itemView = inflator.inflate(R.layout.gridview_item, null)
        itemView.image.setImageResource(item.image!!)
        itemView.tvName.text = item.name!!

        itemView.image.setImageResource(item.image!!)
        itemView.tvName.text = item.name!!

        // Control del click sobre el ImageView.
        /*
        itemView.image.setOnClickListener {
            Toast.makeText(
                context,
                "${itemView.image.contentDescription}",
                Toast.LENGTH_LONG
            ).show()
        }
        */

        return itemView
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