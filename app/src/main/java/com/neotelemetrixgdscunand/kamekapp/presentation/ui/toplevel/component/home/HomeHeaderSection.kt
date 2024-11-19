package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import coil.compose.AsyncImage
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon50
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon55
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon60
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Pink
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Pink80

@Composable
fun HomeHeaderSection(modifier: Modifier = Modifier) {

    val parentModifier = remember {
        modifier
            .fillMaxWidth()
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

    }

    val circleImageModifier = remember {
        Modifier
            .size(60.dp)
            .clip(CircleShape)
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

    val cardToHeadlineInfoMarginRatio = 0.0292f
    val autoSpacerModifier = remember {
        Modifier
            .fillMaxHeight(cardToHeadlineInfoMarginRatio)
            .defaultMinSize(minHeight = 16.dp)
    }

    val cardModifier = remember {
        Modifier
            .fillMaxWidth()
            .background(
                color = Maroon60,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(vertical = 18.dp, horizontal = 16.dp)
    }

    val columnModifier = remember {
        Modifier.padding(horizontal = 16.dp, vertical = 25.dp)
    }

    BoxWithConstraints(
        modifier = parentModifier
    )
    {
        val backgroundIconSize = remember {
            if(this.maxWidth < this.maxHeight){
                0.6125f * this.maxWidth
            }else 0.6125f * this.maxHeight

        }
        val backgroundIconModifier = remember {
            Modifier
                .width(backgroundIconSize)
                .height(backgroundIconSize)
                .align(Alignment.TopEnd)
        }

        Image(
            modifier = backgroundIconModifier,
            painter = painterResource(R.drawable.header_bg),
            contentScale = ContentScale.FillBounds,
            contentDescription = null
        )
        Column(modifier = columnModifier) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Box(
                    modifier = circleImageModifier
                ){
                    AsyncImage(
                        modifier = Modifier
                            .align(Alignment.Center),
                        alignment = Alignment.Center,
                        model = R.drawable.dummy_profile,
                        placeholder = painterResource(R.drawable.ic_camera),
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

                Box(
                    modifier = iconBellModifier
                ){
                    Image(
                        imageVector = ImageVector
                            .vectorResource(R.drawable.ic_bell),
                        contentDescription = stringResource(R.string.notification)
                    )
                }

            }


            Spacer(
                modifier =  autoSpacerModifier
            )
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
                    Image(
                        painter = painterResource(R.drawable.ic_weather),
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

            Spacer(Modifier.height(18.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Harga Buah Kakao Terkini",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )


                Spacer(Modifier.weight(1f))

                Text(
                    "Rp 2000",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )
                Spacer(Modifier.width(8.dp))
                Image(
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp),
                    painter = painterResource(R.drawable.ic_up_chart),
                    contentDescription = null
                )

            }

        }

    }

}

@Preview
@Composable
private fun HomeHeaderSectionPreview() {
    KamekAppTheme {
        HomeHeaderSection()
    }

}