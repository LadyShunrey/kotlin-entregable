package ar.edu.unicen.seminarioentregable.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ar.edu.unicen.seminarioentregable.R
import ar.edu.unicen.seminarioentregable.databinding.FragmentWishlistBinding
import ar.edu.unicen.seminarioentregable.ddl.models.WishlistMovie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WishlistFragment: Fragment() {

    private val viewModel: WishlistViewModel by viewModels()
    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar.visibility = View.VISIBLE
        binding.movieList.visibility = View.INVISIBLE

        val movieAdapter = WishlistMovieAdapter(
            onMovieClick = { movie ->
                val intent = Intent(requireContext(), MoviePDPActivity::class.java)
                intent.putExtra("movie", movie.id)
                startActivity(intent)
            },
            viewModel = viewModel
        )
        binding.movieList.adapter = movieAdapter
        binding.movieList.layoutManager = LinearLayoutManager(requireContext())

        movieAdapter.showRemoveButton = true

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.wishlistMovies.collectLatest { wishlistMovies ->
                movieAdapter.submitList(wishlistMovies)
                delay(2000)
                binding.progressBar.visibility = View.INVISIBLE
                binding.movieList.visibility = View.VISIBLE
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}