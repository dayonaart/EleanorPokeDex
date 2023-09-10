package id.dayona.eleanorpokemondatabase.ui.routes

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import id.dayona.eleanorpokemondatabase.viewmodel.PokemonViewModel

interface DetailPokemonScreen {
    val pokemonViewModel: PokemonViewModel

    @Composable
    fun DetailPokemon(id: Int) {
        val data = pokemonViewModel.getPokemonData().find {
            it.pokemonId == id
        }
        val ability = data?.abilities?.map { it.ability.name }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = data?.sprites?.other?.officialArtwork?.frontDefault,
                contentDescription = "datamon",
                modifier = Modifier.size(300.dp)
            )
            Text(text = "${data?.name}", fontSize = 40.sp, color = Color.Green)
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(10.dp),
                modifier = Modifier
                    .padding(10.dp)
                    .border(width = 1.dp, color = Color.Cyan)
            ) {
                items(count = ability?.size ?: 0) { i ->
                    Text(text = "${ability?.get(i)}")
                }
            }
        }
    }
}