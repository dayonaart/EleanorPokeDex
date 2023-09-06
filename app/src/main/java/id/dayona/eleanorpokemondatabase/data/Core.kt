package id.dayona.eleanorpokemondatabase.data

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import id.dayona.eleanorpokemondatabase.EleanorPokemonApp

sealed interface ApiResult<T>
class ApiSuccess<T>(val data: T) : ApiResult<T>
class ApiError<T : Any>(val code: Int, val message: String?) : ApiResult<T>
class ApiException<T : Any>(val e: String) : ApiResult<T>
class ApiLoading<T : Any>(val message: String) : ApiResult<T>

interface Core {
    companion object {
        const val contentType = "Accept:application/json"
        const val movieKey = "X-RapidAPI-Key:4c465f74c6msh136aa996af42af6p182965jsn27547d4107fa"
        const val movieHost = "X-RapidAPI-Host:streaming-availability.p.rapidapi.com"
    }
}

val PERMISSIONS_REQUIRED = arrayOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION,
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) Manifest.permission.FOREGROUND_SERVICE else ""
)

fun hasPermission(context: Context): Boolean {
    val check = PERMISSIONS_REQUIRED.map {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }
    return !check.contains(false)
}

val NORMAL_TAG = EleanorPokemonApp::class.java.simpleName
val API_TAG = "API::TAG"
