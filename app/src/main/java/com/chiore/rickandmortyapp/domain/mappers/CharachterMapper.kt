package com.chiore.rickandmortyapp.domain.mappers

import com.chiore.rickandmortyapp.domain.models.Character
import com.chiore.rickandmortyapp.models.Characters
import com.chiore.rickandmortyapp.models.EpisodeResult

object CharachterMapper {

    fun buildFrom(
        response: Characters,
        episodes: List<EpisodeResult>
    ): Character {
        return Character(
            episodeList = episodes.map {
                EpisodeMapper.buildFrom(it, emptyList())
            },
            gender = response.gender,
            id = response.id,
            image = response.image,
            loacation = Character.Location(
                name = response.location.name,
                url = response.location.url
            ),
            name = response.name,
            origin = Character.Origin(
                name = response.origin.name,
                url = response.origin.url
            ),
            species = response.species,
            status = response.status
        )
    }

}