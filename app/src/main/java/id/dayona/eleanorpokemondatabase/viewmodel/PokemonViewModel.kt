package id.dayona.eleanorpokemondatabase.viewmodel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import id.dayona.eleanorpokemondatabase.data.ApiError
import id.dayona.eleanorpokemondatabase.data.ApiException
import id.dayona.eleanorpokemondatabase.data.ApiLoading
import id.dayona.eleanorpokemondatabase.data.ApiSuccess
import id.dayona.eleanorpokemondatabase.data.TAG
import id.dayona.eleanorpokemondatabase.data.model.PokeListModel
import id.dayona.eleanorpokemondatabase.data.model.PokemonIdModel
import id.dayona.eleanorpokemondatabase.data.repository.Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class PokemonViewModel @Inject constructor(repository: Lazy<Repository>) : ViewModel() {
    private val instance = repository.get()
    val pokelist = MutableStateFlow(PokeListModel())
    val pokeIdList = MutableStateFlow(listOf(PokemonIdModel()))

    init {
        initPokeList()
    }

    @Composable
    fun getPokeList(): State<PokeListModel?> {
        return produceState<PokeListModel?>(initialValue = null) {
            instance.pokeList(100, 10).collectLatest { res ->
                when (res) {
                    is ApiSuccess -> {
                        value = res.data
                    }

                    is ApiError -> {}
                    is ApiException -> {}
                    is ApiLoading -> {
                        Log.d(TAG, "Loading")
                    }
                }
            }
        }
    }

    private fun initPokeList() {
        viewModelScope.launch {
            instance.pokeList(20, 30).collectLatest { res ->
                when (res) {
                    is ApiSuccess -> {
                        delay(1.seconds)
                        repeat(res.data.results?.size ?: 0) { i ->
                            val url =
                                res.data.results!![i]?.url!!.replace(
                                    "https://pokeapi.co/api/v2/",
                                    ""
                                )
                            getPokeId(url = url)
                        }
                    }

                    is ApiError -> {
                        Log.d(TAG, "ApiError message : ${res.message} code : ${res.code}")
                    }

                    is ApiException -> {
                        Log.d(TAG, "ApiException message ${res.e}")
                    }

                    is ApiLoading -> {
                        Log.d(TAG, "Loading")
                    }
                }
            }
        }
    }

    private fun getPokeId(url: String) {
        viewModelScope.launch {
            instance.pokemonByUrl(url).collectLatest { res ->
                when (res) {
                    is ApiSuccess -> {
                        pokeIdList.update {
                            (it + res.data).filter { f -> f.name != null }
                        }
                    }

                    is ApiError -> {
                        Log.d(TAG, "${res.message}")
                    }

                    is ApiException -> {
                        Log.d(TAG, res.e)
                    }

                    is ApiLoading -> {
                        Log.d(TAG, "Loading")
                    }
                }
            }
        }
    }
}