package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neotelemetrixgdscunand.kamekapp.domain.common.Result
import com.neotelemetrixgdscunand.kamekapp.domain.data.Repository
import com.neotelemetrixgdscunand.kamekapp.domain.data.WeatherRepository
import com.neotelemetrixgdscunand.kamekapp.domain.model.DiagnosisSessionPreview
import com.neotelemetrixgdscunand.kamekapp.presentation.mapper.WeatherDuiMapper
import com.neotelemetrixgdscunand.kamekapp.presentation.util.UIText
import com.neotelemetrixgdscunand.kamekapp.presentation.util.toErrorUIText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
    private val weatherRepository: WeatherRepository,
    private val mapper:WeatherDuiMapper
) : ViewModel() {

    private val _onMessageEvent = Channel<UIText>()
    val onMessageEvent = _onMessageEvent.receiveAsFlow()

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

    private val padangCoordinate = Pair(-0.948041f, 100.36309f)
    val weatherForecastOverview = weatherRepository.getWeatherForecastOverviewAutoUpdate(
        latitude = padangCoordinate.first,
        longitude = padangCoordinate.second
    ).map { result ->
        when(result){
            is Result.Error -> {
                val errorUIText = result.toErrorUIText()
                _onMessageEvent.send(errorUIText)
                null
            }
            is Result.Success -> {
                return@map mapper.mapWeatherForecastOverviewToDui(result.data)
            }
        }
    }.flowOn(Dispatchers.IO)
        .stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )
}