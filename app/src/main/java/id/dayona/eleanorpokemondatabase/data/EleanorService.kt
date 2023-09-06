package id.dayona.eleanorpokemondatabase.data

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import id.dayona.eleanorpokemondatabase.R
import id.dayona.eleanorpokemondatabase.data.model.PokemonIdModel
import id.dayona.eleanorpokemondatabase.data.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@AndroidEntryPoint
class EleanorService : Service() {
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @Inject
    lateinit var repository: Lazy<Repository>
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
        client = LocationServices.getFusedLocationProviderClient(repository.get().getContext())
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
        notif = NotificationCompat.Builder(repository.get().getContext(), "Location")
            .setContentTitle("Please wait..")
            .setContentText("Pokemon : ")
            .setSmallIcon(R.drawable.ic_launcher_background).setOngoing(true)
        notifManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        serviceScope.launch {
            repository.get().pokeList(10, 30).collectLatest { res ->
                when (res) {
                    is ApiSuccess -> {
                        var data = ""
                        repeat(res.data.results?.size ?: 0) { i ->
                            delay(1.seconds)
                            val url =
                                res.data.results!![i]?.url!!.replace(
                                    "https://pokeapi.co/api/v2/",
                                    ""
                                )
                            getPokeId(url = url) { s, p ->
                                if (!s) this.cancel()
                                if (s) {
                                    data = "$i ${p?.name}"
                                    updateNotif = notif.setContentText("Pokemon : $data")
                                    notifManager.notify(1, updateNotif.build())
                                }
                                if (i > 8) {
                                    this.cancel()
                                    stop()
                                }
                            }
                        }
                    }

                    is ApiError -> {
                        updateNotif = notif.setContentText("${res.message}")
                        notifManager.notify(1, updateNotif.build())
                    }


                    is ApiException -> {
                        updateNotif = notif.setContentText(res.e)
                        notifManager.notify(1, updateNotif.build())
                    }

                    is ApiLoading -> {
                        updateNotif = notif.setContentText(res.message)
                        notifManager.notify(1, updateNotif.build())
                    }
                }
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

    private suspend fun getPokeId(url: String, onSuccess: (Boolean, PokemonIdModel?) -> Unit) {
        repository.get().pokemonByUrl(url).collectLatest { res ->
            when (res) {
                is ApiSuccess -> {
                    onSuccess(true, res.data)
                }

                is ApiError -> {
                    updateNotif = notif.setContentText("${res.message}")
                    notifManager.notify(1, updateNotif.build())
                }


                is ApiException -> {
                    updateNotif = notif.setContentText(res.e)
                    notifManager.notify(1, updateNotif.build())
                }

                is ApiLoading -> {
                    updateNotif = notif.setContentText(res.message)
                    notifManager.notify(1, updateNotif.build())
                }
            }
        }
    }
}
