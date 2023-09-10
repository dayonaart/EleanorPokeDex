package id.dayona.eleanorpokemondatabase.data.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import id.dayona.eleanorpokemondatabase.data.database.entity.AppDatabaseEntity

@Dao
interface DatabaseRepositoryDao {
    @Query("SELECT * FROM app_database")
    fun getAll(): List<AppDatabaseEntity>

    @Query("SELECT * FROM app_database ORDER BY name ASC ")
    fun sortPokemonByName(): List<AppDatabaseEntity>

    @Query("SELECT * FROM app_database ORDER BY weight DESC ")
    fun sortPokemonByWeight(): List<AppDatabaseEntity>

    @Upsert
    fun insert(data: AppDatabaseEntity)

    @Query("SELECT * FROM app_database WHERE name LIKE :name || '%'")
    fun searchPokemonByName(name: String): List<AppDatabaseEntity>

    @Query("DELETE FROM app_database WHERE id BETWEEN :firstId AND :lastId")
    fun delete(firstId:Int,lastId:Int)

    @Query("SELECT * FROM app_database LIMIT :limit")
    fun getDatabaseLimit(limit:Int):List<AppDatabaseEntity>
}