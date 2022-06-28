package com.chiore.rickandmortyapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chiore.rickandmortyapp.R
import com.chiore.rickandmortyapp.databinding.FeedFragmentRvBinding
import com.chiore.rickandmortyapp.models.Characters

class FeedAdapter : PagingDataAdapter<Characters,FeedAdapter.FeedViewHolder >(DiffCallback()) {

    inner class FeedViewHolder(private val binding: FeedFragmentRvBinding) :
            RecyclerView.ViewHolder(binding.root) {
                fun bind(characters: Characters) {
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

                        println(characters.name)


                    }
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val binding =
            FeedFragmentRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
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