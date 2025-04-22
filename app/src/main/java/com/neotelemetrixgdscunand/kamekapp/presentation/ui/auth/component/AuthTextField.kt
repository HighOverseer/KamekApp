package com.neotelemetrixgdscunand.kamekapp.presentation.ui.auth.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme

@Composable
fun AuthTextField(
    title: String = "",
    modifier: Modifier = Modifier,
    isFocusedProvider: () -> Boolean = { false },
    valueProvider: () -> String = { "" },
    onValueChange: (String) -> Unit = {},
    hintText: String = "",
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {

    Text(
        modifier = modifier,
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = Color.Black
    )

    Spacer(Modifier.height(8.dp))

    PrimaryTextField(
        isFocusedProvider = isFocusedProvider,
        valueProvider = valueProvider,
        onValueChange = onValueChange,
        hintText = hintText,
        interactionSource = interactionSource,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation
    )

}

@Preview(showBackground = true)
@Composable
private fun AuthTextFieldPreview() {
    KamekAppTheme {
        AuthTextField()
    }

}