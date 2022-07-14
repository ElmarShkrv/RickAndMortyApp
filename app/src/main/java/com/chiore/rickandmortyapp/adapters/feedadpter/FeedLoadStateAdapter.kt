package com.chiore.rickandmortyapp.adapters.feedadpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chiore.rickandmortyapp.databinding.ItemLoadStateBinding

class FeedLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<FeedLoadStateAdapter.FeedLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): FeedLoadStateViewHolder {
        val binding =
            ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FeedLoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class FeedLoadStateViewHolder(private val binding: ItemLoadStateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryBtn.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                retryBtn.isVisible = loadState !is LoadState.Loading
                errorTv.isVisible = loadState !is LoadState.Loading
            }
        }

    }


}