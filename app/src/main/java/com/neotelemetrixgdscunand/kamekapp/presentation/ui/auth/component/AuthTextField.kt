package com.neotelemetrixgdscunand.kamekapp.presentation.ui.auth.component

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey50
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey70
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon55

@Composable
fun AuthTextField(
    title:String = "",
    modifier: Modifier = Modifier,
    isFocused:Boolean = false,
    value:String = "",
    onValueChange:(String) -> Unit = {},
    hintText:String = "",
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
        isFocused = isFocused,
        value = value,
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