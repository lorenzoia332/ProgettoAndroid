package com.example.simon

sealed interface PartitaEvent {

    object SavePartita: PartitaEvent
    data class SetRightSeq(val rightSeq: String): PartitaEvent
    data class SetPlayerSeq(val playerSeq: String): PartitaEvent
    object ShowDialog: PartitaEvent
    object HideDialog: PartitaEvent
    data class SortPartite(val sortType: SortType): PartitaEvent
    data class DeletePartita(val partita: Partita): PartitaEvent
}