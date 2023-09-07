package id.dayona.eleanorpokemondatabase.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_database")
data class AppDatabaseEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo("data")
    val data: String?
) {
    @ColumnInfo(name = "created_at")
    var createdAt: Long = 0

    @ColumnInfo(name = "modified_at")
    var modifiedAt: Long = 0
}
