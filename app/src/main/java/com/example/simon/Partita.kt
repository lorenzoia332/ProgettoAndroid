package com.example.simon

import androidx.room3.Entity
import androidx.room3.PrimaryKey


@Entity
data class Partita(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val rightSeq: String,
    val playerSeq: String
)
