package id.dayona.eleanorpokemondatabase.data

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.database.CursorWindow
import android.os.Build
import androidx.core.content.ContextCompat
import id.dayona.eleanorpokemondatabase.EleanorPokemonApp
import java.io.File
import java.lang.reflect.Field

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

@Throws(Exception::class)
fun getRoomDatabasePath(context: Context, dbName: String): String? {
    val dbFolderPath = context.filesDir.absolutePath.replace("files", "databases")
    val dbFile = File("$dbFolderPath/$dbName")

    // Check if database file exist.
    if (!dbFile.exists()) throw Exception("${dbFile.absolutePath} doesn't exist")
    return dbFile.absolutePath
//        return dbFile.length()
}

fun increaseDatabaseCapacity() {
    try {
        val field: Field = CursorWindow::class.java.getDeclaredField("sCursorWindowSize")
        field.isAccessible = true
        field.set(null, 600 * 1024 * 1024)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

const val ACTION_START = "ACTION_START"
const val ACTION_STOP = "ACTION_STOP"
val NORMAL_TAG = EleanorPokemonApp::class.java.simpleName
val API_TAG = "API::TAG"
val DATABASE_TAG = "DATABASE::TAG"
