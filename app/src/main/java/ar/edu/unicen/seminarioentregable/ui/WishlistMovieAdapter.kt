package ar.edu.unicen.seminarioentregable.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ar.edu.unicen.seminarioentregable.databinding.ListItemMovieBinding
import ar.edu.unicen.seminarioentregable.ddl.models.Movie
import ar.edu.unicen.seminarioentregable.ddl.models.WishlistMovie
import com.bumptech.glide.Glide

class WishlistMovieAdapter(
    private val onMovieClick: (WishlistMovie) -> Unit
): ListAdapter<WishlistMovie, WishlistMovieAdapter.MovieViewHolder>(MOVIE_COMPARATOR) {

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<WishlistMovie>() {
            override fun areItemsTheSame(oldItem: WishlistMovie, newItem: WishlistMovie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: WishlistMovie, newItem: WishlistMovie): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemMovieBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null){
            holder.bind(movie)
        }
    }

    inner class MovieViewHolder(
        private val binding: ListItemMovieBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(wishlistMovie: WishlistMovie){
            binding.movieTitle.text = wishlistMovie.title
            binding.movieOverview.text = wishlistMovie.overview

            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500" + wishlistMovie.poster_path)
                .into(binding.moviePoster)

            binding.root.setOnClickListener {
                onMovieClick(wishlistMovie)
            }

        }
    }
}