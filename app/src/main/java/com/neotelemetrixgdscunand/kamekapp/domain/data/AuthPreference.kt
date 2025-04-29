package com.neotelemetrixgdscunand.kamekapp.domain.data

import kotlinx.coroutines.flow.Flow

interface AuthPreference {

    suspend fun saveToken(token:String)

    fun getToken(): Flow<String>

    companion object{
        const val NAME = "auth_preference"
    }
}