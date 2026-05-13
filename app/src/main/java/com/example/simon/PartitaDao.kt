package com.example.simon

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Query
import androidx.room3.Upsert
import kotlinx.coroutines.flow.Flow

/**
 * @param PartitaDao è l'interfaccia che permette di eseguire le operazioni sul database,
 *                      definita come DAO Data Access Object
 *
 * @property upsertPartita permette di inserire/aggiornare una partita
 * @property deletePartita permette di cancellare una partita (non richiesto nella consegna)
 * @property getPartiteOrderByIdASC permette di ottenere una lista di partite dalla più vecchia alla più recente
 * @property getPartiteOrderByIdDESC permette di ottenere una lista di partite dalla più recente alla più vecchia
 * @property getPartiteOrderByLenDESC permette di ottenere una lista di partite dalla più lunga alla più corta
 */
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

    @Query("SELECT * FROM partita ORDER BY rightLen DESC")
    fun getPartiteOrderByLenDESC(): Flow<List<Partita>>
}