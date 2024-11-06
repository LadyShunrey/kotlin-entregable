package ar.edu.unicen.seminarioentregable.ui

import androidx.activity.result.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.seminarioentregable.ddl.data.TheMovieRepository
import ar.edu.unicen.seminarioentregable.ddl.models.Movie
import ar.edu.unicen.seminarioentregable.ddl.models.WishlistMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val theMovieRepository: TheMovieRepository
) : ViewModel() {

    val wishlistMovies: StateFlow<List<WishlistMovie>> =
        theMovieRepository.getWishlistMovies()
            .stateIn(viewModelScope,
                SharingStarted.WhileSubscribed(5000), emptyList())

    fun addToWishlist(movie: Movie) {
        viewModelScope.launch {
            theMovieRepository.addToWishlist(movie)
        }
    }

    fun removeFromWishlist(movieId: Int) {
        viewModelScope.launch {
            theMovieRepository.removeFromWishlist(movieId)
        }
    }

}