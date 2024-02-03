package otus.homework.coroutines.server

import otus.homework.coroutines.server.dto.Photo
import retrofit2.http.GET

interface CatsPhotosService {

    @GET("search")
    suspend fun getCatPhoto(): List<Photo>
}