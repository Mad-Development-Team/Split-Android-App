package com.madteam.split.data.repository.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface DatastoreManager {
    suspend fun saveString(key: String, value: String)
    suspend fun getString(key: String): String?
    suspend fun saveInt(key: String, value: Int)
    suspend fun getInt(key: String): Int?
    suspend fun removeValue(key: String)
}

class DatastoreManagerImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : DatastoreManager {

    override suspend fun saveString(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    override suspend fun getString(key: String): String? {
        val dataStoreKey = stringPreferencesKey(key)
        return dataStore.data.map { it[dataStoreKey] }.first()
    }

    override suspend fun saveInt(key: String, value: Int) {
        val dataStoreKey = intPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    override suspend fun getInt(key: String): Int? {
        val dataStoreKey = intPreferencesKey(key)
        return dataStore.data.map { it[dataStoreKey] }.first()
    }

    override suspend fun removeValue(key: String) {
        val dataStoreKey = stringPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences.remove(dataStoreKey)
        }
    }
}
