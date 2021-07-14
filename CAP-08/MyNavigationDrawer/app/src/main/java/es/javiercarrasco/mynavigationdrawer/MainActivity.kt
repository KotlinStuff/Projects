package es.javiercarrasco.mynavigationdrawer

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import es.javiercarrasco.mynavigationdrawer.adapters.ViewPager2Adapter
import es.javiercarrasco.mynavigationdrawer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se carga la barra de acción.
        setSupportActionBar(binding.myToolBar)

        // Se añade el botón hamburgesa a la toolbar y
        // se vincula con el DrawerLayout.
        val toggle = ActionBarDrawerToggle(
            this,
            binding.myDrawerLayout,
            binding.myToolBar,
            R.string.txt_open,
            R.string.txt_close
        )
        binding.myDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Se crea el adapter.
        val adapter = ViewPager2Adapter(supportFragmentManager, lifecycle)

        // Se crean los fragments.
        adapter.addFragment(
            createFragment(
                "Pantalla inicio",
                "Esta es la pantalla de inicio de la aplicación"
            )
        )
        adapter.addFragment(
            createFragment(
                "Segunda pantalla",
                "Esta es la segunda pantalla de la aplicación"
            )
        )
        adapter.addFragment(
            createFragment(
                "Tercera pantalla",
                "Esta es la tercera pantalla de la aplicación"
            )
        )
        adapter.addFragment(
            createFragment(
                "Cuarta pantalla",
                "Esta es la cuarta pantalla de la aplicación"
            )
        )

        // Se asocia el adapter al ViewPager2.
        binding.myViewPager2.adapter = adapter

        // Control sobre la opción seleccionada.
        binding.myNavigationView.setNavigationItemSelectedListener {

            it.isChecked = true

            when (it.itemId) {
                R.id.item11 -> {
                    // Se carga el fragment en el ViewPager2.
                    binding.myViewPager2.setCurrentItem(0)

                    // Se cierra el Drawer Layout.
                    binding.myDrawerLayout.close()
                    true
                }
                R.id.item12 -> {
                    binding.myViewPager2.setCurrentItem(1)

                    binding.myDrawerLayout.close()
                    true
                }
                R.id.item13 -> {
                    binding.myViewPager2.setCurrentItem(2)

                    binding.myDrawerLayout.close()
                    true
                }
                R.id.item14 -> {
                    binding.myViewPager2.setCurrentItem(3)

                    binding.myDrawerLayout.close()
                    true
                }
                R.id.item21 -> {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.txtMenu21),
                        Toast.LENGTH_SHORT
                    ).show()

                    binding.myDrawerLayout.close()
                    true
                }
                R.id.item22 -> {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.txtMenu22),
                        Toast.LENGTH_SHORT
                    ).show()

                    binding.myDrawerLayout.close()
                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed() {
        if(binding.myDrawerLayout.isOpen){
            binding.myDrawerLayout.close()
        }else super.onBackPressed()
    }

    // Método encargado de crear fragments.
    private fun createFragment(name: String, texto: String): Fragment {
        val fragment = PageFragment()
        val bundle = Bundle()

        bundle.putString("name", name)
        bundle.putString("texto", texto)
        fragment.arguments = bundle
        return fragment
    }
}