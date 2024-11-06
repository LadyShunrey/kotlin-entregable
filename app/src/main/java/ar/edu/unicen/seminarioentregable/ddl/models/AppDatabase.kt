package ar.edu.unicen.seminarioentregable.ddl.models

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WishlistMovie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wishlistDao(): WishlistDao
}