package com.chiore.rickandmortyapp.domain.mappers

import com.chiore.rickandmortyapp.domain.models.Episode
import com.chiore.rickandmortyapp.models.EpisodeResult

object EpisodeMapper {

    fun buildFrom(networkEpiosde: EpisodeResult): Episode {
        return Episode(
            id = networkEpiosde.id,
            name = networkEpiosde.name,
            airDate = networkEpiosde.air_date,
            episode = networkEpiosde.episode,
            created = networkEpiosde.created
        )
    }

}