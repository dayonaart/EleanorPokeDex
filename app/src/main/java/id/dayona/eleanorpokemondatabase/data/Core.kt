package id.dayona.eleanorpokemondatabase.data

import id.dayona.eleanorpokemondatabase.EleanorPokemonApp

sealed interface ApiResult<T>
class ApiSuccess<T>(val data: T) : ApiResult<T>
class ApiError<T : Any>(val code: Int, val message: String?) : ApiResult<T>
class ApiException<T : Any>(val e: String) : ApiResult<T>
class ApiLoading<T : Any>(val message: String) : ApiResult<T>

interface Core {
    companion object {
        const val contentType = "Accept:application/json"
        const val movieKey = "X-RapidAPI-Key:4c465f74c6msh136aa996af42af6p182965jsn27547d4107fa"
        const val movieHost = "X-RapidAPI-Host:streaming-availability.p.rapidapi.com"
    }
}

val NORMAL_TAG = EleanorPokemonApp::class.java.simpleName
val API_TAG = "API::TAG"
