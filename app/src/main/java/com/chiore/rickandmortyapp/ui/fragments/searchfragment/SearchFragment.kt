package com.chiore.rickandmortyapp.ui.fragments.searchfragment

import android.os.Bundle
import android.text.Editable
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chiore.rickandmortyapp.R
import com.chiore.rickandmortyapp.adapters.SearchAdapter
import com.chiore.rickandmortyapp.databinding.SearchFragmentBinding
import com.chiore.rickandmortyapp.utils.Constants.Companion.SEARCH_CHARACTER_TIME_DELAY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.search_fragment) {

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter

    /*

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                binding.defultImage.visibility = View.INVISIBLE
            }
        })
    }

     */



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        setupRv()

    }

    private fun setupUi() {
        binding.serchIv.setImageResource(R.drawable.ic_search)


        viewModel.characters.observe(viewLifecycleOwner) {
            searchAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        var job: Job? = null
        binding.defultImage.visibility = View.VISIBLE
        binding.searchTxt.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_CHARACTER_TIME_DELAY)
                editable?.let { query ->
                    if (query.toString().isNotEmpty()) {
                        binding.feedRv.scrollToPosition(0)
                        binding.defultImage.visibility = View.INVISIBLE
                        viewModel.searchCharacter(query.toString())

                    }
                }
            }
        }
    }

    private fun setupRv() {
        searchAdapter = SearchAdapter()
        binding.feedRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.feedRv.apply {
            adapter = searchAdapter
            setHasFixedSize(true)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

}