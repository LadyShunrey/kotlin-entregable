package ar.edu.unicen.seminarioentregable.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ar.edu.unicen.seminarioentregable.databinding.ActivityMoviepdpBinding
import ar.edu.unicen.seminarioentregable.ddl.models.Movie
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MoviePDPActivity: AppCompatActivity()  {
    private lateinit var binding: ActivityMoviepdpBinding

    private val viewModel by viewModels<MoviePDPViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMoviepdpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.loading.onEach { loading ->
            if(loading){
                binding.progressBar.visibility = android.view.View.VISIBLE
                binding.movieTitle.visibility = android.view.View.INVISIBLE
                binding.movieOverview.visibility = android.view.View.INVISIBLE
                binding.moviePoster.visibility = android.view.View.INVISIBLE
                binding.movieRating.visibility = android.view.View.INVISIBLE
                binding.movieGenre.visibility = android.view.View.INVISIBLE
            }else{
                binding.progressBar.visibility = android.view.View.INVISIBLE
                binding.movieTitle.visibility = android.view.View.VISIBLE
                binding.movieOverview.visibility = android.view.View.VISIBLE
                binding.moviePoster.visibility = android.view.View.VISIBLE
                binding.movieRating.visibility = android.view.View.VISIBLE
                binding.movieGenre.visibility = android.view.View.VISIBLE
            }
        }.launchIn(lifecycleScope)

        viewModel.error.onEach { error ->
            if(error){
                binding.error.visibility = android.view.View.VISIBLE
            }else{
                binding.error.visibility = android.view.View.INVISIBLE
            }
        }.launchIn(lifecycleScope)

        val movie = intent.getParcelableExtra<Movie>("movie")
        if(movie != null){
            viewModel.setMovie(movie)
            binding.movieTitle.text = movie.title
            binding.movieOverview.text = movie.overview

            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500" + movie.poster_path)
                .into(binding.moviePoster)

            binding.movieRating.text = movie.vote_average.toString()

            viewModel.genres.onEach { genres ->
                val genreNames = genres?.joinToString(", ") { it.name ?: "" }?:""
                binding.movieGenre.text = genreNames
            }.launchIn(lifecycleScope)

        }else{
            Toast.makeText(this, "Error loading movie", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.addToWishlistButton.setOnClickListener {
            viewModel.addToWishlist(movie)
        }

    }
}