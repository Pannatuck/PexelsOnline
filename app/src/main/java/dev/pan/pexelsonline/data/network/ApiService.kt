package dev.pan.pexelsonline.data.network

import dev.pan.pexelsonline.data.network.models.Photo
import dev.pan.pexelsonline.data.network.models.Photos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("curated")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<Photos>

    @GET("search")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("per_page") perPage: Int
    ) : Response<Photos>

}