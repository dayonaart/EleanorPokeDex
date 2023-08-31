package id.dayona.eleanorpokemondatabase.data

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlin.concurrent.thread

class EleanorServices : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        thread {
            while (true) {
                println("Services is running")
                Thread.sleep(2000L)
            }
        }
    }
}