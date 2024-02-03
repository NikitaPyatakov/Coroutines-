package otus.homework.coroutines.server.dto

import com.google.gson.annotations.SerializedName

data class Photo(
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("url")
    val url: String,
    @field:SerializedName("width")
    val width: Int,
    @field:SerializedName("height")
    val height: Int
)