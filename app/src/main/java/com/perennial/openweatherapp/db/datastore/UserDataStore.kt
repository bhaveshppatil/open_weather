package com.perennial.openweatherapp.db.datastore

import android.content.Context
import android.content.SharedPreferences
import com.perennial.openweatherapp.utils.Constants.AUTH_DATASTORE_NAME

class UserDataStore constructor(private val context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(AUTH_DATASTORE_NAME, Context.MODE_PRIVATE)

    //Marks the user as logged in
    fun loginUser(key: String, value: Boolean) {
        val prefEditor: SharedPreferences.Editor = preferences.edit()
        with(prefEditor) {
            putBoolean(key, value)
            commit()
        }
    }

    //Checks if the user is logged in
    fun isUserLoggedIn(key: String, value: Boolean): Boolean {
        return preferences.getBoolean(key, value)
    }

    //Marks the user as logged out
    fun logOutUser(key: String, value: Boolean) {
        val prefEditor: SharedPreferences.Editor = preferences.edit()
        with(prefEditor) {
            putBoolean(key, value)
            commit()
        }
    }
}