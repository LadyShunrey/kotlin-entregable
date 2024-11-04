package ar.edu.unicen.seminarioentregable.ddl.data

import ar.edu.unicen.seminarioentregable.ddl.models.Movie
import ar.edu.unicen.seminarioentregable.ddl.models.MovieAPIResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class TheMovieDBRemoteDataSource @Inject constructor(
    private val theMovieDBAPI: TheMovieDBAPI
) {
    suspend fun getMovie(
        title: String
    ): Response<MovieAPIResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = theMovieDBAPI.getMovie(title)
                if (response.isSuccessful) {
                    println("JSON Response: ${response.body().toString()}")
                } else {
                    println("API Error: ${response.code()} - ${response.message()}")
                }
                response // Retorna la respuesta de la API
            } catch (e: Exception) {
                println("Network Error: ${e.message}")
                // Puedes crear una Response con un error aquí si lo necesitas
                // Por ejemplo: Response.error<MovieAPIResponse>(500, ResponseBody.create(null, ""))
                throw e // Re-lanza la excepción para que sea manejada por la capa superior
            }
        }
    }

    suspend fun getPopularMovies(): List<Movie>? {
        return withContext(Dispatchers.IO) {
            try{
                val response = theMovieDBAPI.getPopularMovies()
                return@withContext response.body()?.results?.map{ it.toMovie() }
            }catch(e: Exception){
                e.printStackTrace()
                return@withContext null
            }
        }
    }
}