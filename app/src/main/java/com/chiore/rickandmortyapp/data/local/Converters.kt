package com.chiore.rickandmortyapp.data.local


import androidx.room.TypeConverter
import com.chiore.rickandmortyapp.domain.models.Character
import com.chiore.rickandmortyapp.domain.models.Episode
import com.chiore.rickandmortyapp.models.Location
import com.chiore.rickandmortyapp.models.Origin
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {



    @TypeConverter
    fun mapListToString(value: List<String>): String {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun mapStringToList(value: String): List<String> {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }



    @TypeConverter
    fun fromLocation(location: Location): String {
        return location.name
    }

    @TypeConverter
    fun toLocation(name: String): Location {
        return Location(name,name)
    }

    @TypeConverter
    fun fromOrigin(origin: Origin): String {
        return origin.name
    }

    @TypeConverter
    fun toOrigin(name: String): Origin {
        return Origin(name, name)
    }


}