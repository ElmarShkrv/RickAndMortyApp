package com.chiore.rickandmortyapp.ui.fragments.episodeFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chiore.rickandmortyapp.repository.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    private val repository: EpisodeRepository
) : ViewModel() {

    val listEpisode = repository.getAllEpisodes().cachedIn(viewModelScope)

}