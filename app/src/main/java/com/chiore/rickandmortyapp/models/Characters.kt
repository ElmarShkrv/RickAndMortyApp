package com.chiore.rickandmortyapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    tableName = "characters"
)
data class Characters(
    val episode: List<String>,
    val gender: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String
) : Parcelable {

}