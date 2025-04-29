package com.neotelemetrixgdscunand.kakaoxpert.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RegisterDto(

	@field:SerializedName("no_hp")
	val noHp: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null
)
