package com.chiore.rickandmortyapp.ui.fragments.detailsfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.chiore.rickandmortyapp.R
import com.chiore.rickandmortyapp.adapters.DetailsAdapter
import com.chiore.rickandmortyapp.databinding.DetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.details_fragment) {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()
    //private lateinit var character: Characters
    //private lateinit var bottomData: Character
    private val viewModel: DetailsViewModel by viewModels()
    private lateinit var detailsAdapter: DetailsAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.refreshCharachter(args.characterId)

        setupUi()
        setupRv()

    }

    private fun setupUi() {
        viewModel.charachterByLiveData.observe(
            viewLifecycleOwner,
            Observer { characterData ->
                characterData?.let { character ->
                    binding.apply {
                        Glide.with(requireView())
                            .load(character.image)
                            .into(headerImageView)

                        nameTextView.text = character.name
                        aliveTextView.text = character.status
                        OriginTxt.text = character.origin.name
                        SpeciesTxt.text = character.species

                        genderSetup()
                    }
                }
            }
        )
    }



    private fun genderSetup() {

        viewModel.charachterByLiveData.observe(
            viewLifecycleOwner,
            Observer { characterData ->
                characterData?.let { character ->
                    binding.apply {

                        if (character.gender.equals("Male", true)) {
                            genderImageView.setImageResource(R.drawable.ic_male)
                        } else if (character.gender.equals("Female", true)) {
                            genderImageView.setImageResource(R.drawable.ic_female)
                        } else if (character.gender.equals("genderless", true)) {
                            genderImageView.setImageResource(R.drawable.ic_genderles)
                        } else {
                            genderImageView.setImageResource(R.drawable.ic_unkown)
                        }

                    }
                }
            }
        )
    }

    private fun setupRv() {
        viewModel.charachterByLiveData.observe(
            viewLifecycleOwner,
            Observer { characterEpisodeList ->
                detailsAdapter = DetailsAdapter(characterEpisodeList!!.episodeList)
                binding.detailsRv.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding.detailsRv.apply {
                    adapter = detailsAdapter
                    //setHasFixedSize(true)
                    visibility = View.VISIBLE
                }

            })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}