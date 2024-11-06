package ar.edu.unicen.seminarioentregable.ddl.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow

@Dao
interface WishlistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: WishlistMovie)

    @Query("DELETE FROM wishlist_movies WHERE id = :movieId")
    fun delete(movieId: Int)

    @Query("SELECT * FROM wishlist_movies")
    fun getAll(): Flow<List<WishlistMovie>>
}