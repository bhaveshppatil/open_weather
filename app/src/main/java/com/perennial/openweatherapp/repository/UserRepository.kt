package com.perennial.openweatherapp.repository

import com.perennial.openweatherapp.db.User
import com.perennial.openweatherapp.db.UserDatabase
import com.perennial.openweatherapp.db.datastore.UserDataStore
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

    suspend fun setUserLoggedIn() = userDataStore.loginUser()

    suspend fun isUserLoggedIn() = userDataStore.isUserLoggedIn()

    suspend fun setUserLoggedOut() = userDataStore.logOutUser()

}