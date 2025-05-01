package com.neotelemetrixgdscunand.kamekapp.di

import com.neotelemetrixgdscunand.kamekapp.data.AuthRepositoryImpl
import com.neotelemetrixgdscunand.kamekapp.data.RepositoryImpl
import com.neotelemetrixgdscunand.kamekapp.data.local.AuthPreferenceImpl
import com.neotelemetrixgdscunand.kamekapp.domain.data.AuthPreference
import com.neotelemetrixgdscunand.kamekapp.domain.data.AuthRepository
import com.neotelemetrixgdscunand.kamekapp.domain.data.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideRepository(repository: RepositoryImpl): Repository

    @Binds
    @Singleton
    abstract fun provideAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun provideAuthPreference(authPreferenceImpl: AuthPreferenceImpl): AuthPreference

}