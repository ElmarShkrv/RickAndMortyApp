package com.chiore.rickandmortyapp.ui.fragments.episodeFragment

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chiore.rickandmortyapp.api.SimpleApi
import com.chiore.rickandmortyapp.models.EpisodeResult

class EpisodeFragmentPagingSource(
    private val simpleApi: SimpleApi
) : PagingSource<Int, EpisodeResult>() {
    override fun getRefreshKey(state: PagingState<Int, EpisodeResult>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodeResult> {
        val pageNumber = params.key ?: 1
        return try {
            val response = simpleApi.getAllEpisodes(pageNumber)
            val pagedResponse = response.body()
            val data = pagedResponse?.results

            var nextPageNumber: Int? = null
            if (pagedResponse?.info?.next != null) {
                val uri = Uri.parse(pagedResponse.info.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }

            LoadResult.Page(
                data = data.orEmpty(),
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}