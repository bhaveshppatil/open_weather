package com.perennial.openweatherapp.di

import android.app.Application
import androidx.room.Room
import com.perennial.openweatherapp.db.datastore.UserDataStore
import com.perennial.openweatherapp.db.user.UserDatabase
import com.perennial.openweatherapp.db.weather.WeatherDatabase
import com.perennial.openweatherapp.remote.ApiService
import com.perennial.openweatherapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private val httpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


    @Singleton
    @Provides
    fun providesAppDatabase(application: Application): UserDatabase {
        return Room.databaseBuilder(application, UserDatabase::class.java, "userdata.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherDatabase(application: Application): WeatherDatabase {
        return Room.databaseBuilder(application, WeatherDatabase::class.java, "weather_history.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesAuthDatastore(application: Application): UserDataStore {
        return UserDataStore(application)
    }

    @Provides
    fun provideApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build())
            .build()
        return retrofit.create(ApiService::class.java)
    }
}