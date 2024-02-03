package otus.homework.coroutines.server

import otus.homework.coroutines.server.dto.Fact
import retrofit2.http.GET

interface CatsTextService {

    @GET("fact")
    suspend fun getCatFact() : Fact
}