package com.chiore.rickandmortyapp.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Episode(
    val id: Int = 0,
    val name: String = "",
    val airDate: String = "",
    val episode: String = "",
    val created: String = "",
    val characters: List<Character>
) : Parcelable