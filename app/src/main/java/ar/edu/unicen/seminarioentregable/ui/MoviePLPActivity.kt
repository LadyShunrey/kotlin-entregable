package ar.edu.unicen.seminarioentregable.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ar.edu.unicen.seminarioentregable.databinding.ActivityMovieplpBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MoviePLPActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMovieplpBinding

    private val viewModel by viewModels<MoviePLPViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieplpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subscribeToUi()
        subscribeToViewModel()
    }

    private fun subscribeToUi(){
        binding.showPopularMoviesButton.setOnClickListener {
            viewModel.getPopularMovies()
        }
    }

    private fun subscribeToViewModel(){
        viewModel.loading.onEach { loading ->
            if(loading){
                binding.progressBar.visibility = android.view.View.VISIBLE
            }else{
                binding.progressBar.visibility = android.view.View.INVISIBLE
            }
            binding.showPopularMoviesButton.isEnabled = !loading
        }.launchIn(lifecycleScope)

        viewModel.popularMovies.onEach { popularMovies ->
            binding.movieList.adapter = MovieAdapter(
                popularMovies = popularMovies ?: emptyList(),
                onMovieClick = { movie ->
                    val intent = Intent(this, MoviePDPActivity::class.java)
                    intent.putExtra("movie", movie)
                    startActivity(intent)
                }
            )
        }.launchIn(lifecycleScope)

        //val intent = Intent(this, MoviePLPActivity::class.java)
        //            startActivity(intent)
        viewModel.error.onEach { error ->
            if(error){
                binding.error.visibility = android.view.View.VISIBLE
            }else{
                binding.error.visibility = android.view.View.INVISIBLE
            }
        }.launchIn(lifecycleScope)
    }
}