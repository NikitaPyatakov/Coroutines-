package otus.homework.coroutines.data.server.dto

import com.google.gson.annotations.SerializedName

data class FactDto(
	@field:SerializedName("fact")
	val fact: String,
	@field:SerializedName("length")
	val length: Int
)