package id.dayona.eleanorpokemondatabase.data.di

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.dayona.eleanorpokemondatabase.data.remote.Api
import id.dayona.eleanorpokemondatabase.data.repoimpl.DeviceRepoImpl
import id.dayona.eleanorpokemondatabase.data.repoimpl.LocationRepoImpl
import id.dayona.eleanorpokemondatabase.data.repoimpl.RepoImpl
import id.dayona.eleanorpokemondatabase.data.repository.DatabaseRepositoryDao
import id.dayona.eleanorpokemondatabase.data.repository.DeviceRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApi(): Api {
        val builder = OkHttpClient().newBuilder().connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
        val okHttpClient: OkHttpClient = builder.build()
        builder.hostnameVerifier { _, _ -> true }
        return Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/")
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build()
            .create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(
        api: Api,
        app: Application,
        deviceRepository: DeviceRepository,
    ): RepoImpl {
        return RepoImpl(api, app, deviceRepository)
    }

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "CREATE TABLE `app_database2` (`id` INTEGER, `data` TEXT NULL, " +
                        "PRIMARY KEY(`id`))"
            )
        }
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): DatabaseRepositoryDao {
        val db = Room.databaseBuilder(
            app,
            AppDatabase::class.java, "pokedb.db"
        ).allowMainThreadQueries().build()
        return db.bindDatabaseRepositoryDao()
    }

    @Provides
    @Singleton
    fun provideLocationRepository(app: Application): LocationRepoImpl {
        return LocationRepoImpl(app)
    }

    @Provides
    @Singleton
    fun provideDeviceRepository(app: Application): DeviceRepoImpl {
        return DeviceRepoImpl(app)
    }
}

