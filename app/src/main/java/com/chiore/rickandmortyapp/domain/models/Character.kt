package com.chiore.rickandmortyapp.domain.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Character(
    val episodeList: List<Episode> = listOf(),
    val gender: String = "",
    val id: Int = 0,
    val image: String = "",
    val loacation: Location = Location(),
    val name: String = "",
    val origin: Origin = Origin(),
    val species: String = "",
    val status: String = "",
) : Parcelable {

    @Parcelize
    data class Location(
        val name: String = "",
        val url: String = ""
    ) : Parcelable

    @Parcelize
    data class Origin(
        val name: String = "",
        val url: String = ""
    ) : Parcelable
}