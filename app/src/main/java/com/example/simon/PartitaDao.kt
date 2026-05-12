package com.example.simon

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Query
import androidx.room3.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface PartitaDao {

    @Upsert
    suspend fun upsertPartita(partita: Partita)

    @Delete
    suspend fun deletePartita(partita: Partita)

    @Query("SELECT * FROM partita ORDER BY id ASC")
    fun getPartiteOrderByIdASC(): Flow<List<Partita>>

    @Query("SELECT * FROM partita ORDER BY id DESC")
    fun getPartiteOrderByIdDESC(): Flow<List<Partita>>
}