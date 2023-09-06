package id.dayona.eleanorpokemondatabase.data.service

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import id.dayona.eleanorpokemondatabase.data.ACTION_START
import id.dayona.eleanorpokemondatabase.data.ACTION_STOP
import id.dayona.eleanorpokemondatabase.data.repository.LocationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import javax.inject.Inject

@AndroidEntryPoint
class EleanorService : Service(), EleanorServiceUtils {
    override val eleanorService = this
    override val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @Inject
    override lateinit var locationRepository: Lazy<LocationRepository>
    override lateinit var client: FusedLocationProviderClient
    override lateinit var notif: NotificationCompat.Builder
    override lateinit var updateNotif: NotificationCompat.Builder
    override lateinit var notifManager: NotificationManager
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

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}
