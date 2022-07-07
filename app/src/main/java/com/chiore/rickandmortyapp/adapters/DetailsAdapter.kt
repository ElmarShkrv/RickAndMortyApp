package com.chiore.rickandmortyapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.chiore.rickandmortyapp.databinding.DetailRowBinding
import com.chiore.rickandmortyapp.domain.models.Episode
import com.chiore.rickandmortyapp.ui.fragments.detailsfragment.DetailsFragmentDirections

class DetailsAdapter(var episodeList: List<Episode>) :
    RecyclerView.Adapter<DetailsAdapter.DetailViewHolder>() {

    class DetailViewHolder(val binding: DetailRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val binding = DetailRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val currentItem = episodeList[position]

        holder.binding.apply {

            holder.itemView.setOnClickListener { view ->
                val action = DetailsFragmentDirections
                    .actionDetailsFragmentToBottomSheetFragment(currentItem.id)
                Navigation.findNavController(view).navigate(action)
            }

            episodeName.text = currentItem.name
            episodeAirDate.text = currentItem.airDate
            episodeCreated.text = currentItem.created
        }
    }

    override fun getItemCount(): Int {
        return episodeList.size
    }

}
