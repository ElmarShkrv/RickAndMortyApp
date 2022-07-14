package com.chiore.rickandmortyapp.ui.fragments.feedfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.chiore.rickandmortyapp.R
import com.chiore.rickandmortyapp.adapters.feedadpter.FeedAdapter
import com.chiore.rickandmortyapp.adapters.feedadpter.FeedLoadStateAdapter
import com.chiore.rickandmortyapp.databinding.FeedFragmentBinding
import com.chiore.rickandmortyapp.ui.fragments.feedfragment.viewmodel.FeedViewModel
import com.chiore.rickandmortyapp.ui.fragments.feedfragment.viewmodel.states.ListType
import com.chiore.rickandmortyapp.utils.CalculateWindowSize
import com.chiore.rickandmortyapp.utils.WindowSizeClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : Fragment(R.layout.feed_fragment) {

    private var _binding: FeedFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var feedAdapter: FeedAdapter
    private val viewModel: FeedViewModel by viewModels()
    lateinit var widthWindowClass: WindowSizeClass

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

        widthWindowClass = CalculateWindowSize(requireActivity()).calculateCurrentWidthSize()

        setupRv()
        getCharacters()
        initAdapter()

        binding.imgListType.apply {
            this.setOnClickListener {
                viewModel.setLayoutManager()

                setListTypeIcon(this)
                setCharacterListLayoutManager()
            }
        }

    }

    private fun setCharacterListLayoutManager() {
        if (viewModel.getListType() == ListType.GridLayout) {

            val spanCount = if (widthWindowClass == WindowSizeClass.EXPANDED) 3 else 2

            binding.feedRv.layoutManager = GridLayoutManager(requireContext(), spanCount)

            feedAdapter.setListType(ListType.GridLayout)
        } else {
            binding.feedRv.layoutManager = LinearLayoutManager(requireContext())
            feedAdapter.setListType(ListType.LinearLayout)
        }
    }

    private fun setListTypeIcon(imageView: ImageView) {
        val icon = when (viewModel.getListType()) {
            ListType.GridLayout -> R.drawable.ic_lin
            else -> R.drawable.ic_grid
        }
        imageView.setImageResource(icon)

    }

    private fun initAdapter() {

        binding.feedRv.adapter = feedAdapter.withLoadStateHeaderAndFooter(
            header = FeedLoadStateAdapter { feedAdapter.retry() },
            footer = FeedLoadStateAdapter { feedAdapter.retry() },
        )

        feedAdapter.addLoadStateListener { loadState ->
            binding.feedRv.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.retryBtn.isVisible = loadState.source.refresh is LoadState.Error
            handleError(loadState)
        }
        binding.retryBtn.setOnClickListener {
            feedAdapter.retry()
        }
    }

    private fun handleError(loadState: CombinedLoadStates) {
        val errorStates = loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error

        errorStates?.let {
            Toast.makeText(requireContext(), "${it.error}", Toast.LENGTH_LONG).show()
        }

    }

    private fun getCharacters() {
        viewModel.listData.observe(viewLifecycleOwner, Observer { pagingData ->
                feedAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
        })
    }



    private fun setupRv() {
        feedAdapter = FeedAdapter()
        binding.feedRv.layoutManager =
                GridLayoutManager(requireContext(),2)
            //LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.feedRv.apply {
            adapter = feedAdapter
            setHasFixedSize(true)
            visibility = View.VISIBLE
        }
    }



    override fun onStart() {
        super.onStart()
        setListTypeIcon(binding.imgListType)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}