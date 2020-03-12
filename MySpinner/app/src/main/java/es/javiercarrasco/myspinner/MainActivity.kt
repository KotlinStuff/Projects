package es.javiercarrasco.myspinner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Se crea el Adapter, uniendo la fuente de datos con una vista
        // por defecto para el spinner de Android.
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.array_nombres,
            android.R.layout.simple_spinner_item
        )

        // Se especifica el diseño que debe utilizarse para mostrar la lista.
        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        // Se carga el Adapter en el Spinner.
        mySpinner.adapter = adapter

        mySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onNothingSelected(p0: AdapterView<*>?) {}

                override fun onItemSelected(
                    p0: AdapterView<*>?, p1: View?,
                    p2: Int, p3: Long
                ) {
                    if (p2 == 0)
                        Toast.makeText(
                            applicationContext,
                            "Ningún elemento seleccionado.",
                            Toast.LENGTH_LONG
                        ).show()
                    else Toast.makeText(
                        applicationContext,
                        "Seleccionado ${mySpinner.getItemAtPosition(p2)}!",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d("MySpinner", "Selected ${mySpinner.getItemAtPosition(p2)}!")
                }
            }

    }
}
