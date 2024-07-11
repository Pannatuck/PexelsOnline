package dev.pan.pexelsonline.presenter.homeScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.Navigator
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.pan.pexelsonline.Screen
import dev.pan.pexelsonline.data.network.ApiRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) : ViewModel(){

    private val _navigationEvent = MutableSharedFlow<String>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    fun onEvent(event: HomeEvents) {
        when(event) {
            is HomeEvents.GetPhotos -> getPhotos(event.page, event.perPage)
            is HomeEvents.PhotoClicked -> onPhotoClicked(event.photoId)
        }
    }

    private fun getPhotos(page: Int, perPage: Int) {
        viewModelScope.launch {
            try {
                val response = apiRepository.getPhotos(page, perPage)
                if (response.isSuccessful) {
                    val photosResponse = response.body()
                    Log.d("HomeScreenViewModel", "Photos received: ${photosResponse?.photos?.size}")
                    _homeState.update { currentState ->
                        currentState.copy(
                            photos = photosResponse?.photos ?: emptyList(),
                            isLoading = false
                        )
                    }
                } else {
                    Log.e("HomeScreenViewModel", "API call failed: ${response.code()}")
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception){
                Log.e("HomeScreenViewModel", "Error getting photos: ${e.message}")
            }
        }
    }

    private fun onPhotoClicked(photoId: Int) {
        val photo = _homeState.value.photos?.find { photo ->
            photo.id == photoId
        }
        photo?.let {
            val route = Screen.Details.createRoute(
                landscape = photo.src.landscape,
                photographer = photo.photographer,
                photographerUrl = photo.photographer_url,
                alt = photo.alt
            )
            viewModelScope.launch {
                _navigationEvent.emit(route)
            }
        }
    }
}