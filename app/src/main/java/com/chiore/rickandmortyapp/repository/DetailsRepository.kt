package com.chiore.rickandmortyapp.repository

import com.chiore.rickandmortyapp.api.SimpleApi
import com.chiore.rickandmortyapp.data.local.CharacterDao
import com.chiore.rickandmortyapp.domain.mappers.CharachterMapper
import com.chiore.rickandmortyapp.domain.models.Character
import com.chiore.rickandmortyapp.models.Characters
import com.chiore.rickandmortyapp.models.EpisodeResult
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val simpleApi: SimpleApi,
    private val characterDao: CharacterDao
) {

    suspend fun upsert(characters: Characters) = characterDao.upsert(characters)

    fun getSavedCharacters() = characterDao.getAllCharacters()

    suspend fun deleteCharacter(characters: Characters) = characterDao.deleteCharacter(characters)

    suspend fun getCharactersForSave(charachterId: Int): Characters? {
        val request = simpleApi.getCharacterById(charachterId)
        if (!request.isSuccessful) {
            return null
        }
        return request.body()
    }

    suspend fun getCharachter(charachterId: Int): Character? {
        val request = simpleApi.getCharacterById(charachterId)

        if (!request.isSuccessful) {
            return null
        }

        val networkEpiosde = getEpiosdeFromChaacterResponse(request.body()!!)
        return CharachterMapper.buildFrom(
            response = request.body()!!,
            episodes = networkEpiosde
        )
    }

    private suspend fun getEpiosdeFromChaacterResponse(
        characterResponse: Characters
    ): List<EpisodeResult> {
        val episodeRange = characterResponse.episode.map {
            it.substring(it.lastIndexOf("/") + 1)
        }.toString()
        val request = simpleApi.getEpisodeRange(episodeRange)

        if (!request.isSuccessful) {
            return emptyList()
        }

        return request.body()!!

    }
}