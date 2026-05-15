package com.example.simon

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.SQLiteConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
    abstract fun partitaDao() : PartitaDao

    companion object{

        @Volatile
        private var INSTANCE: PartitaDatabase? = null



        fun getDatabase(context: Context,
                        scope: CoroutineScope
        ): PartitaDatabase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PartitaDatabase::class.java,
                    "word_database"

                ).fallbackToDestructiveMigration()
                    .addCallback(PartitaDatabaseCallback(scope))
                    .build()

                INSTANCE= instance
                instance
            }
        }
        private class PartitaDatabaseCallback(
            private val scope: CoroutineScope): Callback(){
            override suspend fun onOpen(connection: SQLiteConnection) {
                super.onOpen(connection)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO){
                        populateDatabase(database.partitaDao() )

                    }
                }
            }


            fun populateDatabase(partitaDao: PartitaDao){
                partitaDao.insertPartita(Partita(
                    rightSeq = "A-B-C",
                    playerSeq = "A-B-C",
                    rightLen = 3
                ))


            }
        }

    }
}