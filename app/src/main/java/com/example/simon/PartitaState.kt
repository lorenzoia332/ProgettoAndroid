package com.example.simon

data class PartitaState(
    val partite: List<Partita> = emptyList(),
    val rightSeq: String = "",
    val playerSeq: String = "",
    val isAddingPartita: Boolean = false,
    val sortType: SortType = SortType.ASC
)