package com.chiore.rickandmortyapp.adapters.feedadpter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chiore.rickandmortyapp.R
import com.chiore.rickandmortyapp.adapters.SaveAdapter
import com.chiore.rickandmortyapp.databinding.FeedFragmentRvBinding
import com.chiore.rickandmortyapp.enum.CharacterStatusEnums
import com.chiore.rickandmortyapp.models.Characters
import com.chiore.rickandmortyapp.ui.fragments.feedfragment.FeedFragmentDirections
import com.chiore.rickandmortyapp.ui.fragments.feedfragment.viewmodel.states.ListType

const val GRID_LAYOUT = 0
const val LINEARLAYOUT = 1


class FeedAdapter(
    private var listType: ListType = ListType.GridLayout
) : PagingDataAdapter<Characters, RecyclerView.ViewHolder>(DiffCallback()) {

    fun setListType(listType: ListType) {
        this.listType = listType
    }

    class FeedViewHolder(private val binding: FeedFragmentRvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): FeedViewHolder {
                val binding = FeedFragmentRvBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

                return FeedViewHolder(binding)
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
                    .into(characterImage)
                characterName.text = characters.name
                characterStatus.text = characters.status

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == GRID_LAYOUT) {
            FeedViewHolder.from(parent)
        } else {
            SaveAdapter.SaveViewHolder.create(parent)
        }

    }

    /*

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {

        return if (viewType == GRID_LAYOUT) {
            FeedViewHolder.from(parent)
        } else {
            SaveAdapter.SaveViewHolder.create(parent)
        }

        /*
        val binding =
            FeedFragmentRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedViewHolder(binding)

         */
    }

     */

    override fun getItemViewType(position: Int): Int {
        return when (listType) {
            ListType.GridLayout -> GRID_LAYOUT
            ListType.LinearLayout -> LINEARLAYOUT
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val characterModel = getItem(position)
        if (listType == ListType.GridLayout) {
            holder as FeedViewHolder
            holder.bind(characterModel!!)

            holder.itemView.animation = AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.scale_up
            )

        } else {
            holder as SaveAdapter.SaveViewHolder
            holder.bind(characterModel!!)

            holder.itemView.animation = AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.up_anim
            )
        }

        //getItem(position)?.let { holder.bind(it) }
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