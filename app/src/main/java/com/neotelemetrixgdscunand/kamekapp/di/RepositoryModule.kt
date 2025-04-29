package com.neotelemetrixgdscunand.kamekapp.di

import com.neotelemetrixgdscunand.kamekapp.data.RepositoryImpl
import com.neotelemetrixgdscunand.kamekapp.data.local.AuthPreferenceImpl
import com.neotelemetrixgdscunand.kamekapp.domain.data.AuthPreference
import com.neotelemetrixgdscunand.kamekapp.domain.data.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(repository: RepositoryImpl): Repository

    @Binds
    abstract fun provideAuthPreference(authPreferenceImpl: AuthPreferenceImpl): AuthPreference

}