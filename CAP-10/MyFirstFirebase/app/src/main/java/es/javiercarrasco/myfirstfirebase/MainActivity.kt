package es.javiercarrasco.myfirstfirebase

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import es.javiercarrasco.myfirstfirebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference

    data class User(
        var name: String? = "",
        var surname: String? = ""
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se establece la referencia a la base de datos.
        database = FirebaseDatabase.getInstance().reference

        // Se obtiene la referencia al gato.
        val dbfGato = database.child("mascotas")
            .child("gato")

        dbfGato.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.e("onCancelled", "Error!", error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                binding.tvNombre.text = "Nombre: ${snapshot.child("nombre").value}"
                binding.tvRaza.text = "Raza: ${snapshot.child("raza").value}"
            }
        })
    }

    override fun onStart() {
        super.onStart()

        // Añadir usuarios a Firebase.
        binding.btnAddUsers.setOnClickListener {
            val users: MutableList<User> = ArrayList()

            users.add(User("Javier", "Carrasco"))
            users.add(User("Nacho", "Cabanes"))
            users.add(User("Patricia", "Aracil"))
            users.add(User("Juan", "Palomo"))
            users.add(User("Raquel", "Sánchez"))

            database.child("usuarios").setValue(users)
        }

        // Actualizar usuario.
        binding.btnUpdateUser.setOnClickListener {
            val userUpdate = HashMap<String, Any>()
            userUpdate["0"] = User("Javi", "Hernández")

            database.child("usuarios").updateChildren(userUpdate)
        }

        // Eliminar usuario.
        binding.btnDelUser.setOnClickListener {
            database.child("usuarios")
                .child("0")
                .removeValue()
        }
    }
}