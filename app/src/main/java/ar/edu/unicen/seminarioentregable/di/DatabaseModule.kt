package ar.edu.unicen.seminarioentregable.di

import android.app.Application
import androidx.room.Room
import ar.edu.unicen.seminarioentregable.ddl.models.AppDatabase
import ar.edu.unicen.seminarioentregable.ddl.models.WishlistDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideWishlistDao(appDatabase: AppDatabase): WishlistDao {
        return appDatabase.wishlistDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(applicacion: Application): AppDatabase {
        return Room.databaseBuilder(applicacion, AppDatabase::class.java, "app_database")
            .build()
    }
}