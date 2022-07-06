package com.chiore.rickandmortyapp.repository

import com.chiore.rickandmortyapp.api.SimpleApi
import com.chiore.rickandmortyapp.domain.mappers.EpisodeMapper
import com.chiore.rickandmortyapp.domain.models.Episode
import com.chiore.rickandmortyapp.models.Characters
import com.chiore.rickandmortyapp.models.EpisodeResult
import javax.inject.Inject

class BottomSheetRepository @Inject constructor(
    private val simpleApi: SimpleApi
) {

    suspend fun getEpisod(episodeId: Int): Episode? {
        val request = simpleApi.getEpisodeById(episodeId)

        if (!request.isSuccessful) {
            return null
        }

        val networkCharacter = getCharacterFromEpisodeResponse(request.body()!!)
        return EpisodeMapper.buildFrom(
            networkEpiosde = request.body()!!,
            characters = networkCharacter
        )
    }

    private suspend fun getCharacterFromEpisodeResponse(
        episodeResponse: EpisodeResult
    ): List<Characters> {
        val characterRange = episodeResponse.characters.map {
            it.substring(it.lastIndexOf("/") + 1)
        }.toString()
        val request = simpleApi.getCharacterRange(characterRange)

        if (!request.isSuccessful) {
            return emptyList()
        }

        return request.body()!!
    }

}