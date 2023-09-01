package id.dayona.eleanorpokemondatabase.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.dayona.eleanorpokemondatabase.data.repoimpl.DeviceRepoImpl
import id.dayona.eleanorpokemondatabase.data.repoimpl.RepoImpl
import id.dayona.eleanorpokemondatabase.data.repository.DeviceRepository
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
}