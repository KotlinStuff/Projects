package es.javiercarrasco.mymenus3

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import es.javiercarrasco.mymenus3.databinding.ItemLayoutBinding

class ListViewAdapter(
    context: Context,
    var names: List<String>
) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(
            Context.LAYOUT_INFLATER_SERVICE
        ) as LayoutInflater

    override fun getView(
        position: Int,
        convertView: View?, parent: ViewGroup?
    ): View {
        val viewFila = inflater.inflate(R.layout.item_layout, parent, false)

        // Se asigna el nombre al TextView
        val nombre = viewFila.findViewById(R.id.person_name) as TextView
        nombre.text = this.getItem(position)

        // Se asigna como etiqueta del checkBox la posición en la que
        // se encuentra.
        val check = viewFila.findViewById(R.id.checkBox) as CheckBox
        check.tag = position

        if (MainActivity.isActionMode) {
            check.visibility = View.VISIBLE
            //bindingFila.checkBox.visibility = View.VISIBLE
        } else {
            check.visibility = View.GONE
            //bindingFila.checkBox.visibility = View.GONE
        }

        // Se controla la selección del usuario mediante la lista selección.
        check.setOnCheckedChangeListener { compoundButton, _ ->
            val position = compoundButton.tag as Int
            Log.d("CHECKBOX", position.toString())

            // Se añade o elimina de la lista la selección.
            if (MainActivity.seleccion.contains(this.names[position])) {
                MainActivity.seleccion.remove(this.names[position])
            } else {
                MainActivity.seleccion.add(this.names[position])
            }

            MainActivity.actionMode!!.title =
                "${MainActivity.seleccion.size} items seleccionados"
        }

        // Se devuelve la fila.
        return viewFila
    }

    override fun getItem(position: Int): String {
        return this.names[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return this.names.size
    }

    fun eliminarNombres(items: List<String>) {
        // Se elimina los elementos seleccionados.
        for (item in items) {
            MainActivity.personas.remove(item)
        }

        // Se notifica un cambio en la información mostrada en la lista.
        // Esto producirá la actualización de la vista.
        notifyDataSetChanged()
    }
}