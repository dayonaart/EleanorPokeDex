package id.dayona.eleanorpokemondatabase.data.repository

import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getLocationUpdate(
        interval: Long,
        client: FusedLocationProviderClient
    ): Flow<Location>

    class LocationException(message: String) : Exception()
}