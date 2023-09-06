package id.dayona.eleanorpokemondatabase.data.service

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Lazy
import id.dayona.eleanorpokemondatabase.MainActivity
import id.dayona.eleanorpokemondatabase.R
import id.dayona.eleanorpokemondatabase.data.repository.LocationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale

interface EleanorServiceUtils {
    val eleanorService: EleanorService
    val locationRepository: Lazy<LocationRepository>
    val serviceScope: CoroutineScope
    var notif: NotificationCompat.Builder
    var notifManager: NotificationManager
    val client: FusedLocationProviderClient
    var updateNotif: NotificationCompat.Builder

    @SuppressLint("MissingPermission")
    fun start() {
        val notifyIntent = Intent(eleanorService, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val notifyPendingIntent = PendingIntent.getActivity(
            eleanorService, 0, notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        notif = NotificationCompat.Builder(eleanorService, "Location")
            .setContentTitle("Location")
            .setContentText("Please wait....")
            .setSmallIcon(R.drawable.ic_launcher_background).setContentIntent(notifyPendingIntent)
            .setOngoing(true)
        notifManager =
            eleanorService.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        serviceScope.launch {
            locationRepository.get().getLocationUpdate(10000, client).collectLatest {
                val geocoder = Geocoder(locationRepository.get().getContext(), Locale.getDefault())
                val addr =
                    geocoder.getFromLocation(
                        it.latitude,
                        it.longitude,
                        1
                    )?.last()?.getAddressLine(0)
                val fixAddr =
                    addr?.split(",")?.subList(1, addr.split(",").size)?.joinToString(",")?.trim()
                updateNotif = notif.setContentText("$fixAddr")
                    .setContentTitle("${fixAddr?.split(",")?.first()}")
                    .setStyle(NotificationCompat.BigTextStyle().bigText("$fixAddr"))
                notifManager.notify(1, updateNotif.build())
            }
        }
        eleanorService.startForeground(1, notif.build())
    }

    fun stop() {
        eleanorService.stopForeground(Service.STOP_FOREGROUND_REMOVE)
        eleanorService.stopSelf()
    }

}