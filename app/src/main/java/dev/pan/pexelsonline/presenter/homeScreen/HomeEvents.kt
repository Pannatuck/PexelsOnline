package dev.pan.pexelsonline.presenter.homeScreen

sealed interface HomeEvents {


    // all events that can be done on screen
    // TODO: change to current project

    data class GetPhotos(val page: Int, val perPage: Int) : HomeEvents
    data class PhotoClicked(val photoId: Int) : HomeEvents

    // all events user can do on screen (button press, toggles, etc)
//    object SaveMovie : HomeEvents
//    data class DeleteMovie(val movie: MovieEntity) : HomeEvents
//    data class GetMovieDetails(val movieId: Int) : HomeEvents
//    object GetMovies : HomeEvents

    // also can be used for something like
    // object ShowDialog: MovieEvent
    // object HideDialog: MovieEvent

    /* or like this
     data class SortMovies(val sortType: SortType) : MovieEvent

     and have enum class that will be used to choose from
     enum class SortType { POPULARITY, RATING, VOTE_COUNT }
    */
}