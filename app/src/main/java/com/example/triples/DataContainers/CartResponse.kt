package com.example.triples.DataContainers

import com.google.gson.annotations.SerializedName

data class CartResponse(

	@field:SerializedName("total")
	val total: Double? = null,

	@field:SerializedName("items")
	val items: List<ItemsItem?>? = null
)

data class ItemsItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("price")
	val price: Double? = null,

	@field:SerializedName("qty")
	val qty: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("weight")
	val weight: Double? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("merchant_id")
	val merchantId: Int? = null,

	@field:SerializedName("barcode")
	val barcode: String? = null
)
