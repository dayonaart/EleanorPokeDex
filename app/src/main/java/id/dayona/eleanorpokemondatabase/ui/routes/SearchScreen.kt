package id.dayona.eleanorpokemondatabase.ui.routes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import id.dayona.eleanorpokemondatabase.ui.ScreenRoute
import id.dayona.eleanorpokemondatabase.viewmodel.PokemonViewModel

interface SearchScreen {
    val navController: NavHostController
    val pokemonViewModel: PokemonViewModel

    @Composable
    fun Search() {
        val focusManager = LocalFocusManager.current
        val data by pokemonViewModel.pokemonSearchData.collectAsState()
        val searchController by pokemonViewModel.pokemonSearchController.collectAsState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = searchController,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
                    .border(width = 0.dp, color = Color.Transparent),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    focusManager.clearFocus()
                }),
                onValueChange = pokemonViewModel::searchPokemonName,
                shape = RoundedCornerShape(40),
                label = { Text(text = "Search Pokemon Name") }
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                userScrollEnabled = true,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(count = data.filter { it.name.isNotEmpty() }.size) { i ->
                    Box(
                        modifier = Modifier
                            .background(
                                shape = RoundedCornerShape(20),
                                color = Color(data[i].color)
                            )
                            .border(
                                width = 1.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(20)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(ScreenRoute.DetailPokemon.route + "/${data[i].pokemonId}")
                                }
                                .padding(horizontal = 5.dp, vertical = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box {
                                AsyncImage(
                                    model = data[i].sprites.other.officialArtwork.frontDefault,
                                    contentDescription = "Pokemon"
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = Color.White,
                                            shape = RoundedCornerShape(50)
                                        )
                                        .padding(5.dp)
                                        .size(if (i < 99) 15.dp else 20.dp),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    androidx.compose.material3.Text(
                                        text = "${i + 1}",
                                        fontSize = 10.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(20)
                                    )
                                    .padding(horizontal = 5.dp, vertical = 3.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                androidx.compose.material3.Text(
                                    text = data[i].name,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}