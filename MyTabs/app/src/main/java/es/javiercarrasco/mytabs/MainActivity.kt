package es.javiercarrasco.mytabs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.mytabs.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se carga la toolbar.
        setSupportActionBar(binding.toolbar)

        // Se crea el adapter.
        val adapter = ViewPagerAdapter(supportFragmentManager)

        // Se añaden los fragments y los títulos de pestañas.
        adapter.addFragment(PersonsFragment(), "Personas")
        adapter.addFragment(FruitsFragment(), "Frutas")
        adapter.addFragment(AnimalsFragment(), "Animales")

        // Se asocia el adapter.
        binding.viewPager.adapter = adapter

        // Se cargan las tabs.
        binding.tabs.setupWithViewPager(binding.viewPager)
    }
}