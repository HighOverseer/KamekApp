package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.diagnosishistory

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.domain.model.DiagnosisSessionPreview
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Black10
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey90
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.diagnosishistory.component.DiagnosisHistory
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.diagnosishistory.component.ScrollUpButton
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.diagnosishistory.component.SearchBar
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.diagnosishistory.component.SearchCategory
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.diagnosishistory.component.SearchHistoryCategory
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch

@Composable
fun DiagnosisScreen(
    modifier: Modifier = Modifier,
    viewModel: DiagnosisViewModel = hiltViewModel(),
    navigateToDiagnosisResult: (Int) -> Unit = { _ -> },
    expandTopAppBar: () -> Unit = { },
) {

    val diagnosisHistories by viewModel.diagnosisHistoryPreview.collectAsState()

    DiagnosisContent(
        modifier = modifier,
        diagnosisSessionPreviews = diagnosisHistories,
        navigateToDiagnosisResult = navigateToDiagnosisResult,
        expandTopAppBar = expandTopAppBar,
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DiagnosisContent(
    modifier: Modifier = Modifier,
    diagnosisSessionPreviews: ImmutableList<DiagnosisSessionPreview> = persistentListOf(),
    navigateToDiagnosisResult: (Int) -> Unit = { _ -> },
    expandTopAppBar: () -> Unit = { },
) {

    val selectedSearchHistoryCategory by remember {
        mutableStateOf(SearchHistoryCategory.ALL)
    }

    Box(
        Modifier.fillMaxSize()
    ) {
        val lazyColumnModifier = remember {
            modifier
                .fillMaxWidth()
                .background(color = Grey90)
        }
        val configuration = LocalConfiguration.current
        val contentPadding = remember(diagnosisSessionPreviews) {
            val divider = if (diagnosisSessionPreviews.size < 5) 3 else 10
            PaddingValues(bottom = configuration.screenHeightDp.dp / divider)
        }
        val parentListState = rememberLazyListState()

        val list = remember {
            List(10) {
                DiagnosisSessionPreview(
                    id = it,
                    title = "Example",
                    imageUrlOrPath = "",
                    date = "12-05-2024",
                    predictedPrice = 1400f
                )
            }.toPersistentList()
        }
        LazyColumn(
            modifier = lazyColumnModifier,
            state = parentListState,
            contentPadding = contentPadding
        ) {
            stickyHeader {
                Column(
                    Modifier
                        .padding(horizontal = 16.dp)
                        .background(Grey90)
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
                        queryProvider = { searchQuery },
                        onQueryChange = {
                            searchQuery = it
                        },
                        hint = stringResource(R.string.cari_histori_hasil_diagnosis),
                        interactionSource = searchBarInteractionSource,
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
            items(list, key = { it.id }, contentType = { it::class }) { item ->
                DiagnosisHistory(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    item = item,
                    onClick = {
                        navigateToDiagnosisResult(item.id)
                    }
                )

                Spacer(Modifier.height(16.dp))
            }
        }


        val coroutineScope = rememberCoroutineScope()
        ScrollUpButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxHeight(0.15f)
                .fillMaxWidth(0.25f),
            lazyListStateProvider = { parentListState },
            onClick = {
                coroutineScope.launch {
                    parentListState.scrollToItem(0)
                    expandTopAppBar()
                }
            }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
private fun DiagnosisScreenPreview() {
    KamekAppTheme {
        DiagnosisContent(
            diagnosisSessionPreviews = List(10) {
                DiagnosisSessionPreview(
                    id = it,
                    title = "Example",
                    imageUrlOrPath = "",
                    date = "12-05-2024",
                    predictedPrice = 1400f
                )
            }.toPersistentList()
        )
    }

}
