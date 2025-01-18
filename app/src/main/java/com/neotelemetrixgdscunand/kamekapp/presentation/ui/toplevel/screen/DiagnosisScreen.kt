package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Black10
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey90
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon55
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.diagnosishistory.DiagnosisHistory
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.diagnosishistory.DiagnosisHistoryItemData
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.diagnosishistory.SearchBar
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.diagnosishistory.SearchCategory
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.diagnosishistory.SearchHistoryCategory
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.diagnosishistory.TakePhotoSection
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.viewmodel.DiagnosisViewModel
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.util.dashedBorder

@Composable
fun DiagnosisScreen(
    modifier: Modifier = Modifier,
    viewModel: DiagnosisViewModel = hiltViewModel(),
    navigateToTakePhoto:() -> Unit = {},
    navigateToDiagnosisResult: (Int, String) -> Unit = { _, _ -> }
) {

    val diagnosisHistories by viewModel.diagnosisHistory.collectAsState()

    DiagnosisContent(
        modifier = modifier,
        navigateToTakePhoto = navigateToTakePhoto,
        diagnosisHistories = diagnosisHistories,
        navigateToDiagnosisResult = navigateToDiagnosisResult
    )

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DiagnosisContent(
    modifier: Modifier = Modifier,
    navigateToTakePhoto:() -> Unit = {},
    diagnosisHistories:List<DiagnosisHistoryItemData> = emptyList(),
    navigateToDiagnosisResult: (Int, String) -> Unit = { _, _ -> }
) {

    var searchQuery by remember {
        mutableStateOf("")
    }

    val selectedSearchHistoryCategory by remember {
        mutableStateOf(SearchHistoryCategory.ALL)
    }

    val diagnosisHistoryModifier = remember {
        Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    }

    val listCategoryState = rememberLazyListState()
    val parentListState = rememberLazyListState()

    val topMarginRatio = 0.049f

    val firstCardToTitleRatio = 0.057f

    val secondHeadlineToCardMarginRatio = 0.0422f

    val searchBarInteractionSource = remember {
        MutableInteractionSource()
    }

    val isSearchBarFocused by searchBarInteractionSource.collectIsFocusedAsState()

    val cardModifier = remember {
        Modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .dashedBorder(
                color = Maroon55,
                shape = RoundedCornerShape(8.dp),
                strokeWidth = 1.dp,
                gapLength = 16.dp,
                dashLength = 16.dp
            )
            .padding(
                horizontal = 12.dp,
                vertical = 35.dp
            )
    }

    val stickyHeaderModifier = remember {
        Modifier
            .fillMaxWidth()
            .background(color = Grey90)
            .padding(horizontal =  16.dp)
    }

    BoxWithConstraints(
        modifier = modifier
    ){
        val maxHeight = this.maxHeight

        LazyColumn(
            modifier = Modifier,
            state = parentListState
        ) {

            item {
                Spacer(Modifier.height(topMarginRatio * maxHeight))

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)) {

                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.diagnosis),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Black10
                    )
                    Spacer(Modifier.height(maxHeight * firstCardToTitleRatio))
                }
            }


            item {

                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    TakePhotoSection(modifier = cardModifier, onClick = navigateToTakePhoto)
                }

            }

            stickyHeader {
                Column(
                    modifier = stickyHeaderModifier
                ) {

                    Spacer(Modifier.height(maxHeight * secondHeadlineToCardMarginRatio))

                    Text(
                        stringResource(R.string.riwayat_diagnosis),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Black10
                    )

                    Spacer(Modifier.height(16.dp))

                    SearchBar(
                        query = searchQuery,
                        onQueryChange = {
                            searchQuery = it
                        },
                        hint = stringResource(R.string.cari_histori_hasil_diagnosis),
                        interactionSource = searchBarInteractionSource,
                        isActive = isSearchBarFocused,
                    )

                    Spacer(Modifier.height(12.dp))
                }

                LazyRow(
                    state = listCategoryState,
                    modifier = Modifier
                        .background(color = Grey90),
                    contentPadding = PaddingValues(start = 16.dp, bottom = 8.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(SearchHistoryCategory.entries, key = { it.ordinal }){
                        SearchCategory(
                            isSelected = it == selectedSearchHistoryCategory,
                            text = stringResource(it.textResId)
                        )
                    }
                }

            }

            itemsIndexed(diagnosisHistories, { _, it -> it.id }){ index, item ->
                Spacer(Modifier.height(
                    if(index == 0) 8.dp else 16.dp
                ))

                DiagnosisHistory(
                    modifier = diagnosisHistoryModifier
                        .clickable {
                            navigateToDiagnosisResult(
                                item.outputId,
                                item.imageUrlOrPath
                            )
                        },
                    item = item,
                )
            }


        }
    }

}


@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
private fun DiagnosisScreenPreview() {
    KamekAppTheme {
        DiagnosisScreen()
    }

}