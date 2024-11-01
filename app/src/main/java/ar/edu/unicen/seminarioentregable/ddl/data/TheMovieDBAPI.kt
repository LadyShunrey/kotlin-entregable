package ar.edu.unicen.seminarioentregable.ddl.data

import ar.edu.unicen.seminarioentregable.ddl.models.MovieAPIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDBAPI {
    @GET("/3/search/movie")
    suspend fun getMovie(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = "b71b3f041ea5177c645079a3318d06d"
    ): Response<MovieAPIResponse>
}



//        @Query("include_adult") includeAdult: Boolean = false,
//        @Query("language") language: String = "en-US",
//        @Query("page") page: Int = 1