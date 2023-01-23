package com.perennial.openweatherapp.di

import android.app.Application
import com.perennial.openweatherapp.db.datastore.UserDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)

@Module
object DatastoreModule {

    @Provides
    @Singleton
    fun providesAuthDatastore(application: Application): UserDataStore {
        return UserDataStore(application)
    }

}