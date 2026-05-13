package com.example.simon

/**
 * @param PartitaEvent è un interfaccia che definisce cosa è possibile fare nel Database
 *
 * @property SavePartita per salvare una partita
 * @property SetRightSeq per impostare  il campo rightSeq
 * @property SetPlayerSeq per impostare  il campo playerSeq
 * @property SetRightLen per impostare il campo rightLen
 * @property ShowDialog
 * @property HideDialog
 * @property SortPartite per definire l'ordine nel quale le partite vengono visualizzate
 * @property DeletePartita per eliminare una partita
 */
sealed interface PartitaEvent {

    object SavePartita: PartitaEvent

    /**
     * @param rightSeq di tipo String
     */
    data class SetRightSeq(val rightSeq: String): PartitaEvent
    /**
     * @param playerSeq di tipo String
     */
    data class SetPlayerSeq(val playerSeq: String): PartitaEvent

    /**
     * @param rightLen di tipo Int
     */
    data class SetRightLen(val rightLen: Int): PartitaEvent
    object ShowDialog: PartitaEvent
    object HideDialog: PartitaEvent

    /**
     * @param sortType di tipo SortType una classe enum definita
     */
    data class SortPartite(val sortType: SortType): PartitaEvent

    /**
     * @param partita di tipo Partita
     */
    data class DeletePartita(val partita: Partita): PartitaEvent
}