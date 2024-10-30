package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.home

import com.neotelemetrixgdscunand.kamekapp.R

data class WeeklyNewsItem(
    val id:Int,
    val title:String,
    val pictureResId:Int,
    val date:String
)

fun getDummyWeeklyNewsItems():List<WeeklyNewsItem> {
    return List(5){
        WeeklyNewsItem(
            id = it,
            title = "Pengertian, Gejala, dan Jenis-Jenis Penyakit pada Pertumbuhan Tanaman",
            pictureResId = R.drawable.news_dummy,
            date = "21 September 2024"
        )
    }

}
