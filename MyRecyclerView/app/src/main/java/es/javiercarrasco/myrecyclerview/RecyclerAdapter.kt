package es.javiercarrasco.myrecyclerview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var animals: MutableList<MyAnimals> = ArrayList()
    lateinit var context: Context

    // Constructor de la clase. Se pasa la fuente de datos y el contexto
    // sobre el que se mostrará.
    fun RecyclerAdapter(animalsList: MutableList<MyAnimals>, contxt: Context) {
        this.animals = animalsList
        this.context = contxt
    }

    // Este método se encarga de pasar los objetos, uno a uno al ViewHolder
    // personalizado.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = animals.get(position)
        holder.bind(item, context)
    }

    // Es el encargado de devolver el ViewHolder ya configurado.
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            layoutInflater.inflate(
                R.layout.item_animal_list,
                parent,
                false
            )
        )
    }

    // Devuelve el tamaño del array.
    override fun getItemCount(): Int {
        return animals.size
    }

    // Esta clase se encarga de rellenar cada una de las vistas que se inflarán
    // en el RecyclerView.
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Aquí es necesario utilizar findViewById para localizar el elemento
        // de la vista que se pasa como parámetro.
        private val aniName = view.findViewById(R.id.tv_nameAnimal) as TextView
        private val latinName = view.findViewById(R.id.tv_latinName) as TextView
        private val aniImg = view.findViewById(R.id.iv_animalImage) as ImageView

        fun bind(animal: MyAnimals, context: Context) {
            Log.d("bind", animal.imageAnimal.toString())

            aniName.text = animal.animalName
            latinName.text = animal.latinName
            aniImg.setImageResource(animal.imageAnimal!!)

            itemView.setOnClickListener {
                Toast.makeText(
                    context,
                    animal.animalName,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
