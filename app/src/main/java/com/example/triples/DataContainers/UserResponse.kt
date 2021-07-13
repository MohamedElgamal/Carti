package com.example.triples.DataContainers

import com.google.gson.annotations.SerializedName

data class UserResponse(
	@field:SerializedName("profile_photo_url")
	val profilePhotoUrl: String? = null,
	@field:SerializedName("updated_at")
	val updatedAt: String? = null,
	val name: String? = null,
	@field:SerializedName("created_at")
	val createdAt: String? = null,
	@field:SerializedName("email_verified_at")
	val emailVerifiedAt: Any? = null,
	@field:SerializedName("current_team_id")
	val currentTeamId: Any? = null,
	val id: Int? = null,
	@field:SerializedName("profile_photo_path")
	val profilePhotoPath: Any? = null,
	val email: String? = null
)

