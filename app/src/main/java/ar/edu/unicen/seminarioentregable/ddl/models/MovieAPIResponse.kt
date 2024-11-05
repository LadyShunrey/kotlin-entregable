package ar.edu.unicen.seminarioentregable.ddl.models

import ar.edu.unicen.seminarioentregable.ddl.data.MovieDTO

class MovieAPIResponse (
    val page: Int,
    val results: List<MovieDTO>,
    val total_pages: Int,
    val total_results: Int
){

}