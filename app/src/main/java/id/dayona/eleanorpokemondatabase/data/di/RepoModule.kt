package id.dayona.eleanorpokemondatabase.data.di

import androidx.room.Database
import androidx.room.RoomDatabase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.dayona.eleanorpokemondatabase.data.database.entity.PokemonListEntity
import id.dayona.eleanorpokemondatabase.data.repoimpl.DeviceRepoImpl
import id.dayona.eleanorpokemondatabase.data.repoimpl.LocationRepoImpl
import id.dayona.eleanorpokemondatabase.data.repoimpl.RepoImpl
import id.dayona.eleanorpokemondatabase.data.repository.DatabaseRepositoryDao
import id.dayona.eleanorpokemondatabase.data.repository.DeviceRepository
import id.dayona.eleanorpokemondatabase.data.repository.LocationRepository
import id.dayona.eleanorpokemondatabase.data.repository.Repository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Binds
    @Singleton
    abstract fun bindRepository(repoImpl: RepoImpl): Repository

    @Binds
    @Singleton
    abstract fun bindDeviceRepository(deviceRepoImpl: DeviceRepoImpl): DeviceRepository

    @Binds
    @Singleton
    abstract fun bindLocationRepository(locationRepoImpl: LocationRepoImpl): LocationRepository

}

@Database(entities = [PokemonListEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bindDatabaseRepositoryDao(): DatabaseRepositoryDao
}