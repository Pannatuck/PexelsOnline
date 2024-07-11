package dev.pan.pexelsonline.data.network

import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getPhotos(page: Int, perPage: Int) = apiService.getPhotos(page, perPage)

    suspend fun searchPhotos(query: String, perPage: Int) = apiService.searchPhotos(query, perPage)

}