package com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component

import androidx.compose.foundation.background
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
import com.neotelemetrixgdscunand.kamekapp.domain.model.DamageLevelCategory
import com.neotelemetrixgdscunand.kamekapp.domain.model.DamageLevelSubCategory
import com.neotelemetrixgdscunand.kamekapp.domain.model.getDetectedDiseaseDummies
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Black10
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey90
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.home.DetectedCacaoImageGrid

@Composable
fun PriceAnalysisDetails(
    modifier: Modifier = Modifier,
    isInitiallyExpanded:Boolean = false,
    subDamageLevelSubCategory: DamageLevelSubCategory = DamageLevelCategory.Low.secondSubLevelCategory
) {
    var isDetailsExpanded by remember(isInitiallyExpanded) {
        mutableStateOf(isInitiallyExpanded)
    }

    val detectedDiseases = remember {
        getDetectedDiseaseDummies()
    }

    Column(
        modifier = modifier
            .background(color = Grey90, shape = RoundedCornerShape(8.dp))
            .padding(vertical = 16.dp, horizontal = 8.dp),
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                stringResource(subDamageLevelSubCategory.subTitleResId),
                style = MaterialTheme.typography.labelMedium,
                color = Black10,
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = {
                    isDetailsExpanded = !isDetailsExpanded
                },
                modifier = Modifier
                    .width(24.dp)
                    .height(14.dp),
            ) {

                val drawableResId = remember(isDetailsExpanded) {
                    if (isDetailsExpanded) {
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

        if(isDetailsExpanded){
            Spacer(Modifier.height(16.dp))

            DetectedCacaoImageGrid(
                detectedCacaos = detectedDiseases.first().detectedCacaos
            )

            Spacer(Modifier.height(24.dp))

            PrimaryDescription(
                title = stringResource(R.string.sub_harga_satuan),
                description = "Rp 3.000 - Rp 6.000/kg."
            )

            Spacer(Modifier.height(24.dp))

            PrimaryDescription(
                title = stringResource(R.string.sub_total_harga),
                description = "Rp 3.000 - Rp 6.000/kg."
            )
        }

    }
}

@Preview
@Composable
private fun PriceAnalysisDetailsPreview() {
    KamekAppTheme {
        PriceAnalysisDetails()
    }
}