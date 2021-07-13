package com.example.triples.DataContainers

data class CategoriesResponse(
	val categories: List<CategoriesItem?>? = null
)

data class CategoriesItem(
	val image: String? = null,
	val updatedAt: String? = null,
	val name: String? = null,
	val description: String? = null,
	val createdAt: String? = null,
	val id: Int? = null
)

