package com.chiore.rickandmortyapp.models

data class CharacterResponse(
    val info: Info,
    val characters: List<Characters>
)