package id.dayona.eleanorpokemondatabase.ui.routes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.gson.Gson
import id.dayona.eleanorpokemondatabase.data.model.PokeListModel
import id.dayona.eleanorpokemondatabase.viewmodel.PokemonViewModel

interface ProfileScreen {
    val navController: NavHostController
    val pokemonViewModel: PokemonViewModel

    @Composable
    fun Profile() {
        val pokeDatabase by pokemonViewModel.pokeDatabase.collectAsState()
        return LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                Text(
                    text = "id: ${pokeDatabase?.id}\ncreated_at: ${pokeDatabase?.createdAt}\ndata: ${
                        Gson().fromJson(
                            pokeDatabase?.pokelist,
                            PokeListModel::class.java
                        ).results?.size
                    }"
                )

            }
        }
    }

}