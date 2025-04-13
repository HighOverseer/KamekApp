package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.diagnosishistory

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.domain.model.DiagnosisSessionPreview
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Black10
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey90
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon45
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon45Alpha70
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon55
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.diagnosishistory.component.DiagnosisHistory
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.diagnosishistory.component.SearchBar
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.diagnosishistory.component.SearchCategory
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.diagnosishistory.component.SearchHistoryCategory
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.diagnosishistory.component.TakePhotoSection
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.util.dashedBorder
import kotlinx.coroutines.launch

@Composable
fun DiagnosisScreen(
    modifier: Modifier = Modifier,
    viewModel: DiagnosisViewModel = hiltViewModel(),
    navigateToTakePhoto: () -> Unit = {},
    navigateToDiagnosisResult: (Int) -> Unit = { _ -> }
) {

    val diagnosisHistories by viewModel.diagnosisHistoryPreview.collectAsState()

    DiagnosisContent(
        modifier = modifier,
        navigateToTakePhoto = navigateToTakePhoto,
        diagnosisSessionPreviews = diagnosisHistories,
        navigateToDiagnosisResult = navigateToDiagnosisResult
    )

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DiagnosisContent(
    modifier: Modifier = Modifier,
    navigateToTakePhoto: () -> Unit = {},
    diagnosisSessionPreviews: List<DiagnosisSessionPreview> = emptyList(),
    navigateToDiagnosisResult: (Int) -> Unit = { _ -> }
) {

    val selectedSearchHistoryCategory by remember {
        mutableStateOf(SearchHistoryCategory.ALL)
    }

    val diagnosisHistoryModifier = remember {
        Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    }

    val parentListState = rememberLazyListState()

    Box {
        LazyColumn(
            modifier = modifier,
            state = parentListState
        ) {

            item {
                val configuration = LocalConfiguration.current
                val screenHeightDp = configuration.screenHeightDp.dp

                val topMarginRatio = 0.039f
                Spacer(Modifier.height(topMarginRatio * screenHeightDp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {

                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.diagnosis),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Black10
                    )

                    val firstCardToTitleRatio = 0.037f
                    Spacer(Modifier.height(screenHeightDp * firstCardToTitleRatio))
                }
            }


            item {
                TakePhotoSection(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), onClick = navigateToTakePhoto)

                Spacer(Modifier.height(16.dp))
            }

            stickyHeader {
                val stickyHeaderModifier = remember {
                    Modifier
                        .fillMaxWidth()
                        .background(color = Grey90)
                        .padding(horizontal = 16.dp)
                }

                Column(
                    modifier = stickyHeaderModifier
                ) {

                    Spacer(Modifier.height(16.dp))

                    Text(
                        stringResource(R.string.riwayat_diagnosis),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Black10
                    )

                    Spacer(Modifier.height(16.dp))

                    val searchBarInteractionSource = remember {
                        MutableInteractionSource()
                    }

                    val isSearchBarFocused by searchBarInteractionSource.collectIsFocusedAsState()
                    var searchQuery by remember {
                        mutableStateOf("")
                    }
                    SearchBar(
                        query = searchQuery,
                        onQueryChange = {
                            searchQuery = it
                        },
                        hint = stringResource(R.string.cari_histori_hasil_diagnosis),
                        provideInteractionSource = { searchBarInteractionSource },
                        isActive = isSearchBarFocused,
                    )

                    Spacer(Modifier.height(12.dp))
                }

                val listCategoryState = rememberLazyListState()
                LazyRow(
                    state = listCategoryState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Grey90),
                    contentPadding = PaddingValues(start = 16.dp, bottom = 8.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(SearchHistoryCategory.entries, key = { it.ordinal }) {
                        SearchCategory(
                            isSelected = it == selectedSearchHistoryCategory,
                            text = stringResource(it.textResId)
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

            }

            items(diagnosisSessionPreviews, {it.id }) { item ->
                DiagnosisHistory(
                    modifier = diagnosisHistoryModifier
                        .clickable {
                            navigateToDiagnosisResult(item.id)
                        },
                    item = item,
                )

                Spacer(Modifier.height(16.dp))
            }
        }

        val isScrollUpButtonVisible by remember {
            derivedStateOf {
                parentListState.firstVisibleItemIndex > 1
            }
        }

        AnimatedVisibility(
            visible = isScrollUpButtonVisible,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxHeight(0.15f)
                .fillMaxWidth(0.25f)
        ) {
            val coroutineScope = rememberCoroutineScope()
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        parentListState.animateScrollToItem(index = 0)
                    }
                },
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(Maroon45Alpha70, shape = CircleShape)
                        .padding(8.dp)
                ){
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = null,
                        tint = Color.White,
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
private fun DiagnosisScreenPreview() {
    KamekAppTheme {
        DiagnosisContent(
            diagnosisSessionPreviews = List(10){
                DiagnosisSessionPreview(
                    id = it,
                    title = "Example",
                    imageUrlOrPath = "",
                    date = "12-05-2024",
                    predictedPrice = 1400f
                )
            }
        )
    }

}