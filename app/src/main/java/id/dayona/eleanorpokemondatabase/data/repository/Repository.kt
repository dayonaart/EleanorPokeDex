package id.dayona.eleanorpokemondatabase.data.repository

import android.content.Context
import id.dayona.eleanorpokemondatabase.data.ApiResult
import id.dayona.eleanorpokemondatabase.data.model.PokeListModel
import id.dayona.eleanorpokemondatabase.data.model.PokemonIdModel
import id.dayona.eleanorpokemondatabase.data.repoimpl.LocationRepoImpl
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun pokeList(limit: Int, offset: Int): Flow<ApiResult<PokeListModel>>
    suspend fun pokemonByUrl(url: String): Flow<ApiResult<PokemonIdModel>>
    suspend fun searchPokemon(name: String): Flow<ApiResult<PokemonIdModel>>
    fun getContext(): Context

    fun getLocation(): LocationRepoImpl
}