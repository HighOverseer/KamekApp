package com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.priceanalysis.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.neotelemetrixgdscunand.kamekapp.domain.model.CocoaDisease
import com.neotelemetrixgdscunand.kamekapp.domain.model.DamageLevelCategory
import com.neotelemetrixgdscunand.kamekapp.domain.model.DetectedCocoa
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon55
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Orange80
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component.SecondaryDescription
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component.TitleShimmeringLoading
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.diseasediagnosis.component.DescriptionShimmeringLoading
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap

@Composable
fun PriceAnalysisContent(
    modifier: Modifier = Modifier,
    isInitiallyExpanded: Boolean = true,
    groupedDetectedDisease: ImmutableMap<CocoaDisease, ImmutableList<DetectedCocoa>> =
        mutableMapOf<CocoaDisease, ImmutableList<DetectedCocoa>>().toImmutableMap(),
    damageLevelCategory: DamageLevelCategory = DamageLevelCategory.High,
    onDetectedCacaoImageClicked: (Int) -> Unit = { }
) {

    var isExpand by remember {
        mutableStateOf(isInitiallyExpanded)
    }

    Column(
        modifier = modifier
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                stringResource(damageLevelCategory.titleResId),
                style = MaterialTheme.typography.titleMedium,
                color = Orange80,
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = {
                    isExpand = !isExpand
                },
                modifier = Modifier
                    .width(24.dp)
                    .height(14.dp),
            ) {

                val drawableResId = remember(isExpand) {
                    if (isExpand) {
                        R.drawable.ic_down_arrow
                    } else R.drawable.ic_right_arrow
                }

                Icon(
                    imageVector = ImageVector
                        .vectorResource(drawableResId),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .width(14.dp)
                        .height(14.dp)
                )
            }
        }

        if (isExpand) {
            Spacer(Modifier.height(24.dp))

            SecondaryDescription(
                title = stringResource(R.string.tingkat_serangan_penyakit),
                description = stringResource(damageLevelCategory.descriptionResId)
            )

            Spacer(Modifier.height(24.dp))

            SecondaryDescription(
                title = stringResource(R.string.bobot_buah),
                description = "1 Kg"
            )

            Spacer(Modifier.height(24.dp))

            PriceAnalysisDetails(
                groupedDetectedDisease = groupedDetectedDisease,
                subDamageLevelSubCategory = damageLevelCategory.firstSubLevelCategory,
                onDetectedCacaoImageClicked = onDetectedCacaoImageClicked
            )

            Spacer(Modifier.height(16.dp))

            PriceAnalysisDetails(
                groupedDetectedDisease = groupedDetectedDisease,
                subDamageLevelSubCategory = damageLevelCategory.secondSubLevelCategory,
                onDetectedCacaoImageClicked = onDetectedCacaoImageClicked
            )

            Spacer(Modifier.height(16.dp))

            PriceAnalysisDetails(
                groupedDetectedDisease = groupedDetectedDisease,
                subDamageLevelSubCategory = damageLevelCategory.thirdSubLevelCategory,
                onDetectedCacaoImageClicked = onDetectedCacaoImageClicked
            )

        }

        Spacer(Modifier.height(24.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .border(1.dp, color = Maroon55, shape = RoundedCornerShape(8.dp))
                .padding(vertical = 12.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                stringResource(R.string.sub_total_harga_jual),
                style = MaterialTheme.typography.titleMedium,
                color = Maroon55,
                modifier = Modifier
                    .weight(1f)
            )

            Text(
                "Rp 3.000 - Rp 6.000/kg",
                style = MaterialTheme.typography.labelMedium,
                color = Maroon55
            )
        }
    }
}


@Composable
fun PriceAnalysisContentLoading(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
    ) {


        TitleShimmeringLoading(
            height = 17.dp,
            widthRatio = 0.3f,
        )

        Spacer(Modifier.height(8.dp))

        DescriptionShimmeringLoading(
            lineHeight = 17.dp,
            lastLineWidthRatio = 1f,
            lineCount = 1
        )

        Spacer(Modifier.height(24.dp))

        TitleShimmeringLoading(
            height = 17.dp,
            widthRatio = 0.2f,
        )

        Spacer(Modifier.height(8.dp))

        DescriptionShimmeringLoading(
            lineHeight = 17.dp,
            lastLineWidthRatio = 1f,
            lineCount = 1
        )

        Spacer(Modifier.height(24.dp))

        TitleShimmeringLoading(
            height = 17.dp,
            widthRatio = 0.3f,
        )

        Spacer(Modifier.height(8.dp))

        DescriptionShimmeringLoading(
            lineHeight = 17.dp,
            lastLineWidthRatio = 0.7f,
            lineCount = 3
        )

        Spacer(Modifier.height(24.dp))

        TitleShimmeringLoading(
            height = 17.dp,
            widthRatio = 0.5f,
        )

        Spacer(Modifier.height(8.dp))

        DescriptionShimmeringLoading(
            lineHeight = 17.dp,
            lastLineWidthRatio = 0.7f,
            lineCount = 2
        )

        Spacer(Modifier.height(24.dp))

        TitleShimmeringLoading(
            height = 17.dp,
            widthRatio = 0.6f,
        )

        Spacer(Modifier.height(8.dp))

        DescriptionShimmeringLoading(
            lineHeight = 17.dp,
            lastLineWidthRatio = 1f,
            lineCount = 1
        )


    }

}

@Preview(showBackground = true, heightDp = 1500)
@Composable
private fun PriceAnalysisContentPreview() {
    KamekAppTheme {
        PriceAnalysisContent()
    }
}

