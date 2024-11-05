package ar.edu.unicen.seminarioentregable.ui

import android.content.Intent
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.view.OrientationEventListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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

        val linearLayoutManager = LinearLayoutManager(this)
        val gridLayoutManager = GridLayoutManager(this, 2)

        viewModel.loading.onEach { loading ->
            if(loading){
                binding.progressBar.visibility = android.view.View.VISIBLE
                binding.movieList.visibility = android.view.View.INVISIBLE
            }else{
                binding.progressBar.visibility = android.view.View.INVISIBLE
                binding.movieList.visibility = android.view.View.VISIBLE
            }
        }

        binding.movieList.layoutManager = linearLayoutManager

        val orientationEventListener = object : OrientationEventListener(this) {
            override fun onOrientationChanged(orientation: Int) {
                if (orientation == ORIENTATION_UNKNOWN) {
                    return
                }
                binding.movieList.layoutManager = if(isLandscape(orientation)){
                    gridLayoutManager
                }
                else{
                    linearLayoutManager
                }
            }
        }

        orientationEventListener.enable()
    }

    private fun isLandscape(orientation: Int): Boolean {
        return orientation in (90 - 45)..(90 + 45) ||
                orientation in (270 - 45)..(270 + 45)
    }

    private fun subscribeToUi(){
        viewModel.getPopularMovies()
    }

    private fun subscribeToViewModel(){
        viewModel.loading.onEach { loading ->
            if(loading){
                binding.progressBar.visibility = android.view.View.VISIBLE
            }else{
                binding.progressBar.visibility = android.view.View.INVISIBLE
            }
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

        viewModel.error.onEach { error ->
            if(error){
                binding.error.visibility = android.view.View.VISIBLE
            }else{
                binding.error.visibility = android.view.View.INVISIBLE
            }
        }.launchIn(lifecycleScope)
    }
}