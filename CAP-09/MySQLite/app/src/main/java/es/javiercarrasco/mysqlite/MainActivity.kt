package es.javiercarrasco.mysqlite

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.mysqlite.databinding.ActivityMainBinding

const val UPDATE = "update"
const val DELETE = "delete"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var amigosDBHelper: MyDBOpenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se instancia el objeto MyDBOpenHelper.
        amigosDBHelper = MyDBOpenHelper(this, null)

        with(binding) {
            // Botón INSERTAR.
            btnInsertar.setOnClickListener {
                if (etNombre.text.isNotBlank() && etApes.text.isNotBlank()) {
                    // Se inserta en la tabla.
                    amigosDBHelper.addAmigo(
                        etNombre.text.toString(),
                        etApes.text.toString()
                    )

                    // Se limpian los EditText después de la inserción.
                    etNombre.text.clear()
                    etApes.text.clear()
                } else {
                    myToast("Los campos no pueden estar vacíos.")
                }
            }

            // Botón ACTUALIZAR.
            btnActualizar.setOnClickListener {
                if (etNombre.text.isNotBlank()) {
                    // Se lanza el dialogo para solicitar el id del registro,
                    // además, se indica el tipo de operación.
                    solicitaIdentificador(UPDATE)
                } else {
                    myToast("El campo nombre no debe estar vacío.")
                }
            }

            // Botón ELIMINAR.
            btnEliminar.setOnClickListener {
                // Se lanza el dialogo para solicitar el id del registro,
                // además, se indica el tipo de operación.
                solicitaIdentificador(DELETE)
            }

            // Botón CONSULTAR.
            btnConsultar.setOnClickListener {
                tvResult.text = ""

                // Se instancia la BD en modo lectura y se crea la SELECT.
                val db: SQLiteDatabase = amigosDBHelper.readableDatabase
                val cursor: Cursor = db.rawQuery(
                    "SELECT * FROM ${MyDBOpenHelper.TABLA_AMIGOS};",
                    null
                )

                // Se comprueba que al menos exista un registro.
                if (cursor.moveToFirst()) {
                    do {
                        tvResult.append(cursor.getInt(0).toString() + " - ")
                        tvResult.append(cursor.getString(1).toString() + " ")
                        tvResult.append(cursor.getString(2).toString() + "\n")
                    } while (cursor.moveToNext())
                } else {
                    myToast("No existen datos a mostrar.")
                }
                db.close()
            }

            // Botón VER EN LISTVIEW.
            btnVerListview.setOnClickListener {
                val myIntent = Intent(this@MainActivity, ListviewActivity::class.java)
                startActivity(myIntent)
            }

            // Botón VER EN RECYCLERVIEW.
            btnVerRecyclerview.setOnClickListener {
                val myIntent = Intent(this@MainActivity, RecyclerviewActivity::class.java)
                startActivity(myIntent)
            }
        }
    }

    /**
     * Método encargado de mostrar un cuadro de diálogo para pedir el
     * identificador al usuario y realizar la acción correspondiente según
     * la acción requerida.
     */
    fun solicitaIdentificador(accion: String) {

        // Se infla la vista para el diálogo.
        val myDialogView = LayoutInflater.from(this@MainActivity)
            .inflate(R.layout.dialogo, null)

        // Se crea el builder.
        val builder = AlertDialog.Builder(this)
            .setView(myDialogView)

        builder.apply {
            setPositiveButton(android.R.string.ok) { dialog, _ ->
                val valor = myDialogView
                    .findViewById<EditText>(R.id.identificador).text
                val identificador = valor.toString().toInt()

                // Se realiza la acción.
                when (accion) {
                    UPDATE -> {
                        val nombre = binding.etNombre.text.toString()
                        amigosDBHelper.updateAmigo(identificador, nombre)

                        // Se limpian los EditText después de la inserción.
                        binding.etNombre.text.clear()
                        binding.etApes.text.clear()
                    }
                    DELETE -> {
                        myToast(
                            "Eliminado/s " +
                                    "${amigosDBHelper.delAmigo(identificador)} " +
                                    "registro/s"
                        )
                    }
                }
            }
            setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
        }.show()
    }

    fun myToast(mensaje: String) {
        Toast.makeText(
            this@MainActivity,
            mensaje,
            Toast.LENGTH_SHORT
        ).show()
    }
}