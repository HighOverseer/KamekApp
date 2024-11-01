package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.screen

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Black10
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey60
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey68
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey90
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon55
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon60
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.diagnosishistory.DiagnosisHistory
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.diagnosishistory.SearchBar
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.diagnosishistory.SearchHistoryCategory
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.diagnosishistory.getDummyDiagnosisHistoryItems
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.util.dashedBorder
import kotlin.math.max


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiagnosisScreen(modifier: Modifier = Modifier) {

    var searchQuery by remember {
        mutableStateOf("")
    }

    var isSearchBarActive by remember {
        mutableStateOf(false)
    }

    val selectedSearchHistoryCategory by remember {
        mutableStateOf(SearchHistoryCategory.ALL)
    }

    val diagnosisHistories = remember {
        getDummyDiagnosisHistoryItems()
    }

    val listCategoryState = rememberLazyListState()
    val parentListState = rememberLazyListState()

    val topMarginRatio = 0.049f

    val firstCardToTitleRatio = 0.057f

    val secondHeadlineToCardMarginRatio = 0.0422f

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

    BoxWithConstraints {
        val maxHeight = this.maxHeight

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
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
                    Card(
                        modifier = cardModifier,
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                            contentColor = Color.White
                        ),

                        ) {

                        Column(
                            Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        shape = CircleShape,
                                        color = Maroon55
                                    )
                                    .defaultMinSize(60.dp, 60.dp)
                            ) {
                                Image(
                                    modifier = Modifier.align(Alignment.Center),
                                    imageVector = ImageVector
                                        .vectorResource(R.drawable.ic_open_camera),
                                    contentDescription = null
                                )
                            }

                            Spacer(Modifier.height(16.dp))

                            Text(
                                text = stringResource(R.string.ambil_foto_penyakit_pada_tanaman),
                                style = MaterialTheme.typography.titleMedium,
                                color = Maroon55
                            )

                            Spacer(Modifier.height(8.dp))

                            Text(
                                stringResource(R.string.tekan_gambar_di_atas),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Grey60
                            )
                        }
                    }
                }

            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal =  16.dp)
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
                        isActive = isSearchBarActive,
                    )

                    Spacer(Modifier.height(12.dp))
                }

            }

            item {
                LazyRow(
                    state = listCategoryState,
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(SearchHistoryCategory.entries, key = { it.ordinal }){
                        Box(
                            modifier = Modifier
                                .background(
                                    color = if(it == selectedSearchHistoryCategory) Maroon55 else Color.White,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                        ){
                            Text(
                                text = stringResource(it.textResId),
                                style = MaterialTheme.typography.labelMedium,
                                color = if(it == selectedSearchHistoryCategory) Color.White else Grey68
                            )
                        }

                    }
                }
            }

            items(diagnosisHistories, { it.id }){
                DiagnosisHistory(
                    item = it,
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