package com.chiore.rickandmortyapp.ui.fragments.searchfragment

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chiore.rickandmortyapp.api.SimpleApi
import com.chiore.rickandmortyapp.models.Characters

class SearchFragmentPagingSource(
    private val simpleApi: SimpleApi,
    private val query: String
) : PagingSource<Int, Characters>() {

    override fun getRefreshKey(state: PagingState<Int, Characters>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Characters> {
        val pageNumber = params.key ?: 1
        return try {
            val response = simpleApi.getSearchCharacters(query, pageNumber)
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