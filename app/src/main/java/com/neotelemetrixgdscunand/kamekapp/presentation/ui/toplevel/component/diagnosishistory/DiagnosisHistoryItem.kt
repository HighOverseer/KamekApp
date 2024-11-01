package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.diagnosishistory

import com.neotelemetrixgdscunand.kamekapp.R

data class DiagnosisHistoryItem(
    val id:Int,
    val title:String,
    val pictureResId:Int,
    val date:String,
    val predictedPrice:String
)

fun getDummyDiagnosisHistoryItems():List<DiagnosisHistoryItem>{
    return List(6){
        DiagnosisHistoryItem(
            id = it,
            title = "Phytophthora palmivora Kakao di kebun si Tono",
            pictureResId = R.drawable.diagnosis_item_dummy,
            date = "12-05-2024",
            predictedPrice = "Rp. 17.000"
        )
    }
}