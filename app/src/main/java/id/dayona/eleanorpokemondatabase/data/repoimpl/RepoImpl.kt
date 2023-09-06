package id.dayona.eleanorpokemondatabase.data.repoimpl

import android.app.Application
import android.content.Context
import android.util.Log
import id.dayona.eleanorpokemondatabase.R
import id.dayona.eleanorpokemondatabase.data.API_TAG
import id.dayona.eleanorpokemondatabase.data.ApiError
import id.dayona.eleanorpokemondatabase.data.ApiException
import id.dayona.eleanorpokemondatabase.data.ApiLoading
import id.dayona.eleanorpokemondatabase.data.ApiResult
import id.dayona.eleanorpokemondatabase.data.ApiSuccess
import id.dayona.eleanorpokemondatabase.data.NORMAL_TAG
import id.dayona.eleanorpokemondatabase.data.model.PokeListModel
import id.dayona.eleanorpokemondatabase.data.model.PokemonIdModel
import id.dayona.eleanorpokemondatabase.data.remote.Api
import id.dayona.eleanorpokemondatabase.data.repository.DeviceRepository
import id.dayona.eleanorpokemondatabase.data.repository.LocationRepository
import id.dayona.eleanorpokemondatabase.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.util.concurrent.TimeoutException
import javax.inject.Inject
import javax.net.ssl.SSLPeerUnverifiedException

class RepoImpl @Inject constructor(
    private val api: Api,
    private val appContext: Application,
    deviceRepository: DeviceRepository,
    private val locationRepository: LocationRepository
) : Repository {
    init {
        val appName = appContext.getString(R.string.app_name)
        Log.d(NORMAL_TAG, "HELLO ${deviceRepository.getAllProperties()}")
    }

    override suspend fun pokeList(limit: Int, offset: Int): Flow<ApiResult<PokeListModel>> {
        return flow {
            emit(ApiLoading(message = "Loading"))
            try {
                val res = api.pokeList(limit = "$limit", offset = "$offset")
                Log.d(API_TAG, "${res.raw().request.url}")
                if (res.isSuccessful) {
                    emit(ApiSuccess(data = res.body()!!))
                } else {
                    emit(ApiError(code = res.code(), message = res.errorBody()?.string()))
                }
            } catch (e: HttpException) {
                emit(ApiException(e = "${e.message}"))
            } catch (e: SSLPeerUnverifiedException) {
                emit(ApiException(e = "Unverified SSL\n${e.message}"))
            } catch (e: TimeoutException) {
                emit(ApiException(e = "${e.message}"))
            } catch (e: Exception) {
                emit(ApiException(e = "${e.message}"))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun pokemonByUrl(url: String): Flow<ApiResult<PokemonIdModel>> {
        return flow {
            emit(ApiLoading(message = "Loading"))
            try {
                val res = api.pokomenByUrl(url = url)
                Log.d(API_TAG, "${res.raw().request.url}")
                if (res.isSuccessful) {
                    emit(ApiSuccess(data = res.body()!!))
                } else {
                    emit(ApiError(code = res.code(), message = res.raw().message))
                }
            } catch (e: HttpException) {
                emit(ApiException(e = "${e.message}"))
            } catch (e: SSLPeerUnverifiedException) {
                emit(ApiException(e = "Unverified SSL\n${e.message}"))
            } catch (e: Exception) {
                emit(ApiException(e = "${e.message}"))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun searchPokemon(name: String): Flow<ApiResult<PokemonIdModel>> {
        return flow {
            emit(ApiLoading(message = "Loading"))
            try {
                val res = api.searchPokemon(name = name)
                Log.d(API_TAG, "${res.raw().request.url}")
                if (res.isSuccessful) {
                    emit(ApiSuccess(data = res.body()!!))
                } else {
                    emit(ApiError(code = res.code(), message = res.raw().message))
                }
            } catch (e: HttpException) {
                emit(ApiException(e = "${e.message}"))
            } catch (e: SSLPeerUnverifiedException) {
                emit(ApiException(e = "Unverified SSL\n${e.message}"))
            } catch (e: Exception) {
                emit(ApiException(e = "${e.message}"))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getContext(): Context {
        return appContext.applicationContext
    }

}