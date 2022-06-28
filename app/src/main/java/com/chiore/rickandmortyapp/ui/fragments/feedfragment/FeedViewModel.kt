package com.chiore.rickandmortyapp.ui.fragments.feedfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.chiore.rickandmortyapp.repository.FeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedViewModel
@Inject constructor(
    private val repository: FeedRepository
) : ViewModel() {

    /*
    val listData = Pager(PagingConfig(pageSize = 1)) {
        FeedFragmentPagingSource(simpleApi)
    }.flow.cachedIn(viewModelScope)

     */



    val listData = repository.getCharactersLiveData().cachedIn(viewModelScope)

}