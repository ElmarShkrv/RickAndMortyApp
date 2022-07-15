package com.chiore.rickandmortyapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chiore.rickandmortyapp.domain.models.Character
import com.chiore.rickandmortyapp.models.Characters

@Database(
    entities = [Characters::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class CharacterDatabase : RoomDatabase() {

    abstract fun CharacterDao() : CharacterDao

}