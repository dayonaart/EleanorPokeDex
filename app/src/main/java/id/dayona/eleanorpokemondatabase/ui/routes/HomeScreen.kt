package id.dayona.eleanorpokemondatabase.ui.routes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import id.dayona.eleanorpokemondatabase.ui.ScreenRoute
import id.dayona.eleanorpokemondatabase.viewmodel.PokemonViewModel
import kotlin.random.Random

interface HomeScreen : LoadingDialog, DetailPokemonScreen {
    val navController: NavHostController
    override val pokemonViewModel: PokemonViewModel
    val colorList: List<Color>
        get() = listOf(
            Color.Blue,
            Color.Magenta,
            Color.DarkGray,
            Color.Green,
            Color.Red,
            Color.Yellow
        )
    val randomColor: Color
        get() = colorList[Random.nextInt(0, 6)]
    val buttonListTitle: List<String>
        get() = listOf("by name", "by species", "increase", "decrease")

    @Composable
    fun Home() {
        val pokeIdList by pokemonViewModel.pokeIdList.collectAsState()
        Column {
            LazyHorizontalGrid(
                contentPadding = PaddingValues(10.dp),
                modifier = Modifier.height(60.dp),
                rows = GridCells.Fixed(1),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                content = {
                    items(4) { i ->
                        Button(
                            shape = RoundedCornerShape(30),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                            onClick = {
                                pokemonViewModel.sortPoke(i)
                            }) {
                            Text(text = buttonListTitle[i], color = Color.Black)
                        }
                    }
                })
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                userScrollEnabled = true,
                contentPadding = PaddingValues(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(count = pokeIdList.filter { it.name != null }.size) { i ->
                    Box(
                        modifier = Modifier
                            .background(shape = RoundedCornerShape(20), color = Color.White)
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
                                    navController.navigate(ScreenRoute.DetailPokemon.route + "/$i")
                                }
                                .padding(horizontal = 5.dp, vertical = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box {
                                AsyncImage(
                                    model = pokeIdList[i].sprites?.other?.officialArtwork?.frontDefault,
                                    contentDescription = "Pokemon"
                                )

                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = Color.Blue,
                                            shape = RoundedCornerShape(50)
                                        )
                                        .padding(5.dp)
                                        .size(15.dp),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(
                                        text = "${i + 1}",
                                        fontSize = 10.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Text(
                                text = "${pokeIdList[i].name}",
                                textAlign = TextAlign.Center,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}