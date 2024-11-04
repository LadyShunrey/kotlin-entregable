package ar.edu.unicen.seminarioentregable.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import ar.edu.unicen.seminarioentregable.R
import ar.edu.unicen.seminarioentregable.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setContentView(R.layout.activity_main)

        viewModel.loading.onEach { loading ->
            if(loading){
                binding.progressBar.visibility = android.view.View.VISIBLE
                binding.movieInformation.visibility = android.view.View.INVISIBLE
            }else{
                binding.progressBar.visibility = android.view.View.INVISIBLE
                binding.movieInformation.visibility = android.view.View.VISIBLE
            }
        }.launchIn(lifecycleScope)

        viewModel.movie.onEach { movie ->
            binding.movieTitle.text = movie?.title.orEmpty()
            binding.movieLanguage.text = movie?.original_language.orEmpty()
            binding.movieOverview.text = movie?.overview.orEmpty()
        }.launchIn(lifecycleScope)

        viewModel.error.onEach { error ->
            if(error){
                binding.error.visibility = android.view.View.VISIBLE
            }else{
                binding.error.visibility = android.view.View.INVISIBLE
            }
        }.launchIn(lifecycleScope)

        binding.searchMovieEditText.addTextChangedListener { text ->
            val isValid = text.toString() != null
            binding.searchMovieButton.isEnabled = isValid
        }

        binding.searchMovieButton.setOnClickListener {
            val title = binding.searchMovieEditText.text.toString()
            if (title != null) {
                viewModel.getMovie(title)
            }
        }

        viewModel.posterUrl.observe(this) { posterUrl ->
            if (posterUrl != null) {
                binding.moviePoster.visibility = android.view.View.VISIBLE
                Glide.with(this)
                    .load(posterUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(binding.moviePoster)
            }else{
                binding.moviePoster.visibility = android.view.View.INVISIBLE
            }
        }

        binding.popularMoviesButton.setOnClickListener {
            val intent = Intent(this, MoviePLPActivity::class.java)

            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}