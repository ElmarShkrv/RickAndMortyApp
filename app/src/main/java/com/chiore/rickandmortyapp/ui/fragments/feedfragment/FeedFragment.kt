package com.chiore.rickandmortyapp.ui.fragments.feedfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.chiore.rickandmortyapp.R
import com.chiore.rickandmortyapp.adapters.FeedAdapter
import com.chiore.rickandmortyapp.databinding.FeedFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : Fragment(R.layout.feed_fragment) {

    private var _binding: FeedFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var feedAdapter: FeedAdapter
    private val viewModel: FeedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FeedFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()
        getCharacters()
    }

    private fun getCharacters() {
        viewModel.listData.observe(viewLifecycleOwner, Observer { pagingData ->
                feedAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
        })
    }


    private fun setupRv() {
        feedAdapter = FeedAdapter()
        binding.feedRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.feedRv.apply {
            adapter = feedAdapter
            setHasFixedSize(true)
            visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}