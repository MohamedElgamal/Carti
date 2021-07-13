package com.example.triples.DataContainers

import com.google.gson.annotations.SerializedName

data class ProductsResponse(

	@field:SerializedName("products")
	val products: List<ProductsItem?>? = null
)

data class ProductsItem(

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

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("pivot")
	val pivot: Pivot? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("merchant_id")
	val merchantId: Int? = null,

	@field:SerializedName("barcode")
	val barcode: String? = null

){
	fun productBarcodeCastFactory(product : Product) : ProductsItem{
		var productsItem : ProductsItem
		productsItem = ProductsItem(
			product.image,
			product.updatedAt,
			product.price,
			product.qty,
			product.name,
			product.description,
			product.weight,
			product.createdAt,
			null,
			product.id,
			product.merchantId,
			product.barcode
		)
		return productsItem
	}

	fun cartItemCastFactory(item : ItemsItem) : ProductsItem{
		var productsItem : ProductsItem
		productsItem = ProductsItem(
			item.image,
			item.updatedAt,
			item.price,
			item.qty,
			item.name,
			item.description,
			item.weight,
			null,
			null,
			item.id,
			item.merchantId,
			item.barcode
		)
		return productsItem
	}
}

data class Pivot(

	@field:SerializedName("category_id")
	val categoryId: Int? = null,

	@field:SerializedName("product_id")
	val productId: Int? = null
)
