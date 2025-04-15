package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.diagnosishistory.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey60
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon55
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.util.dashedBorder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TakePhotoSection(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    val cardModifier = remember {
        modifier
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

    Card(
        onClick = onClick,
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

@Preview
@Composable
private fun TakePhotoSectionPreview() {
    KamekAppTheme {
        TakePhotoSection()
    }
}