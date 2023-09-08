package id.dayona.eleanorpokemondatabase.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import id.dayona.eleanorpokemondatabase.data.model.PokeIdListModel
import id.dayona.eleanorpokemondatabase.data.model.PokeListModel
import java.util.Date


@Entity(tableName = "app_database")
data class AppDatabaseEntity(
    @PrimaryKey
    var id: Int?,
    @ColumnInfo("poke_list")
    var pokelist: PokeListModel?,
    @ColumnInfo("poke_id_list")
    var pokeIdList: PokeIdListModel?,
) {
    @ColumnInfo(name = "create_at", defaultValue = "CURRENT_TIMESTAMP")
    var createdAt: Date? = EntityConverter.toDate(System.currentTimeMillis())
}

object EntityConverter {
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromPokeListToJSON(pokelist: PokeListModel?): String {
        return Gson().toJson(pokelist)
    }

    @TypeConverter
    fun fromJSONToPokeList(json: String): PokeListModel? {
        return Gson().fromJson(json, PokeListModel::class.java)
    }

    @TypeConverter
    fun fromPokeIdListToJSON(pokedIdList: PokeIdListModel?): String {
        return Gson().toJson(pokedIdList)
    }

    @TypeConverter
    fun fromJSONToPokeIdList(json: String): PokeIdListModel? {
        return Gson().fromJson(json, PokeIdListModel::class.java)
    }
}
