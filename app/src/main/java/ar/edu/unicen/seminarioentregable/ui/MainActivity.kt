package ar.edu.unicen.seminarioentregable.ui

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ar.edu.unicen.seminarioentregable.R
import ar.edu.unicen.seminarioentregable.app.SeminarioEntregableApp
import ar.edu.unicen.seminarioentregable.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    private val movieAdapter = PopularMoviesAdapter { movie ->
        val intent = Intent(this, MoviePDPActivity::class.java)
        intent.putExtra("movie", movie)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val fragmentContainer = findViewById<FrameLayout>(R.id.fragment_container)

//        setContentView(R.layout.activity_main)

        viewModel.loading.onEach { loading ->
            if(loading){
                binding.progressBar.visibility = android.view.View.VISIBLE
//                binding.movieInformation.visibility = android.view.View.INVISIBLE
                if(!viewModel.loading.value){
                    binding.movieInformation.visibility = android.view.View.INVISIBLE
                }

            }else{
                binding.progressBar.visibility = android.view.View.INVISIBLE
                binding.movieInformation.visibility = android.view.View.VISIBLE
            }
        }.launchIn(lifecycleScope)

        val movieRecyclerView = binding.movieList
        movieRecyclerView.adapter = movieAdapter
        movieRecyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.movie.onEach { movie ->
            binding.movieTitle.text = movie?.title.orEmpty()
            binding.movieLanguage.text = movie?.original_language.orEmpty()
            binding.movieOverview.text = movie?.overview.orEmpty()

            if(movie != null){
                movieAdapter.submitList(listOf(movie))
            }else{
                movieAdapter.submitList(emptyList())
            }
        }.launchIn(lifecycleScope)

        binding.movieTitle.setOnClickListener {
            val movie = viewModel.movie.value
            if (movie != null) {
                val intent = Intent(this, MoviePDPActivity::class.java)
                intent.putExtra("movie", viewModel.movie.value)
                startActivity(intent)
            }
        }

        binding.movieOverview.setOnClickListener {
            val movie = viewModel.movie.value
            if (movie != null) {
                val intent = Intent(this, MoviePDPActivity::class.java)
                intent.putExtra("movie", viewModel.movie.value)
                startActivity(intent)
            }
        }

        viewModel.error.onEach { error ->
            if(error){
                if(viewModel.movie.value == null && viewModel.posterUrl.value == null){
                    binding.error.text = getString(R.string.error_no_results_found)
                }else{
                    binding.error.text = getString(R.string.error_message)
                }
                binding.error.visibility = android.view.View.VISIBLE
            }else{
                binding.error.visibility = android.view.View.INVISIBLE
            }
        }.launchIn(lifecycleScope)

        binding.searchMovieEditText.addTextChangedListener { text ->
            val isValid = text.toString() != null
            binding.searchMovieButton.isEnabled = isValid

            if(text.toString().isEmpty()){
                binding.movieTitle.visibility = android.view.View.INVISIBLE
                binding.movieLanguage.visibility = android.view.View.INVISIBLE
                binding.movieOverview.visibility = android.view.View.INVISIBLE
                binding.moviePoster.visibility = android.view.View.INVISIBLE
                binding.error.visibility = android.view.View.INVISIBLE
            }else{
                binding.movieTitle.visibility = android.view.View.VISIBLE
                binding.movieLanguage.visibility = android.view.View.VISIBLE
                binding.movieOverview.visibility = android.view.View.VISIBLE
                binding.moviePoster.visibility = android.view.View.VISIBLE
                binding.error.visibility = android.view.View.INVISIBLE
            }
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
            val application = applicationContext as SeminarioEntregableApp
            if(application.isNetworkAvailable()) {
                val intent = Intent(this, MoviePLPActivity::class.java)
                startActivity(intent)
            }else {
                Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show()
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PopularMoviesFragment())
            .commit()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.popular_movies_fragment -> {
                    binding.movieInformation.visibility = android.view.View.GONE

                    binding.fragmentContainer.visibility = android.view.View.VISIBLE

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, PopularMoviesFragment())
                        .commit()
                    true
                }
                R.id.wishlist_fragment -> {
                    binding.movieInformation.visibility = android.view.View.GONE

                    binding.fragmentContainer.visibility = android.view.View.VISIBLE

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, WishlistFragment())
                        .commit()
                    true
                }
                R.id.home_fragment -> {
                    binding.fragmentContainer.visibility = android.view.View.GONE

                    binding.movieInformation.visibility = android.view.View.VISIBLE

                    binding.popularMoviesButton.setOnClickListener {
                        val intent = Intent(this, MoviePLPActivity::class.java)
                        startActivity(intent)
                    }

                    binding.searchMovieButton.setOnClickListener {
                        val title = binding.searchMovieEditText.text.toString()
                        if (title != null) {
                            viewModel.getMovie(title)
                        }
                    }
                    true
                }
                R.id.trending_movies_fragment -> {

                    binding.movieInformation.visibility = android.view.View.GONE
                    binding.fragmentContainer.visibility = android.view.View.VISIBLE

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, TrendingMoviesFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}