package com.neotelemetrixgdscunand.kamekapp.presentation.ui.news

import com.neotelemetrixgdscunand.kamekapp.R

enum class NewsCategory(
    val textResId: Int
) {
    ALL(R.string.all),
    DISEASE(R.string.penyakit),
    FARM(R.string.berita_pertanian),
    TECHNOLOGY(R.string.teknologi)
}