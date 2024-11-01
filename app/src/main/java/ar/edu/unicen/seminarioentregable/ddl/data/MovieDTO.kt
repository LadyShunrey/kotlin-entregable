package ar.edu.unicen.seminarioentregable.ddl.data

import ar.edu.unicen.seminarioentregable.ddl.models.Movie
import com.google.gson.annotations.SerializedName

class MovieDTO(
    @SerializedName("title")
    val title: String,
    @SerializedName("original_language")
    val original_language: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val poster_path: String?
) {

    fun toMovie(): Movie {
        return Movie(
            title = title,
            original_language = original_language,
            overview = overview,
            poster_path = poster_path
        )
    }
}