package ar.edu.unicen.seminarioentregable.ddl.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishlist_movies")
data class WishlistMovie(
    @PrimaryKey
    val id: Int,
    val title: String,
    val overview: String?,
    val posterPath: String?
){
}