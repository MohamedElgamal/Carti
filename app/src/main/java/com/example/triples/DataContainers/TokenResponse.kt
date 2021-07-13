package com.example.triples.DataContainers

import com.google.gson.annotations.SerializedName

data class TokenResponse(

	@field:SerializedName("token")
	val token: String? = null
)
