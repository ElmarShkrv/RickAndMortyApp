package com.chiore.rickandmortyapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chiore.rickandmortyapp.domain.models.Character
import com.chiore.rickandmortyapp.models.Characters

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(characters: Characters): Long

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): LiveData<List<Characters>>

    @Delete
    suspend fun deleteCharacter(characters: Characters)

}