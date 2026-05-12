package com.example.simon

import androidx.room3.Database
import androidx.room3.RoomDatabase


@Database(
    entities = [Partita::class],
    version = 1
)
abstract class PartitaDatabase: RoomDatabase() {
    abstract val dao: PartitaDao
}