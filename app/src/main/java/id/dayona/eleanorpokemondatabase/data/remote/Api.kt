package id.dayona.eleanorpokemondatabase.data.remote

import id.dayona.eleanorpokemondatabase.data.state.PokemonInitState
import id.dayona.eleanorpokemondatabase.data.state.PokemonState
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("pokemon/")
    suspend fun initPokeList(
        @Query("limit") limit: String, @Query("offset") offset: String
    ): Response<PokemonInitState>

    @GET("{url}")
    suspend fun pokomenByUrl(
        @Path("url") url: String
    ): Response<PokemonState>

    @GET("pokemon/{name}")
    suspend fun searchPokemon(
        @Path("name") name: String
    ): Response<PokemonState>
}