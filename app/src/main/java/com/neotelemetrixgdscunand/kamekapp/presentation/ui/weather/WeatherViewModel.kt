package com.neotelemetrixgdscunand.kamekapp.presentation.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neotelemetrixgdscunand.kamekapp.domain.common.Result
import com.neotelemetrixgdscunand.kamekapp.domain.common.RootNetworkError
import com.neotelemetrixgdscunand.kamekapp.domain.data.WeatherRepository
import com.neotelemetrixgdscunand.kamekapp.presentation.mapper.WeatherDuiMapper
import com.neotelemetrixgdscunand.kamekapp.presentation.model.WeatherForecastItemDui
import com.neotelemetrixgdscunand.kamekapp.presentation.util.UIText
import com.neotelemetrixgdscunand.kamekapp.presentation.util.toErrorUIText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val mapper: WeatherDuiMapper
):ViewModel() {

    private val _onMessageEvent = Channel<UIText>()
    val onMessageEvent = _onMessageEvent.receiveAsFlow()

    private var _isGettingWeatherForecastForSeveralDays = MutableStateFlow(false)
    private val _isGettingWeatherForecastOverview = MutableStateFlow(false)
    val isLoading = combine(
        _isGettingWeatherForecastOverview,
        _isGettingWeatherForecastForSeveralDays
    ){ isGettingWeatherOverview, isGettingWeatherForSeveralDays ->
        isGettingWeatherOverview || isGettingWeatherForSeveralDays
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        false
    )


    private val padangCoordinate = Pair(-0.948041f, 100.36309f)

    private var isFirstTimeFetchingData = true

    val weatherForecastOverview = weatherRepository.getWeatherForecastOverviewAutoUpdate(
        latitude = padangCoordinate.first,
        longitude = padangCoordinate.second
    ).map { result ->
        delay(3000L)
        when (result) {
            is Result.Error -> {
                val errorUIText = result.toErrorUIText()
                _onMessageEvent.send(errorUIText)
                null
            }

            is Result.Success -> {
                if(isFirstTimeFetchingData){
                    getWeatherForecastForSeveralDays(padangCoordinate.first, padangCoordinate.second)
                    isFirstTimeFetchingData = false
                }
                _isGettingWeatherForecastOverview.update { false }
                return@map mapper.mapWeatherForecastOverviewToDui(result.data)
            }
        }
    }.onStart { _isGettingWeatherForecastOverview.update { true } }
        .onCompletion { _isGettingWeatherForecastOverview.update { false } }
        .flowOn(Dispatchers.IO)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            null
        )

    private val _weatherForecastForSeveralDays = MutableStateFlow<ImmutableList<WeatherForecastItemDui>>(
        persistentListOf()
    )
    val weatherForecastForSeveralDays = _weatherForecastForSeveralDays.asStateFlow()

    private var job:Job? = null

    private fun getWeatherForecastForSeveralDays(
        latitude:Float,
        longitude:Float
    ){
        suspend fun fetchWeatherForecastForSeveralDays(
            latitude: Float,
            longitude: Float
        ):Boolean{
            val result = weatherRepository.getWeatherForecastForSeveralDays(latitude, longitude)
            return when (result) {
                is Result.Error -> {
                    val errorUIText = result.toErrorUIText()
                    _onMessageEvent.send(errorUIText)
                    result.error == RootNetworkError.REQUEST_TIMEOUT
                }

                is Result.Success -> {
                    val weatherForecastItems = result.data.mapNotNull {
                        mapper.mapWeatherForecastItemToDui(it)
                    }
                    _weatherForecastForSeveralDays.update { weatherForecastItems.toImmutableList() }
                    false
                }
            }
        }

        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            _isGettingWeatherForecastForSeveralDays.update { true }
            do {
                val isTimeout = fetchWeatherForecastForSeveralDays(
                    latitude, longitude
                )
            }while (isTimeout)

            _isGettingWeatherForecastForSeveralDays.update { false }
        }.also {
            it.invokeOnCompletion {
                _isGettingWeatherForecastForSeveralDays.update { false }
            }
        }

    }



}