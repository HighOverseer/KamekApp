package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.diagnosishistory

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Black10
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey50
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey60
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon55

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    isActive: Boolean = false,
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    hint: String = "",
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    backgroundColor: Color = Color.White
) {
    val modifierLocal = remember {
        modifier
            .fillMaxWidth()
            .border(
                color = if (isActive) Maroon55 else Color.Transparent,
                width = 1.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .background(shape = RoundedCornerShape(8.dp), color = backgroundColor)
            .padding(horizontal = 16.dp, vertical = 15.dp)
    }

    BasicTextField(
        modifier = modifierLocal,
        value = query,
        onValueChange = onQueryChange,
        interactionSource = interactionSource,
        decorationBox = @Composable { innerTextField: @Composable () -> Unit ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(16.dp),
                    imageVector = ImageVector
                        .vectorResource(R.drawable.ic_search),
                    contentDescription = null,
                    tint = if (isActive) Black10 else Grey60
                )

                Spacer(Modifier.width(8.dp))

                if (!isActive && query.isEmpty()) {
                    Text(
                        text = hint,
                        style = MaterialTheme.typography.labelMedium,
                        color = Grey50
                    )
                }

                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    innerTextField()
                }

            }
        },
        textStyle = MaterialTheme.typography.labelMedium,
        maxLines = 1,
        singleLine = true,
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchBarPreview() {
    KamekAppTheme {
        SearchBar()
    }

}