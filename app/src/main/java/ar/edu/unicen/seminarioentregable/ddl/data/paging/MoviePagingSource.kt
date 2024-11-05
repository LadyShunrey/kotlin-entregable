package ar.edu.unicen.seminarioentregable.ddl.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ar.edu.unicen.seminarioentregable.ddl.data.TheMovieRepository
import ar.edu.unicen.seminarioentregable.ddl.models.Movie

class MoviePagingSource(
    private val theMovieRepository: TheMovieRepository
): PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = theMovieRepository.getPopularMovies(page)

            LoadResult.Page(
                data = response.results.map { it.toMovie() },
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.results.isEmpty()) null else page + 1
            )
        }catch(e: Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int?{
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}