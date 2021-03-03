package es.javiercarrasco.mynotifications

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.javiercarrasco.mynotifications.databinding.ActivityRequestNotificationBinding

class RequestNotification : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRequestNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title ="Activity Notification"
    }
}