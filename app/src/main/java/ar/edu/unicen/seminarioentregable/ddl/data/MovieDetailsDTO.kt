package ar.edu.unicen.seminarioentregable.ddl.data

import ar.edu.unicen.seminarioentregable.ddl.models.Genre
import ar.edu.unicen.seminarioentregable.ddl.models.Movie
import com.google.gson.annotations.SerializedName

class MovieDetailsDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("original_language")
    val original_language: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val poster_path: String?,
    @SerializedName("vote_average")
    val vote_average: Double?,
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("homepage")
    val homepage: String? = null
) {
    fun toMovie(): Movie {
        return Movie(
            id = id,
            title = title,
            original_language = original_language,
            overview = overview,
            poster_path = poster_path,
            vote_average = vote_average,
            genres = genres,
            homepage = homepage
        )
    }
}