package ar.edu.unicen.seminarioentregable.ddl.data

import ar.edu.unicen.seminarioentregable.ddl.models.Movie
import ar.edu.unicen.seminarioentregable.ddl.models.MovieAPIResponse
import retrofit2.Response
import javax.inject.Inject

class TheMovieRepository @Inject constructor(
    private val theMovieDBRemoteDataSource: TheMovieDBRemoteDataSource
){
    suspend fun getMovie(
        title: String
    ): Response<MovieAPIResponse> {
//       return theMovieDBRemoteDataSource.getMovie(title)
        val response = theMovieDBRemoteDataSource.getMovie(title)
        println("Response from API: $response") // Agregar println() aquí
        return response
    }

    suspend fun getPopularMovies(): List<Movie>? {
        return theMovieDBRemoteDataSource.getPopularMovies()
    }
}