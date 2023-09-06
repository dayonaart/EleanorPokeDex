package id.dayona.eleanorpokemondatabase.data.repoimpl

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import id.dayona.eleanorpokemondatabase.data.hasPermission
import id.dayona.eleanorpokemondatabase.data.repository.LocationRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationRepoImpl @Inject constructor(
    private val context: Context,
) : LocationRepository {

    @SuppressLint("MissingPermission")
    override fun getLocationUpdate(
        interval: Long,
        client: FusedLocationProviderClient
    ): Flow<Location> {
        return callbackFlow {
            if (!hasPermission(context)) {
                throw LocationRepository.LocationException("Missing Permission")

            }
            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnable =
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if (!isGpsEnable || !isNetworkEnable) {
                throw LocationRepository.LocationException("GPS or network disable")
            }
            val request =
                LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, interval)
                    .setWaitForAccurateLocation(false)
                    .setMinUpdateIntervalMillis(interval)
                    .setMaxUpdateDelayMillis(interval)
                    .build()
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    super.onLocationResult(p0)
                    p0.locations.lastOrNull()?.let { loc ->
                        launch { send(loc) }
                    }
                }

                override fun onLocationAvailability(p0: LocationAvailability) {
                    super.onLocationAvailability(p0)
                }
            }
            client.requestLocationUpdates(
                request,
                locationCallback,
                Looper.getMainLooper()
            )
            awaitClose { client.removeLocationUpdates(locationCallback) }
        }
    }

    override fun getContext(): Context {
        return context
    }
}