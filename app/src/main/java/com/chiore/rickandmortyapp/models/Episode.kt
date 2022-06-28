package com.chiore.rickandmortyapp.models

data class Episode(
    val info: InfoX,
    val results: List<EpisodeResult>
)