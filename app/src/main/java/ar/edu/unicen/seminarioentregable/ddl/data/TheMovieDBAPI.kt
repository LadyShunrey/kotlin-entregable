package ar.edu.unicen.seminarioentregable.ddl.data

import ar.edu.unicen.seminarioentregable.ddl.models.MovieAPIResponse
import ar.edu.unicen.seminarioentregable.ddl.models.MovieDetailsAPIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBAPI {
    @GET("/3/search/movie")
    suspend fun getMovie(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = "b71b3f041ea5177c645079a3318d06d"
    ): Response<MovieAPIResponse>

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = "b71b3f041ea5177c645079a3318d06d"
    ): Response<MovieAPIResponse>

    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetailsById(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = "b71b3f041ea5177c645079a3318d06d"
    ): Response<MovieDetailsAPIResponse>

    @GET("/3/genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String = "b71b3f041ea5177c645079a3318d06d"
    ): Response<MovieAPIResponse>
}
