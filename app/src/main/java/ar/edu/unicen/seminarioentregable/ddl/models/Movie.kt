package ar.edu.unicen.seminarioentregable.ddl.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie (
    val id: Int?,
    val title: String?,
    val original_language: String?,
    val overview: String?,
    val poster_path: String?,
    val vote_average: Double?,
    val genres: List<Genre>? = null
): Parcelable {
    fun toWishlistMovie(): WishlistMovie {
        return WishlistMovie(
            id = id ?: 0,
            title = title ?: "",
            overview = overview,
            posterPath = poster_path
        )
    }
}