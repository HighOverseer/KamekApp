package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon50
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon55
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon60
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Pink
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.util.AsyncImagePainterStable
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.util.ImagePainterStable

@Composable
fun HomeHeaderSection(
    modifier: Modifier = Modifier,
    navigateToNotification: () -> Unit = {}
) {

    var inflatedCardHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    val parentModifier = remember {
        modifier
            .background(
                brush = Brush.linearGradient(
                    Pair(1f, Maroon55),
                    Pair(1f, Maroon50)
                ),
                shape = RoundedCornerShape(
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                )
            )
            .onGloballyPositioned {
                inflatedCardHeight = with(density) {
                    it.size.height.toDp()
                }
            }
    }

    Box(
        modifier = parentModifier
    ) {

        val backgroundIconModifier = remember(inflatedCardHeight) {
            Modifier
                .size(inflatedCardHeight)
                .align(Alignment.TopEnd)
        }

        ImagePainterStable(
            modifier = backgroundIconModifier,
            drawableResId = R.drawable.header_bg,
            contentScale = ContentScale.FillBounds,
            contentDescription = null
        )


        val columnModifier = remember {
            Modifier.padding(horizontal = 16.dp, vertical = 25.dp)
        }
        Column(
            modifier = columnModifier
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                val circleImageModifier = remember {
                    Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                }

                Box(
                    modifier = circleImageModifier
                ) {
                    AsyncImagePainterStable(
                        modifier = Modifier
                            .align(Alignment.Center),
                        alignment = Alignment.Center,
                        imageDrawableResId = R.drawable.dummy_profile,
                        placeholderResId = R.drawable.ic_camera,
                        contentDescription = stringResource(R.string.profile_photo),
                        contentScale = ContentScale.Crop
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        imageVector = ImageVector
                            .vectorResource(R.drawable.ic_needle_location),
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(R.string.padang),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                    Image(
                        imageVector = ImageVector
                            .vectorResource(
                                R.drawable.ic_down_arrow
                            ),
                        contentDescription = null,
                    )
                }

                val iconBellModifier = remember {
                    Modifier
                        .border(
                            width = 1.dp,
                            color = Pink,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(9.dp)
                }
                Box(
                    modifier = iconBellModifier
                        .clickable(onClick = navigateToNotification)
                ) {
                    Image(
                        imageVector = ImageVector
                            .vectorResource(R.drawable.ic_bell),
                        contentDescription = stringResource(R.string.notification)
                    )
                }

            }

            val autoSpacerModifier = remember {
                val cardToHeadlineInfoMarginRatio = 0.0292f
                Modifier
                    .fillMaxHeight(cardToHeadlineInfoMarginRatio)
                    .defaultMinSize(minHeight = 16.dp)
            }
            Spacer(
                modifier = autoSpacerModifier
            )

            val cardModifier = remember {
                Modifier
                    .fillMaxWidth()
                    .background(
                        color = Maroon60,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(vertical = 18.dp, horizontal = 16.dp)
            }
            Column(
                modifier = cardModifier
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string._17),
                        style = MaterialTheme.typography.displayMedium,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(24.dp))
                    Column {
                        Text(
                            stringResource(R.string.hujan_lebat),
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            stringResource(R.string.h_24),
                            style = MaterialTheme.typography.titleMedium,
                            color = Pink
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            stringResource(R.string.l_17),
                            style = MaterialTheme.typography.titleMedium,
                            color = Pink
                        )
                    }

                    Spacer(Modifier.weight(1f))
                    ImagePainterStable(
                        drawableResId = R.drawable.ic_weather,
                        contentDescription = stringResource(R.string.gambar_cuaca)
                    )
                }

                Divider(
                    modifier =
                    Modifier.padding(vertical = 16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            stringResource(R.string.kelembapan),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.W300
                            ),
                            color = Pink
                        )
                        Spacer(Modifier.height(7.dp))
                        Text(
                            stringResource(R.string._87),
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    }
                    Column {
                        Text(
                            stringResource(R.string.kec_angin),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.W300
                            ),
                            color = Pink
                        )
                        Spacer(Modifier.height(7.dp))
                        Text(
                            stringResource(R.string._2_km_jam),
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    }
                    Column {
                        Text(
                            stringResource(R.string.curah_hujan),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.W300
                            ),
                            color = Pink
                        )
                        Spacer(Modifier.height(7.dp))
                        Text(
                            stringResource(R.string._100mm),
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    }
                }
            }

        }

    }

}

@Preview
@Composable
private fun HomeHeaderSectionPreview() {
    KamekAppTheme {
        HomeHeaderSection(
            modifier = Modifier.fillMaxWidth()
        )
    }

}