package com.chiore.rickandmortyapp.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chiore.rickandmortyapp.R
import com.chiore.rickandmortyapp.databinding.SaveRowBinding
import com.chiore.rickandmortyapp.domain.models.Character
import com.chiore.rickandmortyapp.enum.CharacterStatusEnums
import com.chiore.rickandmortyapp.models.Characters
import com.chiore.rickandmortyapp.ui.fragments.feedfragment.FeedFragmentDirections

class SaveAdapter :
    ListAdapter<Characters, SaveAdapter.SaveViewHolder>(DiffUtilCallBack()) {


    class SaveViewHolder(val binding: SaveRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun create(parent: ViewGroup): SaveViewHolder {
                val binding = SaveRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return SaveViewHolder(binding)
            }
        }

        fun bind(characters: Characters) {

            itemView.setOnClickListener { view ->
                val action = FeedFragmentDirections
                    .actionGlobalToDetailsFragment(characters.id)
                Navigation.findNavController(view).navigate(action)
            }

            with(binding) {

                Glide.with(itemView)
                    .load(characters.image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_launcher_background)
                    .into(feedRvImage)
                saveRvName.text = characters.name
                saveRvStatus.text = characters.status
                saveRvSpecies.text = characters.species
                saveRvGender.text = characters.gender

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaveViewHolder {
        return SaveViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SaveViewHolder, position: Int) {
        val characterDomain = getItem(position)
        holder.bind(characterDomain)
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<Characters>() {
        override fun areItemsTheSame(oldItem: Characters, newItem: Characters): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Characters, newItem: Characters): Boolean {
            return oldItem == newItem
        }

    }

}