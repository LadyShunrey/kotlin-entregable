package ar.edu.unicen.seminarioentregable.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.edu.unicen.seminarioentregable.databinding.ListItemMovieBinding
import ar.edu.unicen.seminarioentregable.ddl.models.Movie

class MovieAdapter(
    private val popularMovies: List<Movie>,
    private val onMovieClick: (Movie) -> Unit
): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemMovieBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = popularMovies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return popularMovies.size
    }

    inner class MovieViewHolder(
        private val binding: ListItemMovieBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(movie: Movie){
            binding.movieTitle.text = movie.title
            binding.movieOverview.text = movie.overview

            binding.root.setOnClickListener {
                onMovieClick(movie)
            }

        }
    }
}