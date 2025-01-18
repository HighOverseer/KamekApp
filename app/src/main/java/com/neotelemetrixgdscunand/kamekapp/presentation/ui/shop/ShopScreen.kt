package com.neotelemetrixgdscunand.kamekapp.presentation.ui.shop

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Black10
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey90
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.news.NewsCategory
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.diagnosishistory.SearchBar
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.diagnosishistory.SearchCategory

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShopScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
) {
    var searchQuery by remember {
        mutableStateOf("")
    }
    val searchBarInteractionSource = remember {
        MutableInteractionSource()
    }
    val selectedCategory by remember {
        mutableStateOf(
            NewsCategory.ALL
        )
    }

    val lazyGridState = rememberLazyGridState()

    val shopItems = remember {
        getShopItemDataDummies()
    }

    Column(
        modifier = modifier
            .background(color = Color.White)
    ) {
        Spacer(Modifier.height(20.dp))

        Box(
            Modifier.fillMaxWidth()
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

        Spacer(modifier = Modifier.height(16.dp))

        val isSearchBarFocused by searchBarInteractionSource.collectIsFocusedAsState()
        val listCategoryState = rememberLazyListState()

        SearchBar(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            query = searchQuery,
            onQueryChange = {
                searchQuery = it
            },
            backgroundColor = Grey90,
            hint = stringResource(R.string.cari_segala_hal_terkait_dunia_perkebunan),
            interactionSource = searchBarInteractionSource,
            isActive = isSearchBarFocused,
        )

        Spacer(Modifier.height(12.dp))

        LazyRow(
            state = listCategoryState,
            contentPadding = PaddingValues(start = 16.dp, bottom = 8.dp, end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(NewsCategory.entries, key = { it.ordinal }){
                SearchCategory(
                    unselectedColor = Grey90,
                    isSelected = it == selectedCategory,
                    text = stringResource(it.textResId)
                )
            }
        }

        LazyVerticalGrid(
            modifier = Modifier
                .background(color = Grey90),
            contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp, start = 16.dp, end = 16.dp),
            state = lazyGridState,
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {


            items(
                shopItems.size,
                key = { it.hashCode() }
            ){

                ShopItem(
                    imageUrl = shopItems[it].imageUrl,
                    title = shopItems[it].title,
                    price = shopItems[it].price,
                    targetUrl = shopItems[it].targetUrl
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ShopScreenPreview() {
    KamekAppTheme {
        ShopScreen()
    }
}