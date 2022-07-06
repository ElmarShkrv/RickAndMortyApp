package com.chiore.rickandmortyapp.api

import com.chiore.rickandmortyapp.models.CharacterResponse
import com.chiore.rickandmortyapp.models.Characters
import com.chiore.rickandmortyapp.models.Episode
import com.chiore.rickandmortyapp.models.EpisodeResult
import com.chiore.rickandmortyapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SimpleApi {

    @GET("character/")
    suspend fun getAllCharacters(
        @Query("page") page: Int? = null
    ) : Response<CharacterResponse>

    @GET("episode/")
    suspend fun getAllEpisodes(
        @Query("page") page: Int? = null
    ) : Response<Episode>

    @GET("character/{character-id}")
    suspend fun getCharacterById(
        @Path("character-id") characterId : Int
    ): Response<Characters>

    @GET("character/{character-range}")
    suspend fun getCharacterRange(
        @Path("character-range") characterId: String
    ): Response<List<Characters>>

    @GET("episode/{episode-range}")
    suspend fun getEpisodeRange(
        @Path("episode-range") episodeId: String
    ): Response<List<EpisodeResult>>

    @GET("episode/{episode-id}")
    suspend fun getEpisodeById(
        @Path("episode-id") episodeId: Int
    ): Response<EpisodeResult>



}