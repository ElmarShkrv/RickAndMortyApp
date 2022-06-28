package com.chiore.rickandmortyapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chiore.rickandmortyapp.databinding.EpisodeRowBinding
import com.chiore.rickandmortyapp.models.EpisodeResult

class EpisodeAdapter : PagingDataAdapter<EpisodeResult, EpisodeAdapter.EpisodeViewHolder>(DiffCallback()) {

    inner class EpisodeViewHolder(private val binding: EpisodeRowBinding) :
            RecyclerView.ViewHolder(binding.root) {
                fun bind(episodeResult: EpisodeResult) {
                    with(binding) {
                        episodeName.text = episodeResult.name
                        episodeCreated.text = "Created: ${episodeResult.created}"
                        episodeAirDate.text = "Air Date: ${episodeResult.air_date}"
                        episodeCharacters.text = "Characters: ${episodeResult.characters.size.toString()}"
                    }
                }
            }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding =
            EpisodeRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodeViewHolder(binding)
    }

    class DiffCallback : DiffUtil.ItemCallback<EpisodeResult>() {
        override fun areItemsTheSame(oldItem: EpisodeResult, newItem: EpisodeResult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EpisodeResult, newItem: EpisodeResult): Boolean {
            return oldItem == newItem
        }
    }

}