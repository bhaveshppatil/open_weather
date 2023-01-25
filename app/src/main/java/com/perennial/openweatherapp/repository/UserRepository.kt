package com.perennial.openweatherapp.repository

import com.perennial.openweatherapp.db.User
import com.perennial.openweatherapp.db.UserDatabase
import com.perennial.openweatherapp.db.datastore.UserDataStore
import com.perennial.openweatherapp.db.weather.WeatherModel
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDatabase: UserDatabase,
    private val userDataStore: UserDataStore
) {

    fun getLoggedInUser() = userDatabase.userDao().getLoggedInUser()

    fun loginUser(email: String, password: String) =
        userDatabase.userDao().loginUser(email, password)

    suspend fun registerUser(user: User) = userDatabase.userDao().registerUser(user)

    suspend fun logOutUser() = userDatabase.userDao().logOutUser()

    suspend fun setUserLoggedIn(key: String, value: Boolean) = userDataStore.loginUser(key, value)

    suspend fun isUserLoggedIn(key: String, value: Boolean) = userDataStore.isUserLoggedIn(key, value)

    suspend fun setUserLoggedOut(key: String, value: Boolean) = userDataStore.logOutUser(key, value)


}