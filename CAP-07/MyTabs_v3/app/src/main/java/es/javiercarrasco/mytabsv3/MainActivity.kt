package es.javiercarrasco.mytabsv3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import es.javiercarrasco.mytabsv2.effects.ZoomOutPageTransformer
import es.javiercarrasco.mytabsv3.adapters.ViewPager2Adapter
import es.javiercarrasco.mytabsv3.databinding.ActivityMainBinding

private const val NUM_FRAGMENTS = 5 // Número de fragments a crear.

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager2 = binding.viewPager2

        // Permite elegir la dirección del paginado.
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // Se crea el adapter.
        val adapter = ViewPager2Adapter(supportFragmentManager, lifecycle)

        // Se añaden los fragments y los títulos de pestañas.
        for (num in 1..NUM_FRAGMENTS) {
            val fragment: PageFragment = PageFragment()
            val bundle = Bundle()
            bundle.putString("name", "Fragment ${num}")
            bundle.putString("texto", getString(R.string.textAMostrar, num))

            fragment.arguments = bundle

            adapter.addFragment(fragment, "Frg ${num}")
        }

        // Se asocia el adapter al ViewPager2.
        viewPager2.adapter = adapter

        // Efectos para el ViewPager2.
        viewPager2.setPageTransformer(ZoomOutPageTransformer())
        //viewPager2.setPageTransformer(DepthPageTransformer())

        // Carga de las pestañas en el TabLayout.
        TabLayoutMediator(binding.tabLayout, viewPager2) { tab, position ->
            tab.text = adapter.getPageTitle(position)
        }.attach()
    }
}