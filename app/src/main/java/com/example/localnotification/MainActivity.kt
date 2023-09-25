package com.example.localnotification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.example.localnotification.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    val CHANNEL_ID = "1"
    var counter = 0
    val PERMISSION_REQUEST_CODE = 1
    val NOTIFICATION_ID = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        mainBinding.notification.setOnClickListener {
            counter++
            mainBinding.notification.text = counter.toString()
            if (counter % 5 == 0) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_NOTIFICATION_POLICY
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_NOTIFICATION_POLICY),
                        PERMISSION_REQUEST_CODE
                    )
                } else {
                    startNotification()
                }
            }
        }
    }

    fun startNotification() {
        val builder = NotificationCompat.Builder(this@MainActivity, CHANNEL_ID)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(CHANNEL_ID, "Channel Name", NotificationManager.IMPORTANCE_DEFAULT)

            val manager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            manager.createNotificationChannel(channel)
        }

        builder.setSmallIcon(R.drawable.notification)
            .setContentTitle("Local Notification")
            .setContentText("This number is divisible by 5")

        val notificationManager = NotificationManagerCompat.from(this@MainActivity)
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            startNotification()
        }
    }
}
