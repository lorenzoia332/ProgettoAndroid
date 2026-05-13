package com.example.simon

import androidx.room3.Database
import androidx.room3.RoomDatabase

/**
 * @param PartitaDatabase è una classe astratta che definsce l'accesso principale tra l' applicazione e il database,
 *                        funge da ponte
 *@param dao è la variabile che permette di interfacciarsi al database
 *           tramite le funzioni di "query" definite nella classe PartitaDao
 */
@Database(
    entities = [Partita::class],
    version = 1
)
abstract class PartitaDatabase: RoomDatabase() {
    abstract val dao: PartitaDao
}