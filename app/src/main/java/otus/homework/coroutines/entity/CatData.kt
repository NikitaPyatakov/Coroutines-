package otus.homework.coroutines.entity

import otus.homework.coroutines.data.server.dto.FactDto
import otus.homework.coroutines.data.server.dto.PhotoDto

data class CatData(
    val factData: FactDto? = null,
    val photoData: PhotoDto? = null
)
