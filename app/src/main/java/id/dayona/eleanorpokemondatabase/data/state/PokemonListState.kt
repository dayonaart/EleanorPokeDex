package id.dayona.eleanorpokemondatabase.data.state


data class PokemonInitState(
    val next: String? = null,
    val previous: String? = null,
    val count: Int? = null,
    val results: List<ResultsItem?>? = null
)

data class ResultsItem(
    val name: String? = null,
    val url: String? = null
)

