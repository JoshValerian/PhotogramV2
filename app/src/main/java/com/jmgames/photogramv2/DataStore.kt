package com.jmgames.photogramv2

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DataStore(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

    object PreferencesKeys {
        val USERNAMES = stringPreferencesKey("usernames") // Cambiamos a plural para guardar múltiples usuarios
        val CHECKBOX_STATE = stringPreferencesKey("checkbox_state")
        val COMMENTS = stringPreferencesKey("comments") // Nueva clave para almacenar comentarios
        val COMMENTS_PREFIX = "comments_" // Prefijo para las claves de los comentarios de cada usuario
    }

    suspend fun saveUserName(username: String) {
        context.dataStore.edit { preferences ->
            val currentUsers = preferences[PreferencesKeys.USERNAMES]?.split(",")?.toMutableSet() ?: mutableSetOf()
            currentUsers.add(username)
            preferences[PreferencesKeys.USERNAMES] = currentUsers.joinToString(",")
        }
    }

    val userNamesFlow: Flow<Set<String>> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.USERNAMES]?.split(",")?.toSet() ?: emptySet()
    }

    // Guardar el estado del CheckBox para un usuario específico
    suspend fun saveCheckBoxStateForUser(username: String, isChecked: Boolean) {
        context.dataStore.edit { preferences ->
            val currentCheckBoxStates = preferences[PreferencesKeys.CHECKBOX_STATE]?.split(",")?.toMutableMap() ?: mutableMapOf()
            currentCheckBoxStates[username] = isChecked.toString()
            preferences[PreferencesKeys.CHECKBOX_STATE] = currentCheckBoxStates.map { "${it.key}:${it.value}" }.joinToString(",")
        }
    }

    // Obtener el estado del CheckBox para un usuario específico
    suspend fun getCheckBoxStateForUser(username: String): Boolean? {
        val checkBoxStates = context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.CHECKBOX_STATE]?.split(",")?.associate {
                val (user, state) = it.split(":")
                user to state.toBoolean()
            } ?: emptyMap()
        }.first()
        return checkBoxStates[username]
    }

    suspend fun saveCommentForUser(username: String, id: String, comment: String) {
        val commentKey = stringPreferencesKey("${PreferencesKeys.COMMENTS_PREFIX}$id")
        context.dataStore.edit { preferences ->
            val currentComments = preferences[commentKey]?.split(",")?.toMutableList() ?: mutableListOf()
            val commentWithAuthor = "$username: $comment"
            currentComments.add(commentWithAuthor)
            preferences[commentKey] = currentComments.joinToString(",")
        }
    }

    fun getCommentsForUser(username: String): Flow<List<String>> {
        val commentKey = stringPreferencesKey("${PreferencesKeys.COMMENTS_PREFIX}$username")
        return context.dataStore.data.map { preferences ->
            preferences[commentKey]?.split(",") ?: emptyList()
        }
    }
}

private fun <E : Any> List<E>?.toMutableMap(): MutableMap<Any, Any> {
    val map = mutableMapOf<Any, Any>()
    this?.forEachIndexed { index, element ->
        map[index] = element
    }
    return map
}


