package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.domain.model.DetectedCacao
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Black10
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme

@Composable
fun DetectedCacaoImageGrid(
    modifier: Modifier = Modifier,
    imagePath: String = "",
    detectedCacaos: List<DetectedCacao> = listOf(),
    onItemClicked: (Int) -> Unit = { }
) {

    val chunkedDetectedCacao = remember(detectedCacaos) {
        detectedCacaos.chunked(2)
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
                        .fillMaxWidth()
                        .then(if(rowItems.size == 2) Modifier.weight(1f) else Modifier)
                        .clickable {
                            onItemClicked(currentCacao.id)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = imagePath,
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(R.drawable.ic_camera),
                        alignment = Alignment.Center,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(56.dp)
                    )

                    Spacer(Modifier.width(16.dp))

                    Text(
                        stringResource(R.string.kakao, currentCacao.cacaoNumber),
                        style = MaterialTheme.typography.labelMedium,
                        color = Black10
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun DetectedCacaoImageGridPreview() {
    KamekAppTheme {
        DetectedCacaoImageGrid()
    }
}