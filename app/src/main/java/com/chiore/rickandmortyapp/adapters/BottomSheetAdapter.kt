package com.chiore.rickandmortyapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chiore.rickandmortyapp.R
import com.chiore.rickandmortyapp.databinding.BottomRowBinding
import com.chiore.rickandmortyapp.domain.models.Character

class BottomSheetAdapter(var characterList: List<Character>) :
    RecyclerView.Adapter<BottomSheetAdapter.BottomSheetViewHolder>() {

    class BottomSheetViewHolder(val binding: BottomRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetViewHolder {
        val binding = BottomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BottomSheetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BottomSheetViewHolder, position: Int) {
        val currentItem = characterList[position]
        holder.binding.apply {
            Glide.with(root)
                .load(currentItem.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_launcher_background)
                .into(characterImageView)

            characterNameTextView.text = currentItem.name
        }
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

}