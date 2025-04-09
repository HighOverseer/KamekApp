package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.domain.model.DiagnosisSessionPreview
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Black10
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey65
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme


@Composable
fun HomeDiagnosisHistory(
    modifier: Modifier = Modifier,
    item: DiagnosisSessionPreview
) {

    val cardModifier = remember {
        modifier
            .width(173.dp)
            .background(color = Color.White, RoundedCornerShape(8.dp))
            .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 16.dp)
    }
    val imageModifier = remember {
        Modifier
            .height(100.dp)
            .clip(RoundedCornerShape(8.dp))
    }

    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.White
        ),
        modifier = cardModifier

    ) {
        Box(
            modifier = imageModifier
                .align(Alignment.CenterHorizontally)
        ) {
            AsyncImage(
                modifier = Modifier
                    .align(Alignment.Center),
                model = item.imageUrlOrPath,
                contentScale = ContentScale.Crop,
                contentDescription = item.title,
                placeholder = painterResource(R.drawable.ic_camera)
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            item.title,
            style = MaterialTheme.typography.titleMedium,
            color = Black10,
            textAlign = TextAlign.Justify,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )

        Spacer(
            Modifier.height(8.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = ImageVector
                    .vectorResource(
                        R.drawable.ic_calendar
                    ),
                contentDescription = stringResource(R.string.kalender)
            )

            Text(
                item.date,
                style = MaterialTheme.typography.labelMedium,
                color = Grey65,
                textAlign = TextAlign.Start
            )
        }

    }
}

@Preview
@Composable
private fun DiagnosisHistoryPreview() {

    KamekAppTheme {
        HomeDiagnosisHistory(
            item =
            DiagnosisSessionPreview(
                id = 0,
                title = "Kakao Pak Tono",
                imageUrlOrPath = "https://drive.google.com/file/d/1SXCPCoMzRjZEpemeT-mLOUTD2mzbGee_/view?usp=drive_link",
                date = "12/11/2024",
                predictedPrice = 700f
            )
        )
    }

}