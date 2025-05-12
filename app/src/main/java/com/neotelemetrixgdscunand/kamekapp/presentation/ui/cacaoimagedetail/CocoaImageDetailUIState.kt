package com.neotelemetrixgdscunand.kamekapp.presentation.ui.cacaoimagedetail

import com.neotelemetrixgdscunand.kamekapp.domain.model.BoundingBox
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class CocoaImageDetailUIState(
    val imagePath: String = "",
    val boundingBox: ImmutableList<BoundingBox> = persistentListOf()
)