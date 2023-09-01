package id.dayona.eleanorpokemondatabase.data.repoimpl

import android.app.Application
import android.content.Context
import android.os.Build
import id.dayona.eleanorpokemondatabase.data.repository.DeviceRepository
import javax.inject.Inject

class DeviceRepoImpl @Inject constructor(private val appContext: Application) : DeviceRepository {
    override fun getContext(): Context {
        return appContext.applicationContext
    }

    override fun getManufacture(): String? {
        return Build.MANUFACTURER
    }

    override fun getIndustrialName(): String? {
        return Build.DEVICE
    }

    override fun getFingerprint(): String? {
        return Build.FINGERPRINT
    }

    override fun getAllProperties(): String {
        return "${getManufacture()}\n${getIndustrialName()}\n${getFingerprint()}"
    }
}