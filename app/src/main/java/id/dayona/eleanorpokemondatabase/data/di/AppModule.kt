package id.dayona.eleanorpokemondatabase.data.di

import android.app.Application
import android.os.Build
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.dayona.eleanorpokemondatabase.data.remote.Api
import id.dayona.eleanorpokemondatabase.data.repoimpl.DeviceRepoImpl
import id.dayona.eleanorpokemondatabase.data.repoimpl.RepoImpl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
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
        @Named("deviceName") deviceName: String?,
    ): RepoImpl {
        return RepoImpl(api, app, deviceName)
    }

    @Provides
    @Singleton
    @Named("deviceName")
    fun deviceName(
    ): String? {
        return Build.MANUFACTURER
    }

    @Provides
    @Singleton
    fun provideDeviceRepository(app: Application): DeviceRepoImpl {
        return DeviceRepoImpl(app)
    }
}