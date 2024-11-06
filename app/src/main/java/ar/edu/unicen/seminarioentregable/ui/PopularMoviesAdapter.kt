package ar.edu.unicen.seminarioentregable.ui

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ar.edu.unicen.seminarioentregable.databinding.ListItemMovieBinding
import ar.edu.unicen.seminarioentregable.ddl.models.Movie
import com.bumptech.glide.Glide

class PopularMoviesAdapter(
    private val onMovieClick: (Movie) -> Unit
): ListAdapter<Movie, PopularMoviesAdapter.MovieViewHolder>(MOVIE_COMPARATOR) {

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
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

        fun bind(movie: Movie){
            binding.movieTitle.text = movie.title
            binding.movieOverview.text = movie.overview

            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500" + movie.poster_path)
                .into(binding.moviePoster)

            binding.root.setOnClickListener {
                onMovieClick(movie)
            }

        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}