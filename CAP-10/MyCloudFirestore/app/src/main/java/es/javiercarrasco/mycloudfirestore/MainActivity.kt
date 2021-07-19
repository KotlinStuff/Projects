package es.javiercarrasco.mycloudfirestore

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import es.javiercarrasco.mycloudfirestore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        val profesCollection: CollectionReference = db.collection("profesores")

        // Obtener un documento concreto.
        val docRef: DocumentReference = profesCollection.document("1")

//        docRef.get().apply {
//            // Obtiene información, se lanza sin llegar a terminar la conexión.
//            addOnSuccessListener {
//                Log.d("addOnSuccessListener", "Cached document data: ${it.data}")
//                val texto = it["modulo"].toString() + " - " +
//                        it["nombre"].toString() + " " +
//                        it["apellido"].toString()
//                binding.tvShowData.text = texto
//            }
//
//            // Fallo de lectura.
//            addOnFailureListener { exception ->
//                Log.d("addOnFailureListener", "Fallo de lectura ", exception)
//            }
//        }

//        docRef.get().apply {
//            addOnCompleteListener {
//                if (it.isSuccessful) {
//                    // Documento encontrado en la caché offline.
//                    val document = it.result
//                    Log.d("addOnCompleteListener", "Document data: ${document?.data}")
//                    val texto = document!!["modulo"].toString() + " - " +
//                            document["nombre"].toString() + " " +
//                            document["apellido"].toString()
//                    binding.tvShowData.text = texto
//                } else {
//                    Log.d("addOnCompleteListener", "Fallo de lectura ", it.exception)
//                }
//            }
//        }

        // Escucha del documento, contrasta la caché con la base de datos.
//        docRef.addSnapshotListener { document, e ->
//            // Se comprueba si hay fallo.
//            if (e != null) {
//                Log.w("addSnapshotListener", "Escucha fallida!", e)
//                return@addSnapshotListener
//            }
//
//            if (document != null && document.exists()) {
//                Log.d("addSnapshotListener", "Información actual: ${document.data}")
//                val texto = document["modulo"].toString() + " - " +
//                        document["nombre"].toString() + " " +
//                        document["apellido"].toString()
//                binding.tvShowData.text = texto
//            } else {
//                Log.d("addSnapshotListener", "Información actual: null")
//            }
//        }

        // Obtiene todos los documentos de una colección (sin escucha).
//        profesCollection.get().apply {
//            addOnSuccessListener {
//                for (document in it) {
//                    Log.d("DOC", "${document.id} => ${document.data}")
//
//                    binding.tvShowData.append(
//                        document!!["modulo"].toString() + " - " +
//                                document["nombre"].toString() + " " +
//                                document["apellido"].toString() + "\n"
//                    )
//                }
//            }
//
//            addOnFailureListener { exception ->
//                Log.d(
//                    "DOC",
//                    "Error durante la recogida de documentos: ",
//                    exception
//                )
//            }
//        }

        // Obtiene todos los documentos de una colección (con escucha).
//        profesCollection.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
//            if (firebaseFirestoreException != null) {
//                Log.w(
//                    "addSnapshotListener",
//                    "Escucha fallida!.",
//                    firebaseFirestoreException
//                )
//                return@addSnapshotListener
//            }
//
//            binding.tvShowData.text = ""
//            for (document in querySnapshot!!) {
//                Log.d("DOC", "${document.id} => ${document.data}")
//
//                binding.tvShowData.append(
//                    document!!["modulo"].toString() + " - " +
//                            document["nombre"].toString() + " " +
//                            document["apellido"].toString() + "\n"
//                )
//            }
//        }

        // Obtiene todos los documentos de una colección (sin escucha).
        profesCollection.whereEqualTo("nombre", "Javier")
            .whereEqualTo("modulo", "PMDM").get().apply {
                addOnSuccessListener {
                    binding.tvShowData.text = "Sin escucha\n"
                    for (document in it) {
                        Log.d("DOC", "${document.id} => ${document.data}")

                        binding.tvShowData.append(
                            document!!["modulo"].toString() + " - " +
                                    document["nombre"].toString() + " " +
                                    document["apellido"].toString() + "\n"
                        )
                    }
                }

                addOnFailureListener { exception ->
                    Log.d(
                        "DOC",
                        "Error durante la recogida de documentos: ",
                        exception
                    )
                }
            }

        // Obtiene todos los documentos de una colección (con escucha).
        profesCollection.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                Log.w(
                    "addSnapshotListener",
                    "Escucha fallida!.",
                    firebaseFirestoreException
                )
                return@addSnapshotListener
            }

            binding.tvShowData2.text = "Con escucha\n"
            for (document in querySnapshot!!) {
                Log.d("DOC", "${document.id} => ${document.data}")

                binding.tvShowData2.append(
                    document!!["modulo"].toString() + " - " +
                            document["nombre"].toString() + " " +
                            document["apellido"].toString() + "\n"
                )
            }
        }

        // Funcionamiento del botón Añadir (versión 1).
        binding.btnAdd.setOnClickListener {
            // Se crea la estructura del documento.
            val profe = hashMapOf(
                "nombre" to "Isabel",
                "apellido" to "Díaz",
                "modulo" to "FOL"
            )

            // Se añade el documento sin indicar ID, dejando que Firebase genere el ID
            // al añadir el documento. Para esta acción se recomienda add().
            profesCollection.document().set(profe)
                // Respuesta si ha sido correcto.
                .addOnSuccessListener {
                    Log.d("DOC_SET", "Documento añadido!")
                }

                // Respuesta si se produce un fallo.
                .addOnFailureListener { e ->
                    Log.w("DOC_SET", "Error en la escritura", e)
                }
        }

        // Funcionamiento del botón Añadir (versión 2).
//        binding.btnAdd.setOnClickListener {
//            // Se selecciona la colección o la crea si no existe.
//            val topicsCollection = db.collection("modulos")
//
//            val modulo = hashMapOf(
//                "abreviatura" to "PMDM",
//                "nombre" to "Programación Multimedia y Dispositivos Móviles"
//            )
//
//            topicsCollection
//                .add(modulo)
//                .addOnSuccessListener {
//                    Log.d("DOC_ADD", "Documento añadido, id: ${it.id}")
//                }
//                .addOnFailureListener { e ->
//                    Log.w("DOC_ADD", "Error añadiendo el documento", e)
//                }
//        }
    }
}