package com.chiore.rickandmortyapp.ui.fragments.searchfragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chiore.rickandmortyapp.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)


    fun searchCharacter(query: String) {
        currentQuery.value = query
    }

    val characters = currentQuery.switchMap { queryString ->
        repository.getSearchLiveData(queryString).cachedIn(viewModelScope)
    }


    companion object {
        private const val DEFAULT_QUERY = "empty"
    }

}