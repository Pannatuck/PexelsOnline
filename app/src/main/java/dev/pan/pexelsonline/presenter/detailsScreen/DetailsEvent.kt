package dev.pan.pexelsonline.presenter.detailsScreen

sealed interface DetailsEvents {

    object NavigateBack : DetailsEvents
    object OpenPictureUrl : DetailsEvents
}