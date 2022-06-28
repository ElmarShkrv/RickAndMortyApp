package com.chiore.rickandmortyapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.chiore.rickandmortyapp.api.SimpleApi
import com.chiore.rickandmortyapp.ui.fragments.feedfragment.FeedFragmentPagingSource
import javax.inject.Inject

class FeedRepository @Inject constructor(
    private val simpleApi: SimpleApi
) {
    fun getCharactersLiveData() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { FeedFragmentPagingSource(simpleApi) }
        ).liveData
}