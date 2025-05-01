package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Black10
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey69
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon55

@Composable
fun PriceInfoSection(modifier: Modifier = Modifier) {
    val cardModifier = remember {
        Modifier
            .background(color = Color.White, shape = RoundedCornerShape(4.dp))
            .padding(vertical = 8.dp, horizontal = 16.dp)
    }

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(4.dp),
        onClick = {}
    ) {
        Row(
            modifier = cardModifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier
                    .weight(1f)
            ) {
                Row {
                    Image(
                        imageVector = ImageVector
                            .vectorResource(R.drawable.ic_chart),
                        contentDescription = "",
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        stringResource(R.string.harga_kakao_hari_ini),
                        style = MaterialTheme.typography.labelMedium,
                        color = Black10
                    )
                }
                Spacer(Modifier.height(16.dp))

                Text(
                    "Rp 10.000",
                    style = MaterialTheme.typography.titleMedium,
                    color = Black10
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        imageVector = ImageVector
                            .vectorResource(R.drawable.ic_arrow_up_chart),
                        contentDescription = null,
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        "0.57%",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Normal
                        ),
                        color = Maroon55
                    )
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    "Rp9.500",
                    style = MaterialTheme.typography.bodySmall,
                    color = Grey69
                )
            }
        }
    }
}

@Preview
@Composable
private fun PriceInfoSectionPreview() {
    KamekAppTheme {
        PriceInfoSection(
            modifier = Modifier.fillMaxWidth()
        )
    }
}