package dev.pan.pexelsonline.presenter.homeScreen

import dev.pan.pexelsonline.data.network.models.Photo

data class HomeState(

    // work on screen and each parameter that you need to work with place here

    val photos: List<Photo>? = emptyList(),
    val search: List<Photo>? = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = true

)