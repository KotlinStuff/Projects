package es.javiercarrasco.mynotifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import es.javiercarrasco.mynotifications.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val CHANNELID1 = "es.javiercarrasco.mynotifications"
    private val notificationId1 = 123456
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se registra el canal de notificación en el sistema.
        createNotificationChannel(
            CHANNELID1,
            R.string.txt_channel1_name,
            R.string.txt_channel1_desc,
            NotificationManager.IMPORTANCE_DEFAULT
        )

        // Se crea el intent que deberá abrirse al pulsarse la notificación.
        val intent = Intent(this, RequestNotification::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        // Se crea una notificación utilizando el builder de NotificationCompat.
        val builder = NotificationCompat.Builder(this, CHANNELID1)
        builder.apply {
            setSmallIcon(R.mipmap.ic_launcher_round)
            setContentTitle("Mi primera notificación")
            setContentText("Esta será la primera notificación creada.")
            priority = NotificationCompat.PRIORITY_DEFAULT
            // Nuevas propiedades para indicar la acción
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }

        binding.btnPrimeraNotificacion.setOnClickListener {
            with(NotificationManagerCompat.from(this)) {
                notify(notificationId1, builder.build())
            }
        }
    }

    /**
     * Método encargado de registrar los caneles de notificaciones.
     */
    private fun createNotificationChannel(
        channel: String, name: Int,
        desc: Int, importance: Int
    ) {
        // Se crea el canal de notificación únicamente para API 26+.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(name)
            val descriptionText = getString(desc)
            val channel = NotificationChannel(channel, name, importance).apply {
                description = descriptionText
            }

            // Se registra el canal en el sistema.
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE)
                        as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}