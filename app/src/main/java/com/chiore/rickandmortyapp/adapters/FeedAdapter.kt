package com.chiore.rickandmortyapp.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chiore.rickandmortyapp.R
import com.chiore.rickandmortyapp.databinding.FeedFragmentRvBinding
import com.chiore.rickandmortyapp.enum.CharacterStatusEnums
import com.chiore.rickandmortyapp.models.Characters
import com.chiore.rickandmortyapp.models.EpisodeResult
import com.chiore.rickandmortyapp.ui.fragments.feedfragment.FeedFragmentDirections

class FeedAdapter : PagingDataAdapter<Characters, FeedAdapter.FeedViewHolder>(DiffCallback()) {

    inner class FeedViewHolder(private val binding: FeedFragmentRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(characters: Characters) {

            itemView.setOnClickListener { view ->
                val action = FeedFragmentDirections
                    .actionGlobalToDetailsFragment(characters)
                Navigation.findNavController(view).navigate(action)
            }

            with(binding) {

                Glide.with(itemView)
                    .load(characters.image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_launcher_background)
                    .into(feedRvImage)
                feedRvName.text = characters.name
                feedRvStatus.text = characters.status
                feedRvSpecies.text = characters.species
                feedRvGender.text = characters.gender

                when (characters.status) {
                    CharacterStatusEnums.CHARACTER_ALIVE.value -> imgCharacterStatus.setColorFilter(
                        Color.parseColor("#14D91B")
                    )
                    CharacterStatusEnums.CHARACTER_DEAD.value -> imgCharacterStatus.setColorFilter(
                        Color.parseColor("#FF0800")
                    )
                    CharacterStatusEnums.CHARACTER_UNKNOWN.value -> imgCharacterStatus.setColorFilter(
                        Color.parseColor("#E3E3E3")
                    )
                    else -> {
                        imgCharacterStatus.setColorFilter(Color.parseColor("#F8F816"))
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val binding =
            FeedFragmentRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedViewHolder(binding)
    }


    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


    class DiffCallback : DiffUtil.ItemCallback<Characters>() {
        override fun areItemsTheSame(oldItem: Characters, newItem: Characters): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Characters, newItem: Characters): Boolean {
            return oldItem == newItem
        }
    }
}