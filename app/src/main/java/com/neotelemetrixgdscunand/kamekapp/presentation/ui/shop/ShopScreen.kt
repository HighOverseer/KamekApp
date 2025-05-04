package com.neotelemetrixgdscunand.kamekapp.presentation.ui.shop

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.domain.model.getShopItemDataDummies
import com.neotelemetrixgdscunand.kamekapp.presentation.dui.ShopItemDui
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Black10
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey90
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.news.NewsCategory
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.diagnosishistory.component.SearchBar
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.diagnosishistory.component.SearchCategory
import com.neotelemetrixgdscunand.kamekapp.presentation.utils.collectChannelWhenStarted
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ShopScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
    showSnackbar: (String) -> Unit = {},
    viewModel: ShopViewModel = hiltViewModel()
) {
    val lifecycle = LocalLifecycleOwner.current
    val context = LocalContext.current
    LaunchedEffect(true) {
        lifecycle.collectChannelWhenStarted(viewModel.onMessageEvent){ messageUIText ->
            val message = messageUIText.getValue(context)
            showSnackbar(message)
        }
    }

    val shopItems by viewModel.shopItems.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val isSearchingByQuery by remember {
        derivedStateOf {
            searchQuery.isNotEmpty()
        }
    }

    ShopContent(
        modifier = modifier,
        navigateUp = navigateUp,
        shopItemsProvider = { shopItems },
        isLoadingProvider = { isLoading },
        searchQueryProvider = { searchQuery },
        onQueryChange = viewModel::onQueryChange,
        isSearchingByQueryProvider = { isSearchingByQuery }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopContent(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
    shopItemsProvider: () -> ImmutableList<ShopItemDui> = { persistentListOf() },
    isLoadingProvider: () -> Boolean = { false },
    searchQueryProvider: () -> String = { "" },
    onQueryChange: (String) -> Unit = {},
    isSearchingByQueryProvider:() -> Boolean = { false },
    ) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val focusManager = LocalFocusManager.current
    val parentModifier = remember {
        modifier
            .fillMaxSize()
            .background(Color.White)
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .pointerInput(Unit){
                detectTapGestures {
                    focusManager.clearFocus()
                }
            }
    }


    Scaffold(
        containerColor = Color.White,
        modifier = parentModifier,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors().copy(
                    scrolledContainerColor = Color.White,
                    containerColor = Color.White
                ),
                title = {},
                scrollBehavior = scrollBehavior,
                actions = {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                    ) {
                        IconButton(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .align(Alignment.CenterStart),
                            onClick = navigateUp
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(20.dp),
                                imageVector = ImageVector
                                    .vectorResource(R.drawable.ic_arrow_left),
                                tint = Black10,
                                contentDescription = stringResource(R.string.kembali)
                            )
                        }
                        Text(
                            modifier = Modifier
                                .align(Alignment.Center),
                            text = stringResource(R.string.toko),
                            style = MaterialTheme.typography.headlineSmall,
                            color = Black10
                        )
                    }
                }
            )

        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)

        ) {
            ShopContentBody(
                isSearchingByQueryProvider = isSearchingByQueryProvider,
                isLoadingProvider = isLoadingProvider,
                shopItemsProvider = shopItemsProvider,
                searchQueryProvider = searchQueryProvider,
                onQueryChange = onQueryChange
            )
        }

    }
}

@Composable
fun ShopContentBody(
    modifier: Modifier = Modifier,
    isLoadingProvider: () -> Boolean = { false },
    shopItemsProvider: () -> ImmutableList<ShopItemDui> = { persistentListOf() },
    isSearchingByQueryProvider:() -> Boolean = { false },
    searchQueryProvider: () -> String = { "" },
    onQueryChange: (String) -> Unit = {},
) {
    val selectedCategory by remember {
        mutableStateOf(
            NewsCategory.ALL
        )
    }

    val searchBarInteractionSource = remember {
        MutableInteractionSource()
    }

    Spacer(Modifier.height(8.dp))

    SearchBar(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        queryProvider = searchQueryProvider,
        onQueryChange = onQueryChange,
        backgroundColor = Grey90,
        hint = stringResource(R.string.cari_segala_hal_terkait_dunia_perkebunan),
        interactionSource = searchBarInteractionSource,
    )

    Spacer(Modifier.height(8.dp))

    val listCategoryState = rememberLazyListState()
    LazyRow(
        state = listCategoryState,
        contentPadding = PaddingValues(start = 16.dp, bottom = 8.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(NewsCategory.entries, key = { item -> item.ordinal }) { item ->
            SearchCategory(
                unselectedColor = Grey90,
                isSelected = item == selectedCategory,
                text = stringResource(item.textResId)
            )
        }
    }

    val lazyGridState = rememberLazyGridState()

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Grey90),
        contentPadding = PaddingValues(
            top = 16.dp,
            bottom = 24.dp,
            start = 16.dp,
            end = 16.dp
        ),
        state = lazyGridState,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if(isLoadingProvider()){
            items(6){
                ShopItemLoading()
            }
        }else{
            val shopItems = shopItemsProvider()

            if(shopItems.isEmpty()){
                item(span = { GridItemSpan(2) }){
                    val text = if(isSearchingByQueryProvider()) {
                        stringResource(R.string.barang_yang_anda_cari_tidak_ditemukan)
                    } else {
                        stringResource(R.string.belum_ada_barang_yang_tersedia)
                    }

                    Spacer(Modifier.height(32.dp))

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = text,
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }else {
                items(
                    shopItems.size,
                    key = { index ->
                        shopItems[index].id
                    }
                ) { index ->
                    val currentShopItem = shopItems[index]
                    ShopItem(
                        imageUrl = currentShopItem.imageUrl,
                        title = currentShopItem.title,
                        price = currentShopItem.price,
                        targetUrl = currentShopItem.targetUrl
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun ShopScreenPreview() {
    KamekAppTheme {
        ShopScreen(Modifier)
    }
}