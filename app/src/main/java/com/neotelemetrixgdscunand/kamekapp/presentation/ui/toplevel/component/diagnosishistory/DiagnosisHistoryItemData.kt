package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.diagnosishistory

import kotlin.random.Random

data class DiagnosisHistoryItemData(
    val id:Int = Random.nextInt(0, 1_000_000),
    val title:String,
    val imageUrlOrPath:String,
    val date:String,
    val predictedPrice:Float,
    val outputId:Int
)

//fun getDummyDiagnosisHistoryItems():List<DiagnosisHistoryItemData>{
//    return List(6){
//        DiagnosisHistoryItemData(
//            id = it,
//            title = "Phytophthora palmivora Kakao di kebun si Tono",
//            imageUrlOrPath = R.drawable.diagnosis_item_dummyz,
//            date = "12-05-2024",
//            predictedPrice = "Rp. 17.000",
//            outputId = 0
//        )
//    }
//}