package es.javiercarrasco.mytabsv2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import es.javiercarrasco.mytabsv2.adapters.ViewPager2Adapter
import es.javiercarrasco.mytabsv2.databinding.ActivityMainBinding
import es.javiercarrasco.mytabsv2.effects.DepthPageTransformer

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager2 = binding.viewPager2

        // Se crea el adapter.
        val adapter = ViewPager2Adapter(supportFragmentManager, lifecycle)

        // Se añaden los fragments y los títulos de pestañas.
        adapter.addFragment(PersonsFragment(), "Personas")
        adapter.addFragment(FruitsFragment(), "Frutas")
        adapter.addFragment(AnimalsFragment(), "Animales")

        // Se asocia el adapter al ViewPager2.
        viewPager2.adapter = adapter

        // Efectos para el ViewPager2.
        //viewPager2.setPageTransformer(ZoomOutPageTransformer())
        viewPager2.setPageTransformer(DepthPageTransformer())

        // Carga de las pestañas en el TabLayout.
        TabLayoutMediator(binding.tabLayout, viewPager2) { tab, position ->
            tab.text = adapter.getPageTitle(position)
        }.attach()
    }
}