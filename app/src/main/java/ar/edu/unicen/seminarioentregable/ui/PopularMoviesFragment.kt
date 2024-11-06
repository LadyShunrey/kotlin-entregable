package ar.edu.unicen.seminarioentregable.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ar.edu.unicen.seminarioentregable.R
import ar.edu.unicen.seminarioentregable.databinding.FragmentPopularMoviesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularMoviesFragment: Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentPopularMoviesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPopularMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loading.onEach { loading ->
            if(loading){
                binding.progressBar.visibility = View.VISIBLE
                binding.movieList.visibility = View.INVISIBLE
            }else{
                delay(2000)
                binding.progressBar.visibility = View.INVISIBLE
                binding.movieList.visibility = View.VISIBLE
            }
        }.launchIn(lifecycleScope)

        val movieAdapter = PopularMoviesAdapter(
            onMovieClick = { movie ->
                val intent = Intent(requireContext(), MoviePDPActivity::class.java)
                intent.putExtra("movie", movie)
                startActivity(intent)
            }
        )

        binding.movieList.adapter = movieAdapter
        binding.movieList.layoutManager = LinearLayoutManager(requireContext())

        viewModel.popularMovies.onEach { popularMovies ->
            try{
                lifecycleScope.launch {
                    delay(2000)
                    movieAdapter.submitList(popularMovies?.results?.map { it.toMovie() })
                }
            }catch(e: Exception){
                Log.e("PopularMoviesFragment", "Error al actualizar la lista", e)
            }
        }.launchIn(lifecycleScope)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}