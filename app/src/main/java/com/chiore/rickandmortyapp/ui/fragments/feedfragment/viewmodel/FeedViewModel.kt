package com.chiore.rickandmortyapp.ui.fragments.feedfragment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.chiore.rickandmortyapp.repository.FeedRepository
import com.chiore.rickandmortyapp.ui.fragments.feedfragment.viewmodel.states.CharacterActivityState
import com.chiore.rickandmortyapp.ui.fragments.feedfragment.viewmodel.states.ListType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FeedViewModel
@Inject constructor(
    private val repository: FeedRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterActivityState())
    val state: StateFlow<CharacterActivityState> get() = _state

    val listData = repository.getCharactersLiveData().cachedIn(viewModelScope)

    fun setLayoutManager() {
        when (this.getListType()) {
            ListType.GridLayout -> this.setListLayoutManager(ListType.LinearLayout)
            else -> this.setListLayoutManager(ListType.GridLayout)
        }
    }


    fun getListType(): ListType {
        return _state.value.listType
    }



    private fun setListLayoutManager(newType: ListType) {
        _state.value = _state.value.copy(
            listType = newType
        )
    }


}