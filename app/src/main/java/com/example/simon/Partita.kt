package com.example.simon

import androidx.room3.Entity
import androidx.room3.PrimaryKey

/**
 * @param Partita la data class che definisce la tabella delle partite e i suoi campi
 *
 * @param id è un dato di tipo Int che viene auto generato, definisce l' deintificatore univoco di ogni istanza
 * @param rightSeq è un dato di tipo String che definisce la sequenza corretta massima inserita dall'utente
 * @param playerSeq è un dato di tipo String che definisce l'ultima sequenza inserita dall'utente
 * @param rightLen è un dato di tipo Int che definisce la lunghezza della sequenza corretta
 *
 * Dato che la sequenza corretta è del tipo "A-B-C" la lunghezza deve valere 3, cioè non è la semplice lenght di una stringa
 */
@Entity
data class Partita(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val rightSeq: String,
    val playerSeq: String,
    val rightLen: Int
)
