package id.dayona.eleanorpokemondatabase.viewmodel

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import id.dayona.eleanorpokemondatabase.data.ACTION_START
import id.dayona.eleanorpokemondatabase.data.ACTION_STOP
import id.dayona.eleanorpokemondatabase.data.ApiError
import id.dayona.eleanorpokemondatabase.data.ApiException
import id.dayona.eleanorpokemondatabase.data.ApiLoading
import id.dayona.eleanorpokemondatabase.data.ApiSuccess
import id.dayona.eleanorpokemondatabase.data.NORMAL_TAG
import id.dayona.eleanorpokemondatabase.data.database.entity.AppDatabaseEntity
import id.dayona.eleanorpokemondatabase.data.model.ErrorDialogModel
import id.dayona.eleanorpokemondatabase.data.model.PokeListModel
import id.dayona.eleanorpokemondatabase.data.model.PokemonIdModel
import id.dayona.eleanorpokemondatabase.data.repository.DatabaseRepositoryDao
import id.dayona.eleanorpokemondatabase.data.repository.Repository
import id.dayona.eleanorpokemondatabase.data.service.EleanorService
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    repository: Lazy<Repository>,
    databaseRepositoryDao: Lazy<DatabaseRepositoryDao>
) : ViewModel() {
    private val instance = repository.get()
    private val databaseInstance = databaseRepositoryDao.get()
    val pokeDatabase = MutableStateFlow(databaseInstance.getAll())
    val pokelist = MutableStateFlow(PokeListModel())
    val pokeIdList = MutableStateFlow(listOf(PokemonIdModel()))
    val pokeId = MutableStateFlow(PokemonIdModel())
    val loading = MutableStateFlow(false)
    val errorDialog = MutableStateFlow(ErrorDialogModel())
    private var pokeCount: Int = 10

    init {
        initPokeList()
    }

    private fun initPokeList() {
        viewModelScope.launch {
            instance.pokeList(pokeCount, 30).collectLatest { res ->
                when (res) {
                    is ApiSuccess -> {
                        databaseInstance.insert(
                            data = AppDatabaseEntity(1, Gson().toJson(res.data)),
                        )
                        pokeDatabase.emit(databaseInstance.loadByIds(1))
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


    fun searchPokeName(name: String) {
        viewModelScope.launch {
            instance.searchPokemon(name = name).collectLatest { res ->
                when (res) {
                    is ApiSuccess -> {
                        pokeId.emit(res.data)
                        loading.emit(false)
                        errorDialog.update {
                            it.copy(showError = false)
                        }
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
            action = ACTION_START
            instance.getContext().startService(this)
        }
    }

    fun stopService() {
        Intent(
            instance.getContext(),
            EleanorService::class.java
        ).apply {
            action = ACTION_STOP
            instance.getContext().startService(this)
        }
    }

    fun sortPoke(i: Int) {
        when (i) {
            0 -> {
                val sorted = pokeIdList.value.sortedBy { it.name }
                pokeIdList.update { sorted }
            }

            1 -> {
                val sorted = pokeIdList.value.sortedBy { it.species?.name }
                pokeIdList.update { sorted }
            }

            2 -> {
                Toast.makeText(instance.getContext(), "Under Develop", Toast.LENGTH_SHORT).show()
            }

            3 -> {
                Toast.makeText(instance.getContext(), "Under Develop", Toast.LENGTH_SHORT).show()
            }
        }
    }
}