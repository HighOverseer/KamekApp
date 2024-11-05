package com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme

@Composable
fun CustomShapeIcon() {
    Canvas(modifier = Modifier.size(100.dp)) {
        val path = Path().apply {
            // Draw the custom star-like shape
            moveTo(size.width * 0.4f, size.height * 0.2f)
            cubicTo(size.width * 0.1f, size.height * 0.1f, size.width * 0.1f, size.height * 0.5f, size.width * 0.4f, size.height * 0.6f)
            cubicTo(size.width * 0.6f, size.height * 0.8f, size.width * 0.9f, size.height * 0.7f, size.width * 0.8f, size.height * 0.4f)
            cubicTo(size.width * 0.9f, size.height * 0.1f, size.width * 0.7f, size.height * 0.1f, size.width * 0.4f, size.height * 0.2f)
            close()
        }

        // Define the gradient brush
        val gradient = Brush.linearGradient(
            colors = listOf(Color(0xFF8B1A1A), Color(0xFFFFA726)),
            start = androidx.compose.ui.geometry.Offset.Zero,
            end = androidx.compose.ui.geometry.Offset(size.width, size.height)
        )


        // Draw the path with the gradient brush
        drawPath(
            path = path,
            brush = gradient,
            style = Fill
        )
    }
}

@Preview
@Composable
private fun testPreview() {
    KamekAppTheme {
        CustomShapeIcon()
    }

}