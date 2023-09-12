package id.dayona.eleanorpokemondatabase.viewmodel

import android.content.Intent
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import id.dayona.eleanorpokemondatabase.data.repository.DatabaseRepositoryDao
import id.dayona.eleanorpokemondatabase.data.repository.Repository
import id.dayona.eleanorpokemondatabase.data.service.EleanorService
import id.dayona.eleanorpokemondatabase.data.state.ErrorDialogState
import id.dayona.eleanorpokemondatabase.data.state.PokemonState
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class PokemonViewModel @Inject constructor(
    repository: Lazy<Repository>,
    databaseRepositoryDao: Lazy<DatabaseRepositoryDao>
) : ViewModel() {
    val instance = repository.get()
    private val databaseInstance = databaseRepositoryDao.get()
    private val pokemonDatabase = MutableStateFlow(listOf<AppDatabaseEntity>())
    private val pokemonListState = MutableStateFlow(listOf(PokemonState()))
    val pokemonSearchData = MutableStateFlow(listOf(PokemonState()))
    private val pokemonDatabaseCount = databaseInstance.getAll().size

    val loading = MutableStateFlow(false)
    val errorDialog = MutableStateFlow(ErrorDialogState())
    private var pokeCount: Int = 50

    val homeButtonListTitle =
        MutableStateFlow(listOf(""))
    private val colorList: List<Long>
        get() = listOf(
            0xFF000000,
            0xFFFFFFFF,
            0xFFFF0000,
            0xFF00FF00,
            0xFF0000FF,
            0xFFFFFF00,
            0xFF00FFFF,
            0xFFFF00FF
        )
    private val randomColor: Long
        get() = colorList[Random.nextInt(0, colorList.size)]
    private val _pokemonSearchState = MutableStateFlow("")
    val pokemonSearchController: StateFlow<String> = _pokemonSearchState

    init {
        initPokemonList()
    }

    private fun initPokemonList() {
        if (pokemonDatabaseCount >= pokeCount) {
            pokemonDatabase.update { databaseInstance.getAll() }
            return
        }
        viewModelScope.launch {
            instance.initPokeList(pokeCount, 30).collectLatest { res ->
                when (res) {
                    is ApiSuccess -> {
                        repeat(res.data.results?.size ?: 0) { i ->
                            val url =
                                res.data.results!![i]?.url!!.replace(
                                    "https://pokeapi.co/api/v2/",
                                    ""
                                )
                            getPokemonById(url = url) {
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

    @Composable
    fun getPokemonData(): List<PokemonState> {
        val pokemonState by pokemonListState.collectAsState()
        val pokemonDatabase by pokemonDatabase.collectAsState()
        return if (pokemonDatabase.map { it.pokemon.name.isNotEmpty() }.contains(true)) {
            val data = pokemonDatabase.map { it.pokemon }
            homeButtonListTitle.update {
                listOf("sort by name", "sort by weigth", "total ${data.size}")
            }
            data
        } else {
            homeButtonListTitle.update {
                listOf("sort by name", "sort by weigth", "total ${pokemonState.size}")
            }
            pokemonState
        }
    }

    fun searchPokemonName(name: String) {
        viewModelScope.launch {
            _pokemonSearchState.update { name }
            if (name.isEmpty()) {
                pokemonDatabase.update { listOf() }
                return@launch
            }
            _pokemonSearchState.debounce(1000).collectLatest {
                pokemonSearchData.update {
                    databaseInstance.searchPokemonByName(name).map { p -> p.pokemon }
                }
            }
        }
    }

    private suspend fun getPokemonById(url: String, onSuccess: (Boolean) -> Unit) {
        instance.pokemonByUrl(url).collectLatest { res ->
            when (res) {
                is ApiSuccess -> {
                    pokemonListState.update {
                        it.plus(res.data.apply {
                            this.color = randomColor
                        }).filter { f ->
                            f.name.isNotEmpty()
                        }
                    }
                    databaseInstance.insert(AppDatabaseEntity(pokemon = res.data))
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

    fun deleteDatabaseByRange(startId: Int, lastId: Int) {
        viewModelScope.launch {
            databaseInstance.delete(firstId = startId, lastId = lastId)
            pokemonDatabase.update { databaseInstance.getAll() }
        }
    }

    fun limtDatabase(limit: String) {
        viewModelScope.launch {
            pokemonDatabase.update { databaseInstance.getDatabaseLimit(limit.toInt()) }
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
                pokemonDatabase.update { databaseInstance.sortPokemonByName() }
            }

            1 -> {
                pokemonDatabase.update { databaseInstance.sortPokemonByWeight() }
            }

        }
    }
}