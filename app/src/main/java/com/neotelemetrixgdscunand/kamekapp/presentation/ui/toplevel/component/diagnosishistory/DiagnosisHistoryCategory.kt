package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.diagnosishistory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey68
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon55

@Composable
fun DiagnosisHistoryCategory(
    modifier: Modifier = Modifier,
    isSelected:Boolean,
    text:String
) {
    Box(
        modifier = modifier
            .background(
                color = if(isSelected) Maroon55 else Color.White,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ){
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = if(isSelected) Color.White else Grey68
        )
    }

}

@Preview
@Composable
private fun DiagnosisHistoryCategoryPreview() {
    KamekAppTheme {
        DiagnosisHistoryCategory(isSelected = true, text = stringResource(SearchHistoryCategory.ALL.textResId) )
    }
}