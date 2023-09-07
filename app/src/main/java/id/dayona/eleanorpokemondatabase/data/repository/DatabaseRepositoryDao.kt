package id.dayona.eleanorpokemondatabase.data.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.dayona.eleanorpokemondatabase.data.database.entity.PokemonListEntity

@Dao
interface DatabaseRepositoryDao {
    @Query("SELECT * FROM pokemonlistentity")
    fun getAll(): PokemonListEntity?

    @Query("SELECT * FROM pokemonlistentity WHERE uid IN (:id)")
    fun loadAllByIds(id: IntArray): PokemonListEntity?

    @Query(
        "SELECT * FROM pokemonlistentity WHERE pokelist LIKE :name LIKE :name LIMIT 1"
    )
    fun findByName(name: String): PokemonListEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pokemonListEntity: PokemonListEntity?)

    @Delete
    fun delete(pokemonListEntity: PokemonListEntity?)
}