package com.chiore.rickandmortyapp.ui.fragments.detailsfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.chiore.rickandmortyapp.R
import com.chiore.rickandmortyapp.databinding.DetailsFragmentBinding
import com.chiore.rickandmortyapp.databinding.FeedFragmentBinding
import com.chiore.rickandmortyapp.enum.CharacterGenderEnums
import com.chiore.rickandmortyapp.models.Characters
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.details_fragment) {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var character: Characters

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

        args.character?.let {
            character = it
        }

        detailsFromFeed()

    }

    private fun detailsFromFeed() {
        binding.apply {
            Glide.with(requireView())
                .load(character.image)
                .into(headerImageView)

            nameTextView.text = character.name
            aliveTextView.text = character.status
            OriginTxt.text = character.origin.name
            SpeciesTxt.text = character.species
            episodeTv.text = "Number of episodes: ${character.episode.size.toString()}"

            when(character.gender) {
                CharacterGenderEnums.CHARACTER_GENDER_FEMALE.value -> genderImageView.findViewById<ImageView>(
                    R.drawable.ic_female
                )
                CharacterGenderEnums.CHARACTER_GENDER_MALE.value -> genderImageView.findViewById<ImageView>(
                    R.drawable.ic_male
                )
                CharacterGenderEnums.CHARACTER_GENDER_GENDERLESS.value -> genderImageView.findViewById<ImageView>(
                    R.drawable.ic_genderles
                )
                CharacterGenderEnums.CHARACTER_GENDER_UNKNOWN.value -> genderImageView.findViewById<ImageView>(
                    R.drawable.ic_unkown
                )
                else -> {
                    genderImageView.findViewById<ImageView>(R.drawable.ic_unkown)
                }
            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}