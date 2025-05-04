package com.neotelemetrixgdscunand.kamekapp.presentation.ui.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neotelemetrixgdscunand.kamekapp.domain.common.Result
import com.neotelemetrixgdscunand.kamekapp.domain.data.ShopRepository
import com.neotelemetrixgdscunand.kamekapp.presentation.dui.ShopItemDui
import com.neotelemetrixgdscunand.kamekapp.presentation.mapper.DuiMapper
import com.neotelemetrixgdscunand.kamekapp.presentation.utils.UIText
import com.neotelemetrixgdscunand.kamekapp.presentation.utils.toErrorUIText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@OptIn(FlowPreview::class)
@HiltViewModel
class ShopViewModel @Inject constructor(
    private val shopRepository: ShopRepository,
    private val duiMapper: DuiMapper
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _onMessageEvent = Channel<UIText>()
    val onMessageEvent = _onMessageEvent.receiveAsFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _shopItems = MutableStateFlow<ImmutableList<ShopItemDui>>(persistentListOf())
    val shopItems = _shopItems.asStateFlow()

    private val searchQueryDebounceDuration = 1000L

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(searchQueryDebounceDuration)
                .collectLatest { query ->
                    try {
                        _isLoading.update { true }
                        getShopItems(query)
                    } finally {
                        _isLoading.update { false }
                    }
                }
        }
    }

    fun onQueryChange(newQuery: String) {
        _searchQuery.update { newQuery }
    }

    private suspend fun getShopItems(query: String = "") {
        when (val result = shopRepository.getShopItems(
            query = query,
        )
        ) {
            is Result.Error -> {
                coroutineContext.ensureActive()
                val errorUIText = result.toErrorUIText()
                _onMessageEvent.send(errorUIText)
            }

            is Result.Success -> {
                coroutineContext.ensureActive()
                val shopItemsDui = result.data.map {
                    duiMapper.mapShopItemToShopItemDui(it)
                }.toImmutableList()

                _shopItems.update { shopItemsDui }
            }
        }
    }

}