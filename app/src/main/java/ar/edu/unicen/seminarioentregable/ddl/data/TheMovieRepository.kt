package ar.edu.unicen.seminarioentregable.ddl.data

import ar.edu.unicen.seminarioentregable.ddl.models.Movie
import ar.edu.unicen.seminarioentregable.ddl.models.MovieAPIResponse
import ar.edu.unicen.seminarioentregable.ddl.models.MovieDetailsAPIResponse
import ar.edu.unicen.seminarioentregable.ddl.models.WishlistMovie
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class TheMovieRepository @Inject constructor(
    private val theMovieDBRemoteDataSource: TheMovieDBRemoteDataSource,
    private val wishlistDataSource: WishlistDataSource
){
    suspend fun getMovie(
        title: String
    ): Response<MovieAPIResponse> {
//       return theMovieDBRemoteDataSource.getMovie(title)
        val response = theMovieDBRemoteDataSource.getMovie(title)
        println("Response from API: $response") // Agregar println() aquí
        return response
    }

    suspend fun getPopularMovies(page: Int): MovieAPIResponse {
        return theMovieDBRemoteDataSource.getPopularMovies(page)
    }

    suspend fun getTrendingMovies(): MovieAPIResponse {
        return theMovieDBRemoteDataSource.getTrendingMovies()
    }

    suspend fun getMovieDetailsById(
        movieId: Int
    ): Response<MovieDetailsAPIResponse>? {
        val response =theMovieDBRemoteDataSource.getMovieDetailsById(movieId)
        return response
    }

    suspend fun addToWishlist(movie: Movie) {
        wishlistDataSource.addToWishlist(movie)
    }

    suspend fun removeFromWishlist(movieId: Int) {
        wishlistDataSource.removeFromWishlist(movieId)
    }

    fun getWishlistMovies(): Flow<List<WishlistMovie>> {
        return wishlistDataSource.getWishlistMovies()
    }
}