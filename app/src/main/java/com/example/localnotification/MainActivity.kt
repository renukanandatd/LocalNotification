package com.example.localnotification

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.example.localnotification.databinding.ActivityMainBinding
import java.nio.file.attribute.AclEntry.Builder

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    val CHANNEL_ID = "1"
    var counter = 0
    val PERMISSION_REQUEST_CODE = 1

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        mainBinding.notification.setOnClickListener {
            counter++
            mainBinding.notification.text=counter.toString()
            if(counter%5==0){
                startNotification()
            }
        }
    }


    fun startNotification(){
        val builder = NotificationCompat.Builder(this@MainActivity, CHANNEL_ID)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel =
                    NotificationChannel(CHANNEL_ID, "1", NotificationManager.IMPORTANCE_DEFAULT)

                val manager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                builder.setSmallIcon(R.drawable.notification)
                    .setContentTitle("Local Notification")
                    .setContentText("This number is divisible by 5")
            } else {

                builder.setSmallIcon(R.drawable.notification)
                    .setContentTitle("Local Notification")
                    .setContentText("This number is divisible by 5")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            }
            val notificationManager = NotificationManagerCompat.from(this@MainActivity)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(1, builder.build())

    }
}