package com.chiore.rickandmortyapp.ui.fragments.bottomfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chiore.rickandmortyapp.domain.models.Episode
import com.chiore.rickandmortyapp.repository.BottomSheetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomSheetViewModel @Inject constructor(
    private val repository: BottomSheetRepository
) : ViewModel() {

    private val _episodeByLiveData = MutableLiveData<Episode?>()
    val episodeByLiveData: LiveData<Episode?> = _episodeByLiveData

    fun refreshEpisode(episodeId: Int) {
        viewModelScope.launch {
            val response = repository.getEpisod(episodeId)

            _episodeByLiveData.postValue(response)
        }
    }

}