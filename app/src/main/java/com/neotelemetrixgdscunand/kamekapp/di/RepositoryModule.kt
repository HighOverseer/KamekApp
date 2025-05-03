package com.neotelemetrixgdscunand.kamekapp.di

import com.neotelemetrixgdscunand.kamekapp.data.AuthRepositoryImpl
import com.neotelemetrixgdscunand.kamekapp.data.RepositoryImpl
import com.neotelemetrixgdscunand.kamekapp.data.WeatherRepositoryImpl
import com.neotelemetrixgdscunand.kamekapp.data.local.AuthPreferenceImpl
import com.neotelemetrixgdscunand.kamekapp.data.utils.LocationManagerImpl
import com.neotelemetrixgdscunand.kamekapp.domain.data.AuthPreference
import com.neotelemetrixgdscunand.kamekapp.domain.data.AuthRepository
import com.neotelemetrixgdscunand.kamekapp.domain.data.Repository
import com.neotelemetrixgdscunand.kamekapp.domain.data.WeatherRepository
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
    abstract fun bindRepository(repository: RepositoryImpl): Repository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(weatherRepository: WeatherRepositoryImpl): WeatherRepository


    @Binds
    abstract fun bindAuthPreference(authPreferenceImpl: AuthPreferenceImpl): AuthPreference

}