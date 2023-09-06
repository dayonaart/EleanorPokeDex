package id.dayona.eleanorpokemondatabase.data

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import id.dayona.eleanorpokemondatabase.R
import id.dayona.eleanorpokemondatabase.data.repository.LocationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class EleanorService : Service() {
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @Inject
    lateinit var locationRepository: Lazy<LocationRepository>
    lateinit var client: FusedLocationProviderClient
    private lateinit var notif: NotificationCompat.Builder
    private lateinit var updateNotif: NotificationCompat.Builder
    private lateinit var notifManager: NotificationManager

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        client =
            LocationServices.getFusedLocationProviderClient(locationRepository.get().getContext())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> {
                start()
            }

            ACTION_STOP -> {
                stop()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("MissingPermission")
    private fun start() {
        notif = NotificationCompat.Builder(locationRepository.get().getContext(), "Location")
            .setContentTitle("Location")
            .setContentText("Please wait....")
            .setSmallIcon(R.drawable.ic_launcher_background).setOngoing(true)
        notifManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
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
                Log.d(NORMAL_TAG, "$fixAddr")
                updateNotif = notif.setContentText("$fixAddr")
                notifManager.notify(1, updateNotif.build())
            }
        }
        startForeground(1, notif.build())
    }

    private fun stop() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}
