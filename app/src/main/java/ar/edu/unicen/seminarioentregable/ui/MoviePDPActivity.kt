package ar.edu.unicen.seminarioentregable.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ar.edu.unicen.seminarioentregable.databinding.ActivityMoviepdpBinding
import ar.edu.unicen.seminarioentregable.ddl.models.Movie
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
            }else{
                binding.progressBar.visibility = android.view.View.INVISIBLE
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
        }else{
            Toast.makeText(this, "Error loading movie", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}