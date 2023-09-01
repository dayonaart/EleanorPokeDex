package id.dayona.eleanorpokemondatabase.data.repository

import android.content.Context

interface DeviceRepository {
    fun getContext(): Context
    fun getManufacture(): String?
    fun getIndustrialName(): String?
    fun getFingerprint(): String?
    fun getAllProperties(): String
}