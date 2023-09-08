package id.dayona.eleanorpokemondatabase

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import id.dayona.eleanorpokemondatabase.data.PERMISSIONS_REQUIRED
import id.dayona.eleanorpokemondatabase.data.increaseDatabaseCapacity
import id.dayona.eleanorpokemondatabase.ui.Screen
import id.dayona.eleanorpokemondatabase.ui.theme.EleanorPokemonDatabaseTheme
import id.dayona.eleanorpokemondatabase.viewmodel.PokemonViewModel


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val TAG = this::class.java.simpleName
    private val screen = Screen()
    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                Log.e(TAG, "${it.key} = ${it.value}")
                val granted = it.value
                val permission = it.key
                if (!granted) {
                    val neverAskAgain = !ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        permission
                    )
                    if (neverAskAgain) {
                        //user click "never ask again"
                    } else {
                        //show explain dialog
                    }
                    return@registerForActivityResult
                }
            }
        }

    @SuppressLint("PrivateApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        increaseDatabaseCapacity()
        checkAppPermission()
        setContent {
            val pokeViewModel = hiltViewModel<PokemonViewModel>()
            screen.navController = rememberNavController()
            screen.pokemonViewModel = pokeViewModel
            EleanorPokemonDatabaseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    screen.MainScreen()
                }
            }
        }
    }

    private fun checkAppPermission() {
        PERMISSIONS_REQUIRED.forEach { permission ->
            if (ContextCompat.checkSelfPermission(this, permission) ==
                PackageManager.PERMISSION_DENIED
            ) {
                requestMultiplePermissions.launch(PERMISSIONS_REQUIRED)
                return
            }
        }
    }
}
