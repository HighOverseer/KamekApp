package com.neotelemetrixgdscunand.kamekapp.di

import com.neotelemetrixgdscunand.kamekapp.data.WeatherDtoMapper
import com.neotelemetrixgdscunand.kamekapp.presentation.mapper.WeatherDuiMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideWeatherDuiMapper(): WeatherDuiMapper = WeatherDuiMapper

    @Provides
    fun provideWeatherDtoMapper(): WeatherDtoMapper = WeatherDtoMapper
}