package com.neotelemetrixgdscunand.kamekapp.domain.model

data class DetectedCacao(
    val name:String,
    val imageUrl:String,
    val boundingBox: BoundingBox,
)