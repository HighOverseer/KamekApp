package com.neotelemetrixgdscunand.kamekapp.domain.data

import com.neotelemetrixgdscunand.kamekapp.domain.common.DataError
import com.neotelemetrixgdscunand.kamekapp.domain.common.Result
import com.neotelemetrixgdscunand.kamekapp.domain.model.WeatherForecastItem
import com.neotelemetrixgdscunand.kamekapp.domain.model.WeatherForecastOverview
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeatherForecastOverviewAutoUpdate(
        latitude: Float,
        longitude: Float,
        delayEachRequestWhenSuccessMs: Long = DELAY_EACH_SUCCESS_REQUEST_MS,
        delayEachRequestWhenErrorMs: Long = DELAY_EACH_ERROR_REQUEST_MS
    ): Flow<Result<WeatherForecastOverview, DataError.NetworkError>>

    suspend fun getWeatherForecastForSeveralDays(
        latitude: Float,
        longitude: Float
    ): Result<List<WeatherForecastItem>, DataError.NetworkError>

    companion object {
        private const val DELAY_EACH_SUCCESS_REQUEST_MS = 5 * 60 * 1000L // 5 Minute
        private const val DELAY_EACH_ERROR_REQUEST_MS = 10 * 1000L // 10 Second

    }
}