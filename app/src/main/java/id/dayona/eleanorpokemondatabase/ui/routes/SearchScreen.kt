package id.dayona.eleanorpokemondatabase.ui.routes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import id.dayona.eleanorpokemondatabase.viewmodel.PokemonViewModel

interface SearchScreen {
    val navController: NavHostController
    val pokemonViewModel: PokemonViewModel

    @Composable
    fun Search() {
        val focusManager = LocalFocusManager.current
        var textController by remember {
            mutableStateOf("")
        }
        val pokemon by pokemonViewModel.pokeId.collectAsState()
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextField(value = textController,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    focusManager.clearFocus()
                    pokemonViewModel.searchPokeName(textController)
                }),
                onValueChange = {
                    textController = it
                })
            Button(modifier = Modifier.align(Alignment.End), onClick = {
                focusManager.clearFocus()
                pokemonViewModel.searchPokeName(textController)
            }) {
                Text(text = "search")
            }
            if (pokemon.name != null) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AsyncImage(
                        model = pokemon.sprites?.other?.officialArtwork?.frontDefault,
                        modifier = Modifier.size(200.dp),
                        contentDescription = "pokemon"
                    )
                    Text(text = "${pokemon.name}", fontSize = 35.sp, textAlign = TextAlign.Center)
                }
            }
        }
    }
}