package es.javiercarrasco.captureandsave

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.GridLayoutManager
import es.javiercarrasco.captureandsave.adapters.ImageRVAdapter
import es.javiercarrasco.captureandsave.databinding.ActivityMainBinding
import es.javiercarrasco.captureandsave.databinding.ImageItemBinding
import es.javiercarrasco.captureandsave.utils.ManageFiles
import es.javiercarrasco.captureandsave.utils.ManagePermissions
import java.io.File

const val PERMISSION_CAMERA = 100

class MainActivity : AppCompatActivity(), ImageRVAdapter.ItemLongClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var managePermissions: ManagePermissions

    private val myAdapter: ImageRVAdapter = ImageRVAdapter()
    private val images: MutableList<File> = ArrayList()

    // Variable para crear el nombre del archivo.
    private var photoFile: File? = null

    // Método sobrecargado de MyContactsRecyclerAdapter encargado
    // de actulizar el RV tras eliminar.
    override fun onItemLongClick(view: View?, position: Int) {
        val bindItem = ImageItemBinding.bind(view!!)

        if (images.get(position).delete()) {
            bindItem.root.setBackgroundColor(Color.RED)
            images.removeAt(position)
            myAdapter.notifyItemRemoved(position)
        }
    }

    // Se evalúa el resultado obtenido de la cámara.
    var resultTakePicture = registerForActivityResult(StartActivityForResult())
    { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            images.add(photoFile!!)
            photoFile = null
            myAdapter.notifyItemInserted(images.size)
        } else { // No se captura imagen.
            Toast.makeText(
                applicationContext,
                R.string.info_picture_canceled,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Botón encargado de lanzar la cámara para capturar la imagen.
        binding.btnAddPicture.setOnClickListener {
            // Se comprueba si el permiso en cuestión está concedido.
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Log.d("DEBUG", "El permiso ya está concedido.")

                // Se crea el fichero donde se guardará la imagen.
                photoFile = ManageFiles().createImageFile(this)
                Log.d("RUTA", photoFile!!.absolutePath.toString())

                val fileProvider =
                    FileProvider.getUriForFile( // En base al provider creado en el Manifest.
                        this,
                        "es.javiercarrasco.captureandsave",
                        photoFile!!
                    )

                // Se crea el intent y se le pasa el contenedor del fichero a recuperar.
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                    putExtra(
                        MediaStore.EXTRA_OUTPUT, fileProvider
                    )
                }

                resultTakePicture.launch(intent)
            } else {
                managePermissions = ManagePermissions(
                    this,
                    Manifest.permission.CAMERA,
                    PERMISSION_CAMERA
                )
                managePermissions.checkPermissions()
            }
        }

        images.addAll(ManageFiles().listFiles(this))
        setUpRecyclerView()
    }

    // Método encargado de configurar el RV.
    private fun setUpRecyclerView() {
        // Esta opción a TRUE significa que el RV tendrá
        // hijos del mismo tamaño, optimiza su creación.
        binding.rvImages.setHasFixedSize(true)

        // Se indica el contexto para RV en forma de grid.
        binding.rvImages.layoutManager = GridLayoutManager(this, 3)

        // Se activa el listener para capturar los eventos.
        myAdapter.setLongClickListener(this)

        // Se genera el adapter.
        myAdapter.ImageRVAdapter(images, this)

        // Se asigna el adapter al RV.
        binding.rvImages.adapter = myAdapter
    }
}