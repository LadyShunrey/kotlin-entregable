package ar.edu.unicen.seminarioentregable.ddl.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie (
    val title: String?,
    val original_language: String?,
    val overview: String?,
    val poster_path: String?
): Parcelable {
}