package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey90
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.diagnosishistory.getDummyDiagnosisHistoryItems
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.home.ExplorationSection
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.home.HomeDiagnosisHistory
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.home.HomeHeaderSection
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.home.SectionHeadline
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.home.WeeklyNews
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.home.getDummyWeeklyNewsItems

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    val parentListState = rememberLazyListState()
    val diagnosisHistoryListState = rememberLazyListState()

    val diagnosisHistoryItems = remember {
        getDummyDiagnosisHistoryItems()
    }

    val weeklyNewsItems = remember {
        getDummyWeeklyNewsItems()
    }

    LazyColumn(
        modifier = modifier
            .background(Grey90)
            .fillMaxSize(),
        state = parentListState
    ) {

        item {
            HomeHeaderSection()

            Spacer(Modifier.height(32.dp))

            ExplorationSection()

            Spacer(Modifier.height(32.dp))
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

                items(diagnosisHistoryItems, { it.id}){ item ->
                    HomeDiagnosisHistory(item = item)
                }
            }
        }


        item {
            Spacer(
                Modifier.height(32.dp)
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
            WeeklyNews(item = item)
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
        HomeScreen()
    }
}