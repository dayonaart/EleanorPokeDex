package id.dayona.eleanorpokemondatabase.data.repository

import android.content.Context
import id.dayona.eleanorpokemondatabase.data.ApiResult
import id.dayona.eleanorpokemondatabase.data.state.PokemonInitState
import id.dayona.eleanorpokemondatabase.data.state.PokemonState
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun initPokeList(limit: Int, offset: Int): Flow<ApiResult<PokemonInitState>>
    suspend fun pokemonByUrl(url: String): Flow<ApiResult<PokemonState>>
    suspend fun searchPokemon(name: String): Flow<ApiResult<PokemonState>>
    fun getContext(): Context
}