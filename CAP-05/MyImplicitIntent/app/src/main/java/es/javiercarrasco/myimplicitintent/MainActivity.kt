package es.javiercarrasco.myimplicitintent

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
                Uri.parse("https://www.javiercarrasco.es")
            )

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Log.d("DEBUG", "Hay un problema para encontrar un navegador.")
            }
        }

        // Realizar una llamada telefónica.
        binding.btnCallphone.setOnClickListener {
            // Se comprueba si el permiso en cuestión está concedido.
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Log.d("DEBUG", "El permiso ya está concedido.")
                val intent = Intent(
                    Intent.ACTION_CALL,
                    Uri.parse("tel:965555555")
                )
                startActivity(intent)
            } else {
                gestionPermisos = GestionPermisos(
                    this,
                    Manifest.permission.CALL_PHONE,
                    MY_PERMISSIONS_REQUEST_CODE
                )
                gestionPermisos.checkPermissions()
            }
        }

        // Abrir la cámara de fotos.
        binding.btnTakepicture.setOnClickListener {
            // Se comprueba si el permiso en cuestión está concedido.
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Log.d("DEBUG", "El permiso ya está concedido.")
                val intent = Intent(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
                startActivity(intent)
            } else {
                gestionPermisos = GestionPermisos(
                    this,
                    Manifest.permission.CAMERA,
                    MY_PERMISSIONS_REQUEST_CODE
                )
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

        // Enviar un SMS.
        binding.btnSendSMS.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_SENDTO,
                Uri.parse("smsto:" + 777666777)
            )
            intent.putExtra("sms_body", "Cuerpo del mensaje")

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }

        // Establecer una alarma.
        binding.btnSetAlarm.setOnClickListener {
            val intent = Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, "Se acabó dormir")
                .putExtra(AlarmClock.EXTRA_HOUR, 7)
                .putExtra(AlarmClock.EXTRA_MINUTES, 45)

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }

        // Botón otras opciones.
        binding.btnOtheroption?.let {
            val estado = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                this.display?.rotation
            } else {
                @Suppress("DEPRECATION")
                this.windowManager.defaultDisplay.rotation
            }

            it.setOnClickListener {
                Toast.makeText(
                    this,
                    "Pulsado el botón \"${getString(R.string.button_otheroption)}\". Estado: ${estado}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        // Enviar un correo electrónico.
        binding.btnSendEmail.setOnClickListener {
            val TO = arrayOf("javier@javiercarrasco.es","javier@javiercarrasco.com")
            val CC = arrayOf("")

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/html" // o también text/plain

            intent.putExtra(Intent.EXTRA_EMAIL, TO)
            intent.putExtra(Intent.EXTRA_CC, CC)
            intent.putExtra(Intent.EXTRA_SUBJECT, "Envío de un email desde Kotlin")
            intent.putExtra(Intent.EXTRA_TEXT, "Esta es mi prueba de envío de un correo.")

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(Intent.createChooser(intent, "Enviar correo..."))
            }
        }

        // Hacer una foto y recuperarla.
        binding.btnTakepicture2.setOnClickListener {
            // Se comprueba si el permiso en cuestión está concedido.
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Log.d("DEBUG", "El permiso ya está concedido.")

                val intent = Intent(Intent(MediaStore.ACTION_IMAGE_CAPTURE))

                if (intent.resolveActivity(packageManager) != null) {
                    startActivityForResult(intent, MY_PERMISSIONS_REQUEST_CODE)
                }
            } else {
                gestionPermisos = GestionPermisos(
                    this,
                    Manifest.permission.CAMERA,
                    MY_PERMISSIONS_REQUEST_CODE
                )
                gestionPermisos.checkPermissions()
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

    // Se recupera la imagen capturada.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === MY_PERMISSIONS_REQUEST_CODE && resultCode === RESULT_OK) {
            val thumbnail: Bitmap = data?.getParcelableExtra("data")!!
            binding.imageView.setImageBitmap(thumbnail)
        }
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
                            == PackageManager.PERMISSION_GRANTED)
                ) {
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
