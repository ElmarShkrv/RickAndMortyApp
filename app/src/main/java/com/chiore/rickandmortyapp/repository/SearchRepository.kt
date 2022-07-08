package com.chiore.rickandmortyapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.chiore.rickandmortyapp.api.SimpleApi
import com.chiore.rickandmortyapp.ui.fragments.searchfragment.SearchFragmentPagingSource
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val simpleApi: SimpleApi
) {

    fun getSearchLiveData(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {SearchFragmentPagingSource(simpleApi, query)}
        ).liveData

}