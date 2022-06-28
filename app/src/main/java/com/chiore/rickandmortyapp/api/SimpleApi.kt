package com.chiore.rickandmortyapp.api

import com.chiore.rickandmortyapp.models.CharacterResponse
import com.chiore.rickandmortyapp.models.Episode
import com.chiore.rickandmortyapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
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



}