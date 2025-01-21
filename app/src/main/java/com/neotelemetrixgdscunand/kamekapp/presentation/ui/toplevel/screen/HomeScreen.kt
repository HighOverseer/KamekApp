package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey90
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.diagnosishistory.DiagnosisHistoryItemData
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.home.ExplorationSection
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.home.HomeDiagnosisHistory
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.home.HomeHeaderSection
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.home.PriceInfoSection
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.home.SectionHeadline
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.home.WeeklyNews
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.home.getDummyWeeklyNewsItems
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToNews:()->Unit = {},
    navigateToShop:()->Unit = {},
    navigateToWeather:()->Unit = {},
    navigateToNewsDetail:()->Unit = {},
    navigateToDiagnosisResult:(Int, String)->Unit = {_, _ -> },
    navigateToNotification:()-> Unit = {},
    showSnackbar:(String)->Unit = {}
) {

    val diagnosisHistories by viewModel.diagnosisHistory.collectAsState()

    HomeContent(
        modifier = modifier,
        navigateToNews = navigateToNews,
        navigateToShop = navigateToShop,
        navigateToWeather = navigateToWeather,
        navigateToNewsDetail = navigateToNewsDetail,
        diagnosisHistories = diagnosisHistories,
        navigateToDiagnosisResult = navigateToDiagnosisResult,
        navigateToNotification = navigateToNotification,
        showSnackbar = showSnackbar
    )
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    navigateToNews:()->Unit = {},
    navigateToShop:()->Unit = {},
    navigateToWeather:()->Unit = {},
    navigateToNewsDetail:()->Unit = {},
    diagnosisHistories:List<DiagnosisHistoryItemData> = emptyList(),
    navigateToDiagnosisResult: (Int, String) -> Unit = { _, _ -> },
    navigateToNotification:()-> Unit = {},
    showSnackbar:(String)->Unit = {}
){
    val parentListState = rememberLazyListState()
    val diagnosisHistoryListState = rememberLazyListState()


    val weeklyNewsItems = remember {
        getDummyWeeklyNewsItems()
    }

    val weeklyItemModifier = remember {
        Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .clickable(onClick = navigateToNewsDetail)
    }


    LazyColumn(
        modifier = modifier
            .background(Grey90),
        state = parentListState
    ) {

        item {
            HomeHeaderSection(
                navigateToNotification = navigateToNotification
            )
        }

        item{
            PriceInfoSection()
        }

        item {
            ExplorationSection(
                navigateToNews = navigateToNews,
                navigateToShop = navigateToShop,
                navigateToWeather = navigateToWeather,
                showSnackbar = showSnackbar
            )

            Spacer(Modifier.height(24.dp))
        }

        item {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                SectionHeadline(
                    leadingIconResId = R.drawable.ic_camera_gradient,
                    trailingIconResId = R.drawable.ic_right_arrow,
                    title = stringResource(R.string.riwayat_diagnosis)
                )
            }
        }

        item {
            Spacer(
                Modifier.height(16.dp)
            )
        }

        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                state = diagnosisHistoryListState
            ) {
                items(diagnosisHistories, {it.id}){ item ->
                    HomeDiagnosisHistory(
                        modifier = Modifier
                            .clickable{
                                navigateToDiagnosisResult(item.outputId, item.imageUrlOrPath)
                            },
                        item = item
                    )
                }
            }
        }


        item {
            Spacer(
                Modifier.height(24.dp)
            )
        }

        item {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                SectionHeadline(
                    title = stringResource(R.string.berita_mingguan_kamek),
                    leadingIconResId = R.drawable.ic_news_gradient
                )
            }
        }

        items(weeklyNewsItems, { it.id }){ item ->
            WeeklyNews(
                modifier = weeklyItemModifier,
                item = item
            )
        }

        item {
            Spacer(Modifier.height(16.dp))
        }


    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    KamekAppTheme {
        HomeContent()
    }
}