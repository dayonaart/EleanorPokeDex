package id.dayona.eleanorpokemondatabase.ui.routes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import id.dayona.eleanorpokemondatabase.viewmodel.PokemonViewModel

interface FeedScreen {
    val navController: NavHostController
    val pokemonViewModel: PokemonViewModel

    @Composable
    fun Feed() {
        return Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                pokemonViewModel.startService()
            }) {
                Text(text = "Start Service")
            }
            Button(onClick = {
                pokemonViewModel.stopService()
            }) {
                Text(text = "Stop Service")
            }
        }
    }

}
