package id.dayona.eleanorpokemondatabase.data.state

import com.google.gson.annotations.SerializedName

data class PokemonState(

    @field:SerializedName("location_area_encounters")
    val locationAreaEncounters: String = "",

    @field:SerializedName("types")
    val types: List<TypesItem> = listOf(),

    @field:SerializedName("base_experience")
    val baseExperience: Int = 0,

    @field:SerializedName("held_items")
    val heldItems: List<HeldItemsItem> = listOf(),

    @field:SerializedName("weight")
    val weight: Int = 0,

    @field:SerializedName("is_default")
    val isDefault: Boolean = false,

    @field:SerializedName("past_types")
    val pastTypes: List<Any> = listOf(),

    @field:SerializedName("sprites")
    val sprites: Sprites = Sprites(),

    @field:SerializedName("abilities")
    val abilities: List<AbilitiesItem> = listOf(),

    @field:SerializedName("game_indices")
    val gameIndices: List<GameIndicesItem> = listOf(),

    @field:SerializedName("species")
    val species: Species = Species(),

    @field:SerializedName("stats")
    val stats: List<StatsItem> = listOf(),

    @field:SerializedName("moves")
    val moves: List<MovesItem> = listOf(),

    @field:SerializedName("name")
    val name: String = "",

    @field:SerializedName("id")
    val pokemonId: Int = 0,

    @field:SerializedName("forms")
    val forms: List<FormsItem> = listOf(),

    @field:SerializedName("height")
    val height: Int = 0,

    @field:SerializedName("order")
    val order: Int = 0,

    @field:SerializedName("color")
    var color: Long = 0
)

data class Other(

    @field:SerializedName("dream_world")
    val dreamWorld: DreamWorld = DreamWorld(),

    @field:SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork = OfficialArtwork(),

    @field:SerializedName("home")
    val home: Home = Home()
)

data class StatsItem(

    @field:SerializedName("stat")
    val stat: Stat,

    @field:SerializedName("base_stat")
    val baseStat: Int = 0,

    @field:SerializedName("effort")
    val effort: Int
)

data class GameIndicesItem(

    @field:SerializedName("game_index")
    val gameIndex: Int = 0,

    @field:SerializedName("version")
    val version: Version
)

data class Sprites(

    @field:SerializedName("back_shiny_female")
    val backShinyFemale: Any = "",

    @field:SerializedName("back_female")
    val backFemale: Any = "",

    @field:SerializedName("other")
    val other: Other = Other(),

    @field:SerializedName("back_default")
    val backDefault: String = "",

    @field:SerializedName("front_shiny_female")
    val frontShinyFemale: Any = "",

    @field:SerializedName("front_default")
    val frontDefault: String = "",

    @field:SerializedName("versions")
    val versions: Versions = Versions(),

    @field:SerializedName("front_female")
    val frontFemale: Any = "",

    @field:SerializedName("back_shiny")
    val backShiny: String = "",

    @field:SerializedName("front_shiny")
    val frontShiny: String = ""
)

data class RubySapphire(

    @field:SerializedName("back_default")
    val backDefault: String = "",

    @field:SerializedName("front_default")
    val frontDefault: String = "",

    @field:SerializedName("back_shiny")
    val backShiny: String = "",

    @field:SerializedName("front_shiny")
    val frontShiny: String = ""
)

data class GenerationVii(

    @field:SerializedName("icons")
    val icons: Icons = Icons(),

    @field:SerializedName("ultra-sun-ultra-moon")
    val ultraSunUltraMoon: UltraSunUltraMoon = UltraSunUltraMoon()
)

data class TypesItem(

    @field:SerializedName("slot")
    val slot: Int = 0,

    @field:SerializedName("type")
    val type: Type
)

data class HeartgoldSoulsilver(

    @field:SerializedName("back_shiny_female")
    val backShinyFemale: Any = "",

    @field:SerializedName("back_female")
    val backFemale: Any = "",

    @field:SerializedName("back_default")
    val backDefault: String = "",

    @field:SerializedName("front_shiny_female")
    val frontShinyFemale: Any = "",

    @field:SerializedName("front_default")
    val frontDefault: String = "",

    @field:SerializedName("front_female")
    val frontFemale: Any = "",

    @field:SerializedName("back_shiny")
    val backShiny: String = "",

    @field:SerializedName("front_shiny")
    val frontShiny: String = ""
)

data class DreamWorld(

    @field:SerializedName("front_default")
    val frontDefault: String = "",

    @field:SerializedName("front_female")
    val frontFemale: Any = ""
)

data class Species(
    @field:SerializedName("name")
    val name: String = "",

    @field:SerializedName("url")
    val url: String = ""
)

data class Gold(

    @field:SerializedName("back_default")
    val backDefault: String = "",

    @field:SerializedName("front_default")
    val frontDefault: String = "",

    @field:SerializedName("front_transparent")
    val frontTransparent: String = "",

    @field:SerializedName("back_shiny")
    val backShiny: String = "",

    @field:SerializedName("front_shiny")
    val frontShiny: String = ""
)

data class Stat(

    @field:SerializedName("name")
    val name: String = "",

    @field:SerializedName("url")
    val url: String = ""
)

data class AbilitiesItem(

    @field:SerializedName("is_hidden")
    val isHidden: Boolean = false,

    @field:SerializedName("ability")
    val ability: Ability,

    @field:SerializedName("slot")
    val slot: Int = 0
)

data class BlackWhite(

    @field:SerializedName("back_shiny_female")
    val backShinyFemale: Any = "",

    @field:SerializedName("back_female")
    val backFemale: Any = "",

    @field:SerializedName("back_default")
    val backDefault: String = "",

    @field:SerializedName("front_shiny_female")
    val frontShinyFemale: Any = "",

    @field:SerializedName("front_default")
    val frontDefault: String = "",

    @field:SerializedName("animated")
    val animated: Animated = Animated(),

    @field:SerializedName("front_female")
    val frontFemale: Any = "",

    @field:SerializedName("back_shiny")
    val backShiny: String = "",

    @field:SerializedName("front_shiny")
    val frontShiny: String = ""
)

data class Item(

    @field:SerializedName("name")
    val name: String = "",

    @field:SerializedName("url")
    val url: String = ""
)

data class OfficialArtwork(

    @field:SerializedName("front_default")
    val frontDefault: String = "",

    @field:SerializedName("front_shiny")
    val frontShiny: String = ""
)

data class Move(

    @field:SerializedName("name")
    val name: String = "",

    @field:SerializedName("url")
    val url: String = ""
)

data class GenerationIv(

    @field:SerializedName("platinum")
    val platinum: Platinum = Platinum(),

    @field:SerializedName("diamond-pearl")
    val diamondPearl: DiamondPearl = DiamondPearl(),

    @field:SerializedName("heartgold-soulsilver")
    val heartgoldSoulsilver: HeartgoldSoulsilver = HeartgoldSoulsilver()
)

data class UltraSunUltraMoon(

    @field:SerializedName("front_shiny_female")
    val frontShinyFemale: Any = "",

    @field:SerializedName("front_default")
    val frontDefault: String = "",

    @field:SerializedName("front_female")
    val frontFemale: Any = "",

    @field:SerializedName("front_shiny")
    val frontShiny: String = ""
)

data class Version(

    @field:SerializedName("name")
    val name: String = "",

    @field:SerializedName("url")
    val url: String = ""
)

data class MoveLearnMethod(

    @field:SerializedName("name")
    val name: String = "",

    @field:SerializedName("url")
    val url: String = ""
)

data class FormsItem(

    @field:SerializedName("name")
    val name: String = "",

    @field:SerializedName("url")
    val url: String = ""
)

data class Type(

    @field:SerializedName("name")
    val name: String = "",

    @field:SerializedName("url")
    val url: String = ""
)

data class Platinum(

    @field:SerializedName("back_shiny_female")
    val backShinyFemale: Any = "",

    @field:SerializedName("back_female")
    val backFemale: Any = "",

    @field:SerializedName("back_default")
    val backDefault: String = "",

    @field:SerializedName("front_shiny_female")
    val frontShinyFemale: Any = "",

    @field:SerializedName("front_default")
    val frontDefault: String = "",

    @field:SerializedName("front_female")
    val frontFemale: Any = "",

    @field:SerializedName("back_shiny")
    val backShiny: String = "",

    @field:SerializedName("front_shiny")
    val frontShiny: String = ""
)

data class DiamondPearl(

    @field:SerializedName("back_shiny_female")
    val backShinyFemale: Any = "",

    @field:SerializedName("back_female")
    val backFemale: Any = "",

    @field:SerializedName("back_default")
    val backDefault: String = "",

    @field:SerializedName("front_shiny_female")
    val frontShinyFemale: Any = "",

    @field:SerializedName("front_default")
    val frontDefault: String = "",

    @field:SerializedName("front_female")
    val frontFemale: Any = "",

    @field:SerializedName("back_shiny")
    val backShiny: String = "",

    @field:SerializedName("front_shiny")
    val frontShiny: String = ""
)

data class GenerationVi(

    @field:SerializedName("omegaruby-alphasapphire")
    val omegarubyAlphasapphire: OmegarubyAlphasapphire = OmegarubyAlphasapphire(),

    @field:SerializedName("x-y")
    val xY: XY = XY()
)

data class OmegarubyAlphasapphire(

    @field:SerializedName("front_shiny_female")
    val frontShinyFemale: Any = "",

    @field:SerializedName("front_default")
    val frontDefault: String = "",

    @field:SerializedName("front_female")
    val frontFemale: Any = "",

    @field:SerializedName("front_shiny")
    val frontShiny: String = ""
)

data class VersionDetailsItem(

    @field:SerializedName("version")
    val version: Version = Version(),

    @field:SerializedName("rarity")
    val rarity: Int = 0
)

data class GenerationV(

    @field:SerializedName("black-white")
    val blackWhite: BlackWhite = BlackWhite()
)

data class Animated(

    @field:SerializedName("back_shiny_female")
    val backShinyFemale: Any = "",

    @field:SerializedName("back_female")
    val backFemale: Any = "",

    @field:SerializedName("back_default")
    val backDefault: String = "",

    @field:SerializedName("front_shiny_female")
    val frontShinyFemale: Any = "",

    @field:SerializedName("front_default")
    val frontDefault: String = "",

    @field:SerializedName("front_female")
    val frontFemale: Any = "",

    @field:SerializedName("back_shiny")
    val backShiny: String = "",

    @field:SerializedName("front_shiny")
    val frontShiny: String = ""
)

data class VersionGroupDetailsItem(

    @field:SerializedName("level_learned_at")
    val levelLearnedAt: Int = 0,

    @field:SerializedName("version_group")
    val versionGroup: VersionGroup = VersionGroup(),

    @field:SerializedName("move_learn_method")
    val moveLearnMethod: MoveLearnMethod = MoveLearnMethod()
)

data class Yellow(

    @field:SerializedName("front_gray")
    val frontGray: String = "",

    @field:SerializedName("back_transparent")
    val backTransparent: String = "",

    @field:SerializedName("back_default")
    val backDefault: String = "",

    @field:SerializedName("back_gray")
    val backGray: String = "",

    @field:SerializedName("front_default")
    val frontDefault: String = "",

    @field:SerializedName("front_transparent")
    val frontTransparent: String = ""
)

data class GenerationViii(

    @field:SerializedName("icons")
    val icons: Icons = Icons()
)

data class HeldItemsItem(

    @field:SerializedName("item")
    val item: Item = Item(),

    @field:SerializedName("version_details")
    val versionDetails: List<VersionDetailsItem> = listOf()
)

data class FireredLeafgreen(

    @field:SerializedName("back_default")
    val backDefault: String = "",

    @field:SerializedName("front_default")
    val frontDefault: String = "",

    @field:SerializedName("back_shiny")
    val backShiny: String = "",

    @field:SerializedName("front_shiny")
    val frontShiny: String = ""
)

data class XY(

    @field:SerializedName("front_shiny_female")
    val frontShinyFemale: Any = "",

    @field:SerializedName("front_default")
    val frontDefault: String = "",

    @field:SerializedName("front_female")
    val frontFemale: Any = "",

    @field:SerializedName("front_shiny")
    val frontShiny: String = ""
)

data class Versions(

    @field:SerializedName("generation-iii")
    val generationIii: GenerationIii = GenerationIii(),

    @field:SerializedName("generation-ii")
    val generationIi: GenerationIi = GenerationIi(),

    @field:SerializedName("generation-v")
    val generationV: GenerationV = GenerationV(),

    @field:SerializedName("generation-iv")
    val generationIv: GenerationIv = GenerationIv(),

    @field:SerializedName("generation-vii")
    val generationVii: GenerationVii = GenerationVii(),

    @field:SerializedName("generation-i")
    val generationI: GenerationI = GenerationI(),

    @field:SerializedName("generation-viii")
    val generationViii: GenerationViii = GenerationViii(),

    @field:SerializedName("generation-vi")
    val generationVi: GenerationVi = GenerationVi()
)

data class GenerationIi(

    @field:SerializedName("gold")
    val gold: Gold = Gold(),

    @field:SerializedName("crystal")
    val crystal: Crystal = Crystal(),

    @field:SerializedName("silver")
    val silver: Silver = Silver()
)

data class Ability(

    @field:SerializedName("name")
    val name: String = "",

    @field:SerializedName("url")
    val url: String = ""
)

data class Home(

    @field:SerializedName("front_shiny_female")
    val frontShinyFemale: Any = "",

    @field:SerializedName("front_default")
    val frontDefault: String = "",

    @field:SerializedName("front_female")
    val frontFemale: Any = "",

    @field:SerializedName("front_shiny")
    val frontShiny: String = ""
)

data class VersionGroup(

    @field:SerializedName("name")
    val name: String = "",

    @field:SerializedName("url")
    val url: String = ""
)

data class Crystal(

    @field:SerializedName("back_transparent")
    val backTransparent: String = "",

    @field:SerializedName("back_shiny_transparent")
    val backShinyTransparent: String = "",

    @field:SerializedName("back_default")
    val backDefault: String = "",

    @field:SerializedName("front_default")
    val frontDefault: String = "",

    @field:SerializedName("front_transparent")
    val frontTransparent: String = "",

    @field:SerializedName("front_shiny_transparent")
    val frontShinyTransparent: String = "",

    @field:SerializedName("back_shiny")
    val backShiny: String = "",

    @field:SerializedName("front_shiny")
    val frontShiny: String = ""
)

data class GenerationI(

    @field:SerializedName("yellow")
    val yellow: Yellow = Yellow(),

    @field:SerializedName("red-blue")
    val redBlue: RedBlue = RedBlue()
)

data class MovesItem(

    @field:SerializedName("version_group_details")
    val versionGroupDetails: List<VersionGroupDetailsItem>,

    @field:SerializedName("move")
    val move: Move = Move()
)

data class Icons(

    @field:SerializedName("front_default")
    val frontDefault: String = "",

    @field:SerializedName("front_female")
    val frontFemale: Any = ""
)

data class Silver(

    @field:SerializedName("back_default")
    val backDefault: String = "",

    @field:SerializedName("front_default")
    val frontDefault: String = "",

    @field:SerializedName("front_transparent")
    val frontTransparent: String = "",

    @field:SerializedName("back_shiny")
    val backShiny: String = "",

    @field:SerializedName("front_shiny")
    val frontShiny: String = ""
)

data class RedBlue(

    @field:SerializedName("front_gray")
    val frontGray: String = "",

    @field:SerializedName("back_transparent")
    val backTransparent: String = "",

    @field:SerializedName("back_default")
    val backDefault: String = "",

    @field:SerializedName("back_gray")
    val backGray: String = "",

    @field:SerializedName("front_default")
    val frontDefault: String = "",

    @field:SerializedName("front_transparent")
    val frontTransparent: String = ""
)

data class GenerationIii(

    @field:SerializedName("firered-leafgreen")
    val fireredLeafgreen: FireredLeafgreen = FireredLeafgreen(),

    @field:SerializedName("ruby-sapphire")
    val rubySapphire: RubySapphire = RubySapphire(),

    @field:SerializedName("emerald")
    val emerald: Emerald = Emerald()
)

data class Emerald(

    @field:SerializedName("front_default")
    val frontDefault: String = "",

    @field:SerializedName("front_shiny")
    val frontShiny: String = ""
)
