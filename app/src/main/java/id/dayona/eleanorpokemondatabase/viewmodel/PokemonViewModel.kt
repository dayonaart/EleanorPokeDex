package id.dayona.eleanorpokemondatabase.viewmodel

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import id.dayona.eleanorpokemondatabase.data.ApiError
import id.dayona.eleanorpokemondatabase.data.ApiException
import id.dayona.eleanorpokemondatabase.data.ApiLoading
import id.dayona.eleanorpokemondatabase.data.ApiSuccess
import id.dayona.eleanorpokemondatabase.data.EleanorService
import id.dayona.eleanorpokemondatabase.data.NORMAL_TAG
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
class PokemonViewModel @Inject constructor(
    repository: Lazy<Repository>
) : ViewModel() {
    private val instance = repository.get()
    val pokelist = MutableStateFlow(PokeListModel())
    val pokeIdList = MutableStateFlow(listOf(PokemonIdModel()))
    val pokeId = MutableStateFlow(PokemonIdModel())
    val loading = MutableStateFlow(false)
    val errorDialog = MutableStateFlow(ErrorDialogModel())

    init {
//        Toast.makeText(
//            instance.getContext(),
//            deviceRepositoryInstance.getAllProperties(),
//            Toast.LENGTH_SHORT
//        ).show()
        initPokeList()
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
                    }

                    is ApiError -> {
                        errorDialog.update {
                            it.copy(showError = true, errorText = "${res.code}\n${res.message}")
                        }
                    }


                    is ApiException -> {
                        errorDialog.update {
                            it.copy(showError = true, errorText = res.e)
                        }
                    }

                    is ApiLoading -> {
                    }
                }
            }
        }
    }


    fun searchPokemon(name: String) {
        viewModelScope.launch {
            instance.searchPokemon(name = name).collectLatest { res ->
                when (res) {
                    is ApiSuccess -> {
                        pokeId.emit(res.data)
                        loading.emit(false)
                    }

                    is ApiError -> {
                        loading.emit(false)
                        errorDialog.update {
                            it.copy(showError = true, errorText = "${res.code}\n${res.message}")
                        }
                    }


                    is ApiException -> {
                        loading.emit(false)
                        errorDialog.update {
                            it.copy(showError = true, errorText = res.e)
                        }
                    }

                    is ApiLoading -> {
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
                    Log.d(NORMAL_TAG, "${res.message}")
                    loading.emit(false)
                    errorDialog.update {
                        it.copy(showError = true, errorText = "${res.code}\n${res.message}")
                    }
                    onSuccess(false)
                }

                is ApiException -> {
                    Log.d(NORMAL_TAG, res.e)
                    loading.emit(false)
                    errorDialog.update {
                        it.copy(showError = true, errorText = res.e)
                    }
                    onSuccess(false)
                }

                is ApiLoading -> {
                }
            }
        }
    }

    fun startService() {
        Intent(
            instance.getContext(),
            EleanorService::class.java
        ).apply {
            action = EleanorService.ACTION_START
            instance.getContext().startService(this)
        }
    }

    fun stopService() {
        Intent(
            instance.getContext(),
            EleanorService::class.java
        ).apply {
            action = EleanorService.ACTION_STOP
            instance.getContext().startService(this)
        }
    }
}