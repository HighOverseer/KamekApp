package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
    detectedCacaos:List<DetectedCacao> = listOf()
) {
    val lazyVerticalGridState = rememberLazyGridState()
    LazyVerticalGrid(
        //contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp, start = 16.dp, end = 16.dp),
        state = lazyVerticalGridState,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        items(detectedCacaos.size, key = {item -> item.hashCode()}){ index ->
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                val currentCacao = detectedCacaos[index]
                AsyncImage(
                    model = currentCacao.imageUrl,
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

                    currentCacao.name,
                    style = MaterialTheme.typography.labelMedium,
                    color = Black10
                )
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