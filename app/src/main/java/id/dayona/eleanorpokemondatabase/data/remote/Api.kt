package id.dayona.eleanorpokemondatabase.data.remote

import id.dayona.eleanorpokemondatabase.data.model.PokeListModel
import id.dayona.eleanorpokemondatabase.data.model.PokemonIdModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("pokemon/")
    suspend fun pokeList(
        @Query("limit") limit: String, @Query("offset") offset: String
    ): Response<PokeListModel>

    @GET("{url}")
    suspend fun pokomenByUrl(
        @Path("url") url: String
    ): Response<PokemonIdModel>
}