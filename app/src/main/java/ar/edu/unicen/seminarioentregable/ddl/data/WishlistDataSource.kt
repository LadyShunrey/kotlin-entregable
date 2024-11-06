package ar.edu.unicen.seminarioentregable.ddl.data

import androidx.sqlite.db.SimpleSQLiteQuery
import ar.edu.unicen.seminarioentregable.ddl.models.Movie
import ar.edu.unicen.seminarioentregable.ddl.models.WishlistDao
import ar.edu.unicen.seminarioentregable.ddl.models.WishlistMovie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WishlistDataSource @Inject constructor(
    private val wishlistDao: WishlistDao
) {
    suspend fun addToWishlist(movie: Movie) {
        wishlistDao.insert(movie.toWishlistMovie())
    }

    fun removeFromWishlist(movieId: Int) {
        wishlistDao.delete(movieId)
    }

    fun getWishlistMovies(): Flow<List<WishlistMovie>> {
        return wishlistDao.getAll()
    }

}