package id.dayona.eleanorpokemondatabase.viewmodel

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import id.dayona.eleanorpokemondatabase.data.model.PokeIdListModel
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
import kotlin.random.Random

@HiltViewModel
class PokemonViewModel @Inject constructor(
    repository: Lazy<Repository>,
    databaseRepositoryDao: Lazy<DatabaseRepositoryDao>
) : ViewModel() {
    private val instance = repository.get()
    private val databaseInstance = databaseRepositoryDao.get()
    val pokeDatabase = databaseInstance.getAll()?.pokeIdList?.data
    private val pokeIdList = MutableStateFlow(PokeIdListModel())
    val pokemonSearchData = MutableStateFlow(PokeIdListModel())
    val loading = MutableStateFlow(false)
    val errorDialog = MutableStateFlow(ErrorDialogModel())
    private var pokeCount: Int = 60
    private val colorList: List<Color>
        get() = listOf(
            Color.Blue,
            Color.Magenta,
            Color.DarkGray,
            Color.Green,
            Color.Red,
            Color.Yellow
        )
    private val randomColor: Color
        get() = colorList[Random.nextInt(0, 6)]

    init {
        initPokeList()
    }

    private val pokeDatabaseSize = pokeDatabase?.size
        ?: 0

    private fun initPokeList() {
        if (pokeCount < pokeDatabaseSize) return
        viewModelScope.launch {
            instance.pokeList(pokeCount, 30).collectLatest { res ->
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
                                if (pokeDatabaseSize < (i + 1)) {
                                    databaseInstance.insert(
                                        data = AppDatabaseEntity(
                                            1,
                                            res.data,
                                            pokeIdList.value
                                        )
                                    )
                                }
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
        val search = databaseInstance.getAll()?.pokeIdList?.data?.filter {
            it?.name?.contains(name) ?: false
        } ?: listOf()
        if (search.size > 1) {
            pokemonSearchData.update {
                it.copy(data = search)
            }
        } else {
            pokemonSearchData.update {
                it.copy(data = listOf())
            }
            errorDialog.update {
                it.copy(showError = true, errorText = "Not Found")
            }
        }
    }

    private suspend fun getPokeId(url: String, onSuccess: (Boolean) -> Unit) {
        instance.pokemonByUrl(url).collectLatest { res ->
            when (res) {
                is ApiSuccess -> {
                    pokeIdList.update {
                        it.copy(data = (it.data + res.data).onEach { c ->
                            c?.color = randomColor
                        }.filter { f -> f?.name != null })
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

    @Composable
    fun getPokemonDataState(): List<PokemonIdModel?> {
        val pokeState by pokeIdList.collectAsState()
        val data = pokeDatabase ?: listOf()
        val ret = if (data.size < pokeState.data.size) {
            pokeState.data.sortedBy {
                it?.name
            }
        } else {
            data.sortedBy {
                it?.name
            }
        }
        return ret
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
                Toast.makeText(instance.getContext(), "Under Develop", Toast.LENGTH_SHORT).show()
            }

            1 -> {
                Toast.makeText(instance.getContext(), "Under Develop", Toast.LENGTH_SHORT).show()
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