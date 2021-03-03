package es.javiercarrasco.myrecyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import es.javiercarrasco.myrecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val myAdapter: RecyclerAdapter = RecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
    }

    // Método encargado de configurar el RV.
    private fun setUpRecyclerView() {
        // Esta opción a TRUE significa que el RV tendrá
        // hijos del mismo tamaño, optimiza su creación.
        binding.myRVAnimals.setHasFixedSize(true)

        // Se indica el contexto para RV en forma de lista.
        binding.myRVAnimals.layoutManager = LinearLayoutManager(this)

        // Se indica el contexto para RV en forma de grid.
        //myRVAnimals.layoutManager = GridLayoutManager(this, 2)

        // Se genera el adapter.
        myAdapter.RecyclerAdapter(getAnimals(), this)

        // Se asigna el adapter al RV.
        binding.myRVAnimals.adapter = myAdapter
    }

    // Método encargado de generar la fuente de datos.
    private fun getAnimals(): MutableList<MyAnimals> {
        val animals: MutableList<MyAnimals> = arrayListOf()

        animals.add(MyAnimals("Cisne", "Cygnus olor", R.mipmap.cisne))
        animals.add(MyAnimals("Erizo", "Erinaceinae", R.mipmap.erizo))
        animals.add(MyAnimals("Gato", "Felis catus", R.mipmap.gato))
        animals.add(MyAnimals("Gorrión", "Passer domesticus", R.mipmap.gorrion))
        animals.add(MyAnimals("Mapache", "Procyon", R.mipmap.mapache))
        animals.add(MyAnimals("Oveja", "Ovis aries", R.mipmap.oveja))
        animals.add(MyAnimals("Perro", "Canis lupus familiaris", R.mipmap.perro))
        animals.add(MyAnimals("Tigre", "Panthera tigris", R.mipmap.tigre))
        animals.add(MyAnimals("Zorro", "Vulpes vulpes", R.mipmap.zorro))

        return animals
    }
}
