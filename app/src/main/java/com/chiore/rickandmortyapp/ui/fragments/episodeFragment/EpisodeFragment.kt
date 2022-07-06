package com.chiore.rickandmortyapp.ui.fragments.episodeFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chiore.rickandmortyapp.R
import com.chiore.rickandmortyapp.adapters.EpisodeAdapter
import com.chiore.rickandmortyapp.databinding.EpisodeFragmentBinding
import com.chiore.rickandmortyapp.databinding.FeedFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeFragment : Fragment(R.layout.episode_fragment) {

    private var _binding: EpisodeFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var episodeAdapter: EpisodeAdapter
    private val viewModel: EpisodeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EpisodeFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()
        getCharacters()



    }

    private fun getCharacters() {
        viewModel.listEpisode.observe(viewLifecycleOwner, Observer { pagingData ->
            episodeAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
        })
    }

    private fun setupRv() {
        episodeAdapter = EpisodeAdapter()
        binding.episodeRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.episodeRv.addItemDecoration(DividerItemDecoration(requireContext(),RecyclerView.VERTICAL))
        binding.episodeRv.apply {
            adapter = episodeAdapter
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}