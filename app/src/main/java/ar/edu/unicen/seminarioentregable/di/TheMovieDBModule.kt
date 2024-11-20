package ar.edu.unicen.seminarioentregable.di

import ar.edu.unicen.seminarioentregable.ddl.data.TheMovieDBAPI
import ar.edu.unicen.seminarioentregable.network.AuthorizationInterceptor
import ar.edu.unicen.seminarioentregable.network.LanguageInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.OkHttpClient.Builder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
class TheMovieDBModule {

    @Provides
    fun provideToken(): String {
        return "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiNzFiM2YwNGUxZWE1MTc3YzY0NTA3OWEzMzE4ZDA2ZCIsIm5iZiI6MTczMDQxMjY3NS4yNDc5MDg2LCJzdWIiOiI2NzFhNzI0NmE0YWM4YTQzMmM1YzBmNGUiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.bkXr1fDPnQam7w4nrlAF0I5jZaVI2L2FLkftNqlwgAE"
    }

    @Provides
    fun provideOkHttpClient(token: String): OkHttpClient {
        return Builder()
            .addInterceptor(AuthorizationInterceptor(token))
            .addInterceptor(LanguageInterceptor())
            .build()
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideTheMovieDBAPI(
        retrofit: Retrofit
    ): TheMovieDBAPI {
        return retrofit.create(TheMovieDBAPI::class.java)
    }
}