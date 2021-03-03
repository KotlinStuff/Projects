package es.javiercarrasco.mycloudfirebase2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import es.javiercarrasco.mycloudfirebase2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val collComunidades = db.collection("Comunidades")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        // Se obtienen todos los documentos de la colección (con escucha)
        // y se rellena el TextView.
        collComunidades.addSnapshotListener { querySnapshot, e ->
            if (e != null) {
                Log.w("DOC_SHOW", "Escucha fallida!.", e)
                return@addSnapshotListener
            }
            binding.tvData.text = ""
            for (document in querySnapshot!!) {
                Log.d("DOC_SHOW", "${document.id} => ${document.data}")
                binding.tvData.append("${document!!["comunidad"]}\n" +
                        "\tCapital: ${document["datos.capital"]}\n" +
                        "\tHabs: ${document["datos.habitantes"]}\n" +
                        "\tProvincias: ${document["provincias"]}\n\n"
                )
            }
        }

        // Botón para añadir documentos a la colección.
        binding.btnAdd.setOnClickListener {
            // Se prepara la estructura de datos.
            val comunidades = listOf(
                mapOf( // Comunidad 1
                    "comunidad" to "Andalucía",
                    "datos" to mapOf(
                        "capital" to "Sevilla",
                        "habitantes" to 709000
                    ),
                    "provincias" to listOf(
                        "Almería",
                        "Granada",
                        "Córdoba",
                        "Jaén",
                        "Sevilla",
                        "Málaga",
                        "Cádiz",
                        "Huelva"
                    )
                ), mapOf( // Comunidad 2
                    "comunidad" to "Comunidad Valenciana",
                    "datos" to mapOf(
                        "capital" to "Valencia",
                        "habitantes" to 791413
                    ),
                    "provincias" to listOf(
                        "Castellón",
                        "Valencia",
                        "Alicante"
                    )
                ), mapOf( // Comunidad 3
                    "comunidad" to "Aragón",
                    "datos" to mapOf(
                        "capital" to "Zaragoza",
                        "habitantes" to 680895
                    ),
                    "provincias" to listOf(
                        "Huesca",
                        "Zaragoza",
                        "Teruel"
                    )
                )
            )

            // Se añade documento a documento.
            for (doc in comunidades) {
                Log.d("DOC_ADD", "Añadiendo documento " + doc["comunidad"])
                collComunidades.document(doc["comunidad"].toString()).set(doc)
                    .addOnSuccessListener {
                        Log.d("DOC_ADD", "Documento añadido correctamente")
                    }
                    .addOnFailureListener { e ->
                        Log.w("DOC_ADD", "Error al añadir el documento", e)
                    }
            }
        }

        // Botón para eliminar documentos de la colección.
        binding.btnDel.setOnClickListener {
            borraDocumento("Andalucía")
            borraDocumento("Comunidad Valenciana")
            borraDocumento("Aragón")
        }
    }

    // Borra el documento indicando el ID por parámetro.
    fun borraDocumento(id: String) {
        // Elimina documento a documento, para ello,
        // se necesita conocer su identificador.
        collComunidades.document(id)
            .delete()
            .addOnSuccessListener {
                Log.d("DOC_DEL", "Documento eliminado correctamente")
            }
            .addOnFailureListener { e ->
                Log.w("DOC_DEL", "Error al eliminar el documento", e)
            }
    }
}