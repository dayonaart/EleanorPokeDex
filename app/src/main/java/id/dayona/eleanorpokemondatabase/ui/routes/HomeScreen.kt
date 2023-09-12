package id.dayona.eleanorpokemondatabase.ui.routes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import id.dayona.eleanorpokemondatabase.R
import id.dayona.eleanorpokemondatabase.data.state.PokemonState
import id.dayona.eleanorpokemondatabase.viewmodel.PokemonViewModel

interface HomeScreen : LoadingDialog, DetailPokemonScreen {
    val navController: NavHostController
    override val pokemonViewModel: PokemonViewModel

    @Composable
    fun Home() {
        val data = pokemonViewModel.getPokemonData()
        val data1 = data.take(data.size / 2)
        val data2 = data.takeLast(data1.size)
        var pokemonView by remember {
            if (data1.isNotEmpty()) {
                mutableStateOf(data1[0])
            } else {
                mutableStateOf(PokemonState())

            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),

            ) {
            ButtonList()
            if (pokemonView.name.isNotEmpty()) {
                PokemonView(data = pokemonView)
            }
            PokemonSlider(data = data1) { r ->
                data.find { it.name == r }.let {
                    pokemonView = it!!
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            PokemonSlider(data = data2) { r ->
                data.find { it.name == r }.let {
                    pokemonView = it!!
                }
            }
        }
    }

    @Composable
    fun PokemonView(data: PokemonState) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(modifier = Modifier) {
                Column {
                    data.abilities.map { it.ability.name }.forEach {
                        Text(
                            text = it,
                            textAlign = TextAlign.Start,
                            color = Color.Black
                        )
                    }
                }
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(data.sprites.other.officialArtwork.frontDefault)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                    error = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null
                )
                Column {
                    data.stats.map { it.stat.name }.forEach {
                        Text(
                            text = it,
                            textAlign = TextAlign.Start,
                            color = Color.Black
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(20)
                    )
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Text(
                    text = data.name,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun PokemonSlider(data: List<PokemonState>, onClick: (String) -> Unit) {
        val pagerState = rememberPagerState(initialPage = 1)
        Box(
            modifier = Modifier
                .border(width = 1.dp, color = Color.DarkGray)
                .padding(vertical = 10.dp)
        ) {
            HorizontalPager(
                pageCount = data.size,
                key = { data[it].name },
                state = pagerState,
                pageSize = PageSize.Fixed(100.dp),
                verticalAlignment = Alignment.CenterVertically,
                pageNestedScrollConnection = remember(pagerState) {
                    PagerDefaults.pageNestedScrollConnection(Orientation.Horizontal)
                }
            ) { index ->
                Card(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(20))
                        .clickable {
                            onClick(data[index].name)
                        },
                    shape = RoundedCornerShape(20),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .align(alignment = Alignment.CenterHorizontally)
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(data[index].sprites.other.officialArtwork.frontDefault)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                            error = painterResource(id = R.drawable.ic_launcher_foreground),
                            modifier = Modifier.background(color = Color(data[index].color))
                        )
                    }
                }
            }
        }

    }

    @Composable
    fun ButtonList() {
        val buttonListState by pokemonViewModel.homeButtonListTitle.collectAsState()
        LazyHorizontalGrid(
            contentPadding = PaddingValues(10.dp),
            modifier = Modifier.height(60.dp),
            rows = GridCells.Fixed(1),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            content = {
                items(buttonListState.size) { i ->
                    Button(
                        shape = RoundedCornerShape(30),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        onClick = {
                            pokemonViewModel.sortPoke(i)
                        }) {
                        Text(
                            text = buttonListState[i],
                            color = Color.Black
                        )
                    }
                }
            })
    }

    @Composable
    fun PokemonName(i: Int, data: List<PokemonState>) {
        Box(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(20)
                )
                .padding(horizontal = 5.dp, vertical = 3.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = data[i].name,
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
    }
}