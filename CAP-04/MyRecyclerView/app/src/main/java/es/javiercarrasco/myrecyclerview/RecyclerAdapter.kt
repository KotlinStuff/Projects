package es.javiercarrasco.myrecyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import es.javiercarrasco.myrecyclerview.databinding.ItemAnimalListBinding

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
            ItemAnimalListBinding.inflate(
                layoutInflater,
                parent,
                false
            ).root
        )
    }

    // Devuelve el tamaño del array.
    override fun getItemCount(): Int {
        return animals.size
    }

    // Esta clase se encarga de rellenar cada una de las vistas que se inflarán
    // en el RecyclerView.
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Se usa View Binding para localizar los elementos en la vista.
        private val binding = ItemAnimalListBinding.bind(view)

        fun bind(animal: MyAnimals, context: Context) {
            binding.tvNameAnimal.text = animal.animalName
            binding.tvLatinName.text = animal.latinName
            binding.ivAnimalImage.setImageResource(animal.imageAnimal!!)

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
