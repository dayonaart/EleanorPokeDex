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
import id.dayona.eleanorpokemondatabase.data.model.ErrorDialogModel
import id.dayona.eleanorpokemondatabase.data.model.PokeListModel
import id.dayona.eleanorpokemondatabase.data.model.PokemonIdModel
import id.dayona.eleanorpokemondatabase.data.repository.Repository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(repository: Lazy<Repository>) : ViewModel() {
    private val instance = repository.get()
    val pokelist = MutableStateFlow(PokeListModel())
    val pokeIdList = MutableStateFlow(listOf(PokemonIdModel()))
    val loading = MutableStateFlow(false)
    val errorDialog = MutableStateFlow(ErrorDialogModel())

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
            instance.pokeList(10, 30).collectLatest { res ->
                when (res) {
                    is ApiSuccess -> {
                        repeat(res.data.results?.size ?: 0) { i ->
                            val url =
                                res.data.results!![i]?.url!!.replace(
                                    "https://pokeapi.co/api/v2/",
                                    ""
                                )
                            getPokeId(url = url) {
                                if (!it) this.cancel()
                            }
                        }
                        loading.emit(false)
                    }

                    is ApiError -> {
                        Log.d(TAG, "ApiError message : ${res.message} code : ${res.code}")
                        loading.emit(false)
                        errorDialog.update {
                            it.copy(showError = true, errorText = "${res.code}\n${res.message}")
                        }
                    }


                    is ApiException -> {
                        Log.d(TAG, "ApiException message ${res.e}")
                        loading.emit(false)
                        errorDialog.update {
                            it.copy(showError = true, errorText = res.e)
                        }
                    }

                    is ApiLoading -> {
                        Log.d(TAG, "Loading")
                        loading.emit(true)
                    }
                }
            }
        }
    }

    private suspend fun getPokeId(url: String, onSuccess: (Boolean) -> Unit) {
        instance.pokemonByUrl(url).collectLatest { res ->
            when (res) {
                is ApiSuccess -> {
                    pokeIdList.update {
                        (it + res.data).filter { f -> f.name != null }
                    }
                    onSuccess(true)
                }

                is ApiError -> {
                    Log.d(TAG, "${res.message}")
                    loading.emit(false)
                    errorDialog.update {
                        it.copy(showError = true, errorText = "${res.code}\n${res.message}")
                    }
                    onSuccess(false)
                }

                is ApiException -> {
                    Log.d(TAG, res.e)
                    loading.emit(false)
                    errorDialog.update {
                        it.copy(showError = true, errorText = res.e)
                    }
                    onSuccess(false)
                }

                is ApiLoading -> {
                    Log.d(TAG, "Loading")
                }
            }
        }
    }
}