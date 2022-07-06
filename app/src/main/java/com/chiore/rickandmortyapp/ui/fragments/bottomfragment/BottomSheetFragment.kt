package com.chiore.rickandmortyapp.ui.fragments.bottomfragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.chiore.rickandmortyapp.adapters.BottomSheetAdapter
import com.chiore.rickandmortyapp.databinding.BottomsheetFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: BottomsheetFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: BottomSheetFragmentArgs by navArgs()
    private val viewModel: BottomSheetViewModel by viewModels()
    private lateinit var bottomshetAdapter: BottomSheetAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomsheetFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.refreshEpisode(args.episodeId)

        fromEpisode()
        setupRv()



    }

    private fun setupRv() {
        viewModel.episodeByLiveData.observe(
            viewLifecycleOwner,
            Observer { episodeCharacterList ->
                bottomshetAdapter = BottomSheetAdapter(episodeCharacterList!!.characters)
                binding.bottomRv.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding.bottomRv.apply {
                    adapter = bottomshetAdapter
                }
            }
        )
    }

    private fun fromEpisode() {
        viewModel.episodeByLiveData.observe(viewLifecycleOwner, Observer { episodeCharacterList ->

            episodeCharacterList?.let { episodeData ->
                binding.episodeNumberTextView.text = episodeData.episode
                binding.episodeNameTextView.text = episodeData.name
                binding.episodeAirDateTextView.text = episodeData.airDate
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}