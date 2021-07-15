package es.javiercarrasco.mysqlite

import android.content.Context
import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import es.javiercarrasco.mysqlite.databinding.ItemRecyclerviewBinding

class MyRecyclerViewAdapter
    : RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {

    private lateinit var context: Context
    private lateinit var cursor: Cursor

    fun MyRecyclerViewAdapter(context: Context, cursor: Cursor) {
        this.context = context
        this.cursor = cursor
    }

    /**
     * Se "infla" la vista de los items.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        Log.d("RECYCLERVIEW", "onCreateViewHolder")
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            inflater.inflate(
                R.layout.item_recyclerview,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return cursor.count
    }

    /**
     * Se completan los datos de cada vista mediante ViewHolder.
     */
    override fun onBindViewHolder(
        holder: MyRecyclerViewAdapter.ViewHolder,
        position: Int
    ) {
        // Importante para recorrer el cursor.
        cursor.moveToPosition(position)
        Log.d("RECYCLERVIEW", "onBindViewHolder")

        // Se asignan los valores a los elementos de la UI.
        holder.id.text = cursor.getString(0)
        holder.nombre.text = cursor.getString(1)
        holder.apes.text = cursor.getString(2)
    }

    inner class ViewHolder : RecyclerView.ViewHolder {
        // Creamos las referencias de la UI.
        val id: TextView
        val nombre: TextView
        val apes: TextView

        constructor(view: View) : super(view) {
            // Se enlazan los elementos de la UI mediante ViewBinding.
            val bindingItemsRV = ItemRecyclerviewBinding.bind(view)
            this.id = bindingItemsRV.tvIdentificador
            this.nombre = bindingItemsRV.tvNombre
            this.apes = bindingItemsRV.tvApes

            view.setOnClickListener {
                Toast.makeText(
                    context,
                    "${this.id.text}-${this.nombre.text} ${this.apes.text}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}