package com.neotelemetrixgdscunand.kamekapp.presentation.ui.auth.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey70
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon55

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier, text:String = "",
    onClick:() -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(vertical = 14.dp)
) {
    ElevatedButton(
        modifier = modifier,
        contentPadding = contentPadding,
        shape = RoundedCornerShape(8.dp),
        onClick = onClick,
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Maroon55,
            disabledContentColor = Grey70,
        ),
    ){
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )
    }
}

@Preview
@Composable
private fun PrimaryButtonPreview() {
    KamekAppTheme {
        PrimaryButton()
    }
}