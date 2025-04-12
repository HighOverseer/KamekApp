package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.diagnosishistory.component

import com.neotelemetrixgdscunand.kamekapp.R

enum class SearchHistoryCategory(
    val textResId: Int
) {
    ALL(R.string.semua),
    TODAY(R.string.hari_ini),
    WEEK(R.string.minggu_ini),
    MONTH(R.string.bulan_ini);
}