package com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult

import com.neotelemetrixgdscunand.kamekapp.domain.model.CacaoDisease
import kotlin.random.Random

data class DiagnosisOutput(
    val id: Int = Random.nextInt(0,1_000_000),
    val disease:CacaoDisease,
    val damageLevel:Float,
    val sellPrice:Float
)