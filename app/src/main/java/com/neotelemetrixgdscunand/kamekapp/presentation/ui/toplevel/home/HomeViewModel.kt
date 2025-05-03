package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neotelemetrixgdscunand.kamekapp.domain.common.LocationError
import com.neotelemetrixgdscunand.kamekapp.domain.common.Result
import com.neotelemetrixgdscunand.kamekapp.domain.data.LocationManager
import com.neotelemetrixgdscunand.kamekapp.domain.data.Repository
import com.neotelemetrixgdscunand.kamekapp.domain.data.WeatherRepository
import com.neotelemetrixgdscunand.kamekapp.domain.model.DiagnosisSessionPreview
import com.neotelemetrixgdscunand.kamekapp.domain.model.Location
import com.neotelemetrixgdscunand.kamekapp.presentation.mapper.WeatherDuiMapper
import com.neotelemetrixgdscunand.kamekapp.presentation.util.UIText
import com.neotelemetrixgdscunand.kamekapp.presentation.util.toErrorUIText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
    private val weatherRepository: WeatherRepository,
    private val locationManager: LocationManager,
    private val mapper: WeatherDuiMapper
) : ViewModel() {

    private val _uiEvent = Channel<HomeUIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val diagnosisHistory: StateFlow<ImmutableList<DiagnosisSessionPreview>> =
        repository.getAllSavedDiagnosisSessionPreviews()
            .map {
                it.toImmutableList()
            }
            .flowOn(Dispatchers.Default)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                persistentListOf()
            )

    private var locationUpdateJob: Job? = null

    private val _currentLocation = MutableStateFlow<Location?>(null)
    val currentLocation = _currentLocation.asStateFlow()

    fun startLocationUpdates() {
        locationUpdateJob?.cancel()
        locationUpdateJob = viewModelScope.launch {
            locationManager
                .getLocationUpdated()
                .flowOn(Dispatchers.IO)
                .collectLatest { result ->
                    when(result){
                        is Result.Error -> {
                            when(result.error){
                                is LocationError.ResolvableSettingsError -> {
                                    _uiEvent.send(HomeUIEvent.OnLocationResolvableError(result.error.exception))
                                }
                                is LocationError.UnknownError -> {
                                    val errorUIText = result.toErrorUIText()
                                    _uiEvent.send(HomeUIEvent.OnLocationUnknownError(errorUIText))
                                }
                                is LocationError.UnexpectedErrorWithMessage -> {
                                    val errorUIText = UIText.DynamicString(result.error.message)
                                    _uiEvent.send(HomeUIEvent.OnLocationUnknownError(errorUIText))
                                }
                            }
                            _currentLocation.update { null }
                        }
                        is Result.Success -> {
                            val location = result.data
                            _currentLocation.update { location }
                        }
                    }
                }
        }
    }

    fun stopLocationUpdates() {
        locationUpdateJob?.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val weatherForecastOverview = _currentLocation.flatMapLatest { location ->
        weatherRepository.getWeatherForecastOverviewAutoUpdate(
            latitude = location?.latitude ?: LocationManager.FALLBACK_LOCATION_LATITUDE,
            longitude = location?.longitude ?: LocationManager.FALLBACK_LOCATION_LONGITUDE
        ).map { result ->
            when (result) {
                is Result.Error -> {
                    val errorUIText = result.toErrorUIText()
                    _uiEvent.send(
                        HomeUIEvent.OnFailedFetchWeatherForecast(errorUIText)
                    )
                    null
                }

                is Result.Success -> {
                    return@map mapper.mapWeatherForecastOverviewToDui(result.data)
                }
            }
        }
    }.flowOn(Dispatchers.IO)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            null
        )


}