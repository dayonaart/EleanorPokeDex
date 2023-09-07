package id.dayona.eleanorpokemondatabase.data.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.dayona.eleanorpokemondatabase.data.database.entity.AppDatabaseEntity

@Dao
interface DatabaseRepositoryDao {
    @Query("SELECT * FROM app_database")
    fun getAll(): AppDatabaseEntity?

    @Query("SELECT * FROM app_database WHERE id IN (:id)")
    fun loadByIds(id: Int): AppDatabaseEntity?
//
//    @Query("SELECT * FROM appdatabaseentity WHERE data LIKE :name LIKE :name LIMIT 1")
//    fun findByName(name: String): AppDatabaseEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: AppDatabaseEntity?) {
        data.apply {
            this?.createdAt = System.currentTimeMillis()
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(data: AppDatabaseEntity?) {
        data.apply {
            this?.modifiedAt = System.currentTimeMillis()
        }
    }

    @Delete
    fun delete(appDatabaseEntity: AppDatabaseEntity?)

    fun updateWithTimestamp(data: AppDatabaseEntity) {
        insert(data.apply {
            modifiedAt = System.currentTimeMillis()
        })
    }
}