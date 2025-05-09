package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Black10
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Orange90
import com.neotelemetrixgdscunand.kamekapp.presentation.utils.ImagePainterStable

@Composable
fun ExplorationMenu(
    modifier: Modifier = Modifier,
    iconResId: Int,
    label: String,
    onClick: () -> Unit = {}
) {

    val imageModifier = remember {
        Modifier
            .clip(CircleShape)
            .background(
                color = Orange90,
                shape = CircleShape
            )
            .defaultMinSize(minWidth = 64.dp, minHeight = 64.dp)
            .clickable(onClick = onClick)

    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = imageModifier
        ) {
            ImagePainterStable(
                modifier = Modifier.align(Alignment.Center),
                drawableResId = iconResId,
                contentDescription = label
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = Black10,
        )
    }

}
