package com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.diseasediagnosis.compoenent

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.domain.model.DetectedCacao
import com.neotelemetrixgdscunand.kamekapp.domain.model.getDetectedDiseaseCacaos
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Black10
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon45
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun DetectedCacaoImageGrid(
    modifier: Modifier = Modifier,
    detectedCacaos: ImmutableList<DetectedCacao> = persistentListOf(),
    onItemClicked: (Int) -> Unit = { }
) {

    val chunkedDetectedCacao = remember(detectedCacaos) {
        detectedCacaos.chunked(3)
    }

    chunkedDetectedCacao.forEach { rowItems ->
        Row(
            modifier = modifier
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            rowItems.forEach { currentCacao ->
                Row(
                    Modifier
                        .weight(1f)
                        .clickable {
                            onItemClicked(currentCacao.id)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(
                        "- ",
                        style = MaterialTheme.typography.labelMedium,
                        color = Black10
                    )

                    Text(
                        stringResource(R.string.kakao, currentCacao.cacaoNumber),
                        style = MaterialTheme.typography.labelMedium,
                        textDecoration = TextDecoration.Underline,
                        color = Black10
                    )

                    Spacer(Modifier.width(4.dp))

                    Image(
                        modifier = Modifier
                            .height(8.dp)
                            .align(Alignment.Top),
                        painter = painterResource(R.drawable.ic_eye),
                        contentScale = ContentScale.Fit,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Maroon45)
                    )
                }
            }

            val remainingItemCount = 3 - rowItems.size
            repeat(remainingItemCount) {
                Spacer(modifier.weight(1f))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetectedCacaoImageGridPreview() {
    KamekAppTheme {
        Column(Modifier.wrapContentSize()) {
            DetectedCacaoImageGrid(
                detectedCacaos = getDetectedDiseaseCacaos().toImmutableList()
            )
        }
    }
}