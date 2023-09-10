package id.dayona.eleanorpokemondatabase.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.dayona.eleanorpokemondatabase.data.state.AbilitiesItem
import id.dayona.eleanorpokemondatabase.data.state.FormsItem
import id.dayona.eleanorpokemondatabase.data.state.GameIndicesItem
import id.dayona.eleanorpokemondatabase.data.state.HeldItemsItem
import id.dayona.eleanorpokemondatabase.data.state.MovesItem
import id.dayona.eleanorpokemondatabase.data.state.PokemonState
import id.dayona.eleanorpokemondatabase.data.state.Species
import id.dayona.eleanorpokemondatabase.data.state.Sprites
import id.dayona.eleanorpokemondatabase.data.state.StatsItem
import id.dayona.eleanorpokemondatabase.data.state.TypesItem
import java.util.Date


@Entity(tableName = "app_database")
data class AppDatabaseEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @Embedded
    var pokemon: PokemonState,
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
    fun a(typeItems: List<TypesItem?>?): String {
        return Gson().toJson(typeItems)
    }

    @TypeConverter
    fun aa(json: String): List<TypesItem?>? {
        val listType = object : TypeToken<List<TypesItem?>?>() {}.type
        return Gson().fromJson(json, listType)
    }

    @TypeConverter
    fun b(typeItems: List<HeldItemsItem?>?): String {
        return Gson().toJson(typeItems)
    }

    @TypeConverter
    fun bb(json: String): List<HeldItemsItem?>? {
        val listType = object : TypeToken<List<HeldItemsItem?>?>() {}.type
        return Gson().fromJson(json, listType)
    }

    @TypeConverter
    fun c(typeItems: List<Any?>?): String {
        return Gson().toJson(typeItems)
    }

    @TypeConverter
    fun cc(json: String): List<Any?>? {
        val listType = object : TypeToken<List<Any?>?>() {}.type
        return Gson().fromJson(json, listType)
    }

    @TypeConverter
    fun d(typeItems: Sprites?): String {
        return Gson().toJson(typeItems)
    }

    @TypeConverter
    fun dd(json: String): Sprites? {
        return Gson().fromJson(json, Sprites::class.java)
    }

    @TypeConverter
    fun e(typeItems: List<AbilitiesItem?>?): String {
        return Gson().toJson(typeItems)
    }

    @TypeConverter
    fun ee(json: String): List<AbilitiesItem?>? {
        val listType = object : TypeToken<List<AbilitiesItem?>?>() {}.type
        return Gson().fromJson(json, listType)
    }

    @TypeConverter
    fun f(typeItems: List<GameIndicesItem?>?): String {
        return Gson().toJson(typeItems)
    }

    @TypeConverter
    fun ff(json: String): List<GameIndicesItem?>? {
        val listType = object : TypeToken<List<GameIndicesItem?>?>() {}.type
        return Gson().fromJson(json, listType)
    }

    @TypeConverter
    fun g(json: String): Species? {
        return Gson().fromJson(json, Species::class.java)
    }

    @TypeConverter
    fun gg(typeItems: Species?): String {
        return Gson().toJson(typeItems)
    }

    @TypeConverter
    fun h(typeItems: List<StatsItem?>?): String {
        return Gson().toJson(typeItems)
    }

    @TypeConverter
    fun hh(json: String): List<StatsItem?>? {
        val listType = object : TypeToken<List<StatsItem?>?>() {}.type
        return Gson().fromJson(json, listType)
    }

    @TypeConverter
    fun j(typeItems: List<MovesItem?>?): String {
        return Gson().toJson(typeItems)
    }

    @TypeConverter
    fun jj(json: String): List<MovesItem?>? {
        val listType = object : TypeToken<List<MovesItem?>?>() {}.type
        return Gson().fromJson(json, listType)
    }

    @TypeConverter
    fun k(typeItems: List<FormsItem?>?): String {
        return Gson().toJson(typeItems)
    }

    @TypeConverter
    fun kk(json: String): List<FormsItem?>? {
        val listType = object : TypeToken<List<FormsItem?>?>() {}.type
        return Gson().fromJson(json, listType)
    }
}
