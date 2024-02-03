package otus.homework.coroutines.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
interface ServerModule {


    companion object {
        @Named("photo")
        @Provides
        fun getCatsPhotoService(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://api.thecatapi.com/v1/images/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Named("text")
        @Provides
        fun getCatsTextService(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://catfact.ninja/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}