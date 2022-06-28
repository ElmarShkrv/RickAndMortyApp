package com.chiore.rickandmortyapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.chiore.rickandmortyapp.api.SimpleApi
import com.chiore.rickandmortyapp.ui.fragments.episodeFragment.EpisodeFragmentPagingSource
import javax.inject.Inject

class EpisodeRepository @Inject constructor(
    private val simpleApi: SimpleApi
) {
    fun getAllEpisodes() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {EpisodeFragmentPagingSource(simpleApi)}
        ).liveData
}