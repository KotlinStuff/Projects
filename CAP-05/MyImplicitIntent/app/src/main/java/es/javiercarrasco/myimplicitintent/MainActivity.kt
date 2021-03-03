package es.javiercarrasco.myimplicitintent

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import es.javiercarrasco.myimplicitintent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Constante que contiene el valor asignado al permiso de la app.
    // private val MY_PERMISSIONS_REQUEST_CALL_PHONE = 234
    private val MY_PERMISSIONS_REQUEST_CODE = 234

    // ViewBinding
    private lateinit var binding: ActivityMainBinding
    private lateinit var gestionPermisos: GestionPermisos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Abrir una página web.
        binding.btnWebpage.setOnClickListener {
            val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://www.javiercarrasco.es")
            )
            startActivity(intent)
        }

        // Realizar una llamada telefónica.
        binding.btnCallphone.setOnClickListener {
            // Se comprueba si el permiso en cuestión está concedido.
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_GRANTED) {
                Log.d("DEBUG", "El permiso ya está concedido.")
                val intent = Intent(
                    Intent.ACTION_CALL,
                    Uri.parse("tel:965555555")
                )
                startActivity(intent)
            }else{
                gestionPermisos = GestionPermisos(this,
                    Manifest.permission.CALL_PHONE,
                    MY_PERMISSIONS_REQUEST_CODE)
                gestionPermisos.checkPermissions()
            }
        }

        // Abrir la cámara de fotos.
        binding.btnTakepicture.setOnClickListener {
            // Se comprueba si el permiso en cuestión está concedido.
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED) {
                Log.d("DEBUG", "El permiso ya está concedido.")
                val intent = Intent(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
                startActivity(intent)
            }else{
                gestionPermisos = GestionPermisos(this,
                    Manifest.permission.CAMERA,
                    MY_PERMISSIONS_REQUEST_CODE)
                gestionPermisos.checkPermissions()
            }
        }

        // Abrir Google Maps.
        binding.btnOpenmap.setOnClickListener {
            // Para abrir Google Maps, si no se requiere ubicación, no es
            // necesario solicitar permiso.
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("geo:0,0?q=Alicante")
                //Uri.parse("geo:38,3579029,-0,5076294")
            )
            startActivity(intent)
        }

        // Botón otras opciones.
        binding.btnOtheroption?.let {
            val estado: Int = this.windowManager.defaultDisplay.rotation

            it.setOnClickListener {
                Toast.makeText(
                    this,
                    "Pulsado el botón \"${getString(R.string.button_otheroption)}\".",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

/** VERSIÓN ANTERIOR
        // Realizar una llamada telefónica.
        binding.btnCallphone.setOnClickListener {
            // Se comprueba si el permiso en cuestión está concedido.
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                // Permiso no concedido.
                Log.d("DEBUG", "No está concedido el permiso para llamar")

                // Si el usuario ya ha rechazado al menos una vez (TRUE),
                // se da una explicación.
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this, Manifest.permission.CALL_PHONE)
                ) {
                    Log.d("DEBUG", "Se da una explicación")

                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Permiso para llamar")
                    builder.setMessage("Puede resultar interesante indicar porqué.")

                    // las variables dialog y which en este caso no se utilizan
                    // se podrían sustituir por _ cada una ({_, _ -> ...).
                    builder.setPositiveButton(android.R.string.ok) { dialog, which ->
                        Log.d("DEBUG", "Se acepta y se vuelve a pedir permiso")

                        ActivityCompat.requestPermissions(
                            this, arrayOf(Manifest.permission.CALL_PHONE),
                            MY_PERMISSIONS_REQUEST_CALL_PHONE
                        )
                    }

                    builder.setNeutralButton(android.R.string.cancel, null)
                    builder.show()
                } else {
                    // No requiere explicación, se pregunta por el permiso.
                    Log.d("DEBUG", "No se da una explicación.")

                    ActivityCompat.requestPermissions(
                        this, arrayOf(Manifest.permission.CALL_PHONE),
                        MY_PERMISSIONS_REQUEST_CALL_PHONE
                    )
                }
            }else{
                Log.d("DEBUG", "El permiso ya está concedido.")
                val intent = Intent(
                    Intent.ACTION_CALL,
                    Uri.parse("tel:965555555")
                )
                startActivity(intent)
            }
        } // Fin btnCallphone.setOnClickListener
FIN VERSIÓN ANTERIOR */

    }

    // Se analiza la respuesta del usuario a la petición de permisos.
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            MY_PERMISSIONS_REQUEST_CODE -> {
                Log.d("DEBUG", "${grantResults[0]} ${permissions[0]}")
                if ((grantResults.isNotEmpty() && grantResults[0]
                            == PackageManager.PERMISSION_GRANTED)) {
                    Log.d("DEBUG", "Permiso concedido!!")
                } else {
                    Log.d("DEBUG", "Permiso rechazado!!")
                }
                return
            }
            else -> {
                Log.d("DEBUG", "Se pasa de los permisos.")
            }
        }
    }
}
