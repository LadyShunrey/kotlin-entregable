package ar.edu.unicen.seminarioentregable.ddl.models

import ar.edu.unicen.seminarioentregable.ddl.data.MovieDetailsDTO

class MovieDetailsAPIResponse(
    val id: Int,
    val title: String,
    val original_language: String,
    val overview: String,
    val poster_path: String?,
    val vote_average: Double?,
    val genres: List<Genre>
) {

}