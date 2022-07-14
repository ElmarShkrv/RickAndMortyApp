package com.chiore.rickandmortyapp.ui.fragments.feedfragment

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chiore.rickandmortyapp.api.SimpleApi
import com.chiore.rickandmortyapp.models.Characters
import com.chiore.rickandmortyapp.utils.Constants.Companion.STARTING_PAGE_INDEX
import kotlinx.coroutines.delay

class FeedFragmentPagingSource(
    private val simpleApi: SimpleApi
) : PagingSource<Int, Characters>() {
    override fun getRefreshKey(state: PagingState<Int, Characters>): Int? {
        return null
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Characters> {
        val pageNumber = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = simpleApi.getAllCharacters(pageNumber)
            //delay(2000)
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
                prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber -1,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}