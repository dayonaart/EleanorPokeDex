package id.dayona.eleanorpokemondatabase.ui.routes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import id.dayona.eleanorpokemondatabase.viewmodel.PokemonViewModel

interface ProfileScreen {
    val navController: NavHostController
    val pokemonViewModel: PokemonViewModel

    @Composable
    fun Profile() {
        val pokeDatabase = pokemonViewModel.getPokemonData()
        var text by remember {
            mutableStateOf("")
        }
        return Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(modifier = Modifier.width(50.dp), value = text, onValueChange = {
                text = it
            }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal), maxLines = 1)
            Button(onClick = {
                pokemonViewModel.limtDatabase(text)
            }) {
                Text(text = "limit database")
            }
        }
    }

}