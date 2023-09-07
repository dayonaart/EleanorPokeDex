package id.dayona.eleanorpokemondatabase.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonListEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "pokelist") val pokeListModel: String,
)
