package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.home

import com.neotelemetrixgdscunand.kamekapp.R

data class DiagnosisHistoryItem(
    val id:Int,
    val pictureResId:Int,
    val title:String,
    val date:String
)

fun getDummyDiagnosisHistoryItems():List<DiagnosisHistoryItem>{
    return List(5){
        DiagnosisHistoryItem(
            id = it,
            R.drawable.diagnosis_item_dummy,
            title = "Phytophthora palmivora Kakao",
            date = "12-05-2024"
        )
    }
}