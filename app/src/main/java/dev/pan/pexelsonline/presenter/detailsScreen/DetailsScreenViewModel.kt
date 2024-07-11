package dev.pan.pexelsonline.presenter.detailsScreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.pan.pexelsonline.data.network.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) : ViewModel() {

    private val _detailsState = MutableStateFlow(DetailsScreenState())
    val detailsState = _detailsState.asStateFlow()

    fun onEvent(event: DetailsEvents){
        when(event){
            DetailsEvents.NavigateBack -> TODO()
            DetailsEvents.OpenPictureUrl -> TODO()
        }
    }


}