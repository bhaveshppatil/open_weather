package com.perennial.openweatherapp.di

import android.app.Application
import androidx.room.Room
import com.perennial.openweatherapp.db.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)

@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun providesAppDatabase(application: Application): UserDatabase {
        return Room.databaseBuilder(application, UserDatabase::class.java, "userdata.db")
            .fallbackToDestructiveMigration()
            .build()
    }

}