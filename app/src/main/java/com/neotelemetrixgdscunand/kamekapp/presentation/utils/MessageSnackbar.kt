package com.neotelemetrixgdscunand.kamekapp.presentation.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon55

@Composable
fun MessageSnackbar(
    modifier: Modifier = Modifier,
    message: String = ""
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .background(color = Color.White, RoundedCornerShape(50))
                .border(width = 1.dp, color = Maroon55, shape = RoundedCornerShape(50))
                .padding(vertical = 4.dp, horizontal = 8.dp)
        ){

            ImagePainterStable(
                modifier = Modifier
                    .height(17.dp),
                drawableResId = R.drawable.ic_logo2,
                contentScale = ContentScale.Fit,
            )

            Spacer(Modifier.width(2.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.labelMedium
            )

            Spacer(Modifier.width(4.dp))
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun MessageSnackbarPreview() {
    KamekAppTheme {
        MessageSnackbar(message = "Hello")
    }
}