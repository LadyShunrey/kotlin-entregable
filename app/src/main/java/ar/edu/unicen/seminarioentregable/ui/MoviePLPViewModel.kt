package ar.edu.unicen.seminarioentregable.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.seminarioentregable.ddl.data.TheMovieRepository
import ar.edu.unicen.seminarioentregable.ddl.models.Movie
import ar.edu.unicen.seminarioentregable.ddl.models.MovieAPIResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviePLPViewModel @Inject constructor(
    private val theMovieRepository: TheMovieRepository
): ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow(false)
    val error = _error.asStateFlow()

    private val _popularMovies = MutableStateFlow<List<Movie>?>(null)
    val popularMovies = _popularMovies.asStateFlow()

    fun getPopularMovies(){
        viewModelScope.launch {
            _loading.value = true
            _error.value = false
            _popularMovies.value = null

            val popularMovies = theMovieRepository.getPopularMovies()

            _loading.value = false
            _popularMovies.value = popularMovies
            _error.value = popularMovies == null

        }
    }
}