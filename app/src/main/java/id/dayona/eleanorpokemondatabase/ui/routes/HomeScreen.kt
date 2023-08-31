package id.dayona.eleanorpokemondatabase.ui.routes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import id.dayona.eleanorpokemondatabase.ui.ScreenRoute
import id.dayona.eleanorpokemondatabase.viewmodel.PokemonViewModel

interface HomeScreen : Loading, DetailPokemonScreen {
    val navController: NavHostController
    override val pokemonViewModel: PokemonViewModel

    @Composable
    fun Home() {
        val pokeIdList by pokemonViewModel.pokeIdList.collectAsState()
        Column {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                userScrollEnabled = true,
                contentPadding = PaddingValues(10.dp)
            ) {
                items(count = pokeIdList.filter { it.name != null }.size) { i ->
                    Card(modifier = Modifier.padding(10.dp)) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(ScreenRoute.DetailPokemon.route + "/$i")
                                }
                        ) {
                            AsyncImage(
                                model = pokeIdList[i].sprites?.other?.officialArtwork?.frontDefault,
                                modifier = Modifier.size(100.dp),
                                contentDescription = "Pokemon"
                            )
                            Text(text = "${pokeIdList[i].name}")

                        }
                    }
                }
            }
        }
    }

}