package ar.edu.unicen.seminarioentregable.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import ar.edu.unicen.seminarioentregable.ddl.data.TheMovieRepository
import ar.edu.unicen.seminarioentregable.ddl.data.paging.MoviePagingSource
import ar.edu.unicen.seminarioentregable.ddl.models.Movie
import ar.edu.unicen.seminarioentregable.ddl.models.MovieAPIResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
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

    private val _popularMovies = MutableStateFlow<MovieAPIResponse?>(null)
    val popularMovies = _popularMovies.asStateFlow()

    val pager: StateFlow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { MoviePagingSource(theMovieRepository) }
    ).flow.cachedIn(viewModelScope).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PagingData.empty()
    )

    fun getPopularMovies(page: Int){
        viewModelScope.launch {
            _loading.value = true
            _error.value = false
            _popularMovies.value = null

            delay(2000)
            val popularMovies = theMovieRepository.getPopularMovies(page)

            _loading.value = false
            _popularMovies.value = popularMovies
            _error.value = popularMovies == null

        }
    }
}