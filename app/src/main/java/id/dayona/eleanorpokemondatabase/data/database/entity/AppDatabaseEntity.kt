package id.dayona.eleanorpokemondatabase.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "app_database")
data class AppDatabaseEntity(
    @PrimaryKey
    var id: Int?,
    @ColumnInfo("poke_list")
    var pokelist: String?
) {
    @ColumnInfo(name = "create_at", defaultValue = "CURRENT_TIMESTAMP")
    @TypeConverters(DateConverter::class)
    var createdAt: Date? = DateConverter.toDate(System.currentTimeMillis())
}

object DateConverter {
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}