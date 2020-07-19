package com.car_plate.data.entity.car

import com.google.gson.annotations.SerializedName

data class ApiCarOperations(

	@field:SerializedName("regAt")
	val regAt: String? = null,

	@field:SerializedName("notes")
	val notes: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("isLast")
	val isLast: Boolean? = null,

	@field:SerializedName("vendor")
	val vendor: String? = null,

	@field:SerializedName("model")
	val model: String? = null,

	@field:SerializedName("modelYear")
	val modelYear: Int? = null,

	@field:SerializedName("operation")
	val operation: String? = null
)

data class ApiCarRegion(

	@field:SerializedName("name_ua")
	val nameUa: String? = null,

	@field:SerializedName("old_code")
	val oldCode: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("new_code")
	val newCode: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null
)
