package ar.edu.unicen.seminarioentregable.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.seminarioentregable.ddl.data.TheMovieRepository
import ar.edu.unicen.seminarioentregable.ddl.models.Genre
import ar.edu.unicen.seminarioentregable.ddl.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviePDPViewModel @Inject constructor(
    private val theMovieRepository: TheMovieRepository
): ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow(false)
    val error = _error.asStateFlow()

    private val _movie = MutableStateFlow<Movie?>(null)
    val movie = _movie.asStateFlow()

    private val _genres = MutableStateFlow<List<Genre>?>(null)
    val genres = _genres.asStateFlow()

    fun setMovie(movie: Movie){
        viewModelScope.launch {
            _loading.value = true
            _error.value = false
            _movie.value = null

            delay(2000)

            _loading.value = false
            _movie.value = movie

            getMovieGenres(movie.id!!)

            _error.value = movie == null
        }

    }

    private fun getMovieGenres(
        movieId: Int
    ) {
        viewModelScope.launch {
            val movieDetails = theMovieRepository.getMovieDetailsById(movieId)
            if (movieDetails != null) {
                _genres.value = movieDetails.body()?.genres
            }

        }
    }

    fun addToWishlist(movie: Movie?){
        viewModelScope.launch {
            if(movie != null){
                theMovieRepository.addToWishlist(movie)
            }
        }
    }
}