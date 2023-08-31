package id.dayona.eleanorpokemondatabase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import id.dayona.eleanorpokemondatabase.ui.Screen
import id.dayona.eleanorpokemondatabase.ui.theme.EleanorPokemonDatabaseTheme
import id.dayona.eleanorpokemondatabase.viewmodel.PokemonViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val screen = Screen()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
}
