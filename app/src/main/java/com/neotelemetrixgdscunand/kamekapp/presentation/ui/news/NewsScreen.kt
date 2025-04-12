package com.neotelemetrixgdscunand.kamekapp.presentation.ui.news

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Black10
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey60
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey90
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon55
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.diagnosishistory.component.SearchBar
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.diagnosishistory.component.SearchCategory
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.home.component.WeeklyNews
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.home.component.getDummyWeeklyNewsItems

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewsScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
    navigateToDetail: () -> Unit = {}
) {
    val listState = rememberLazyListState()
    val configuration = LocalConfiguration.current

    var searchQuery by remember {
        mutableStateOf("")
    }

    val searchBarInteractionSource = remember {
        MutableInteractionSource()
    }

    var selectedNewsCategory by remember {
        mutableStateOf(NewsCategory.ALL)
    }

    var newsItems = remember {
        getDummyWeeklyNewsItems()
    }

    val weeklyItemModifier = remember {
        Modifier
            .padding(start = 16.dp, end = 16.dp)
    }


    Box {
        val imageBackgroundSize = remember {
            val imageAspectRatio = when(configuration.orientation){
                Configuration.ORIENTATION_PORTRAIT -> 0.205f
                else -> 0.5f
            }
            (configuration.screenHeightDp * imageAspectRatio).dp
        }
        Image(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .width(imageBackgroundSize)
                .aspectRatio(0.655f),
            painter = painterResource(R.drawable.news_bg),
            contentScale = ContentScale.FillBounds,
            contentDescription = null
        )

        val topMargin = remember{
            val topMarginRatio = 0.035f
            (topMarginRatio * configuration.screenHeightDp).dp
        }

        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(top = topMargin, bottom = 32.dp),
            state = listState,
        ) {

            item {
                IconButton(
                    modifier = Modifier
                        .padding(start = 8.dp),
                    onClick = navigateUp,

                    ) {
                    Icon(
                        modifier = Modifier
                            .size(20.dp),
                        imageVector = ImageVector
                            .vectorResource(R.drawable.ic_arrow_left),
                        contentDescription = stringResource(R.string.kembali),
                        tint = Black10
                    )
                }
            }

            item {
                Spacer(Modifier.height(16.dp))
            }

            item {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    text = stringResource(R.string.dunia_tanaman),
                    style = MaterialTheme.typography.displaySmall,
                    color = Maroon55
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    text = stringResource(R.string.pelajari_dan_cari_cerita_terbaru_seputar_dunia_pertanian),
                    style = MaterialTheme.typography.labelMedium,
                    color = Grey60
                )
            }

            item {
                Spacer(Modifier.height(32.dp))
            }


            stickyHeader {
                val isSearchBarFocused by searchBarInteractionSource.collectIsFocusedAsState()
                val listCategoryState = rememberLazyListState()

                SearchBar(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    query = searchQuery,
                    onQueryChange = {
                        searchQuery = it
                    },
                    hint = stringResource(R.string.cari_segala_hal_terkait_dunia_perkebunan),
                    interactionSource = searchBarInteractionSource,
                    isActive = isSearchBarFocused,
                )

                Spacer(Modifier.height(12.dp))

                LazyRow(
                    state = listCategoryState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Grey90),
                    contentPadding = PaddingValues(start = 16.dp, bottom = 8.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(NewsCategory.entries, key = { it.ordinal }) {
                        SearchCategory(
                            isSelected = it == selectedNewsCategory,
                            text = stringResource(it.textResId)
                        )
                    }
                }
            }

            itemsIndexed(newsItems, key = { _, it -> it.id }) { index, item ->
                Spacer(
                    Modifier.height(
                        if (index == 0) 8.dp else 16.dp
                    )
                )

                WeeklyNews(
                    modifier = weeklyItemModifier
                        .clickable(onClick = navigateToDetail),
                    item = item
                )
            }
        }
    }


}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
private fun NewsScreenPreview() {
    KamekAppTheme {
        NewsScreen()
    }
}