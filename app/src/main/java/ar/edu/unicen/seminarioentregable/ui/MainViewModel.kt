package ar.edu.unicen.seminarioentregable.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.seminarioentregable.ddl.data.MovieDTO
import ar.edu.unicen.seminarioentregable.ddl.data.TheMovieRepository
import ar.edu.unicen.seminarioentregable.ddl.models.Movie
import ar.edu.unicen.seminarioentregable.ddl.models.MovieAPIResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val theMovieRepository: TheMovieRepository
): ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow(false)
    val error = _error.asStateFlow()

    private val _movie = MutableStateFlow<Movie?>(null)
    val movie = _movie.asStateFlow()

    private val _posterUrl = MutableLiveData<String?>(null)
    val posterUrl: LiveData<String?> = _posterUrl

    fun getMovie(
        title: String
    ){
        viewModelScope.launch {
            _loading.value = true
            _error.value = false
            _movie.value = null

            try{
                val response: Response<MovieAPIResponse> = theMovieRepository.getMovie(title)

                if(response.isSuccessful && response.body() != null){
                    val movieApiResponse = response.body()!!
                    val moviesDTO = movieApiResponse.results
                    val movies = moviesDTO.map { it.toMovie() }
                    _movie.value = movies.firstOrNull()

                    val posterPath = _movie.value?.poster_path
                    if(posterPath != null){
                        _posterUrl.value = "https://image.tmdb.org/t/p/w500$posterPath"
                    }else{
                        null
                    }

                }else{
                    _error.value = true
                }
            }catch (e: Exception){
                _error.value = true
            }finally{
                delay(2000)
                _loading.value = false
            }
        }
    }
}