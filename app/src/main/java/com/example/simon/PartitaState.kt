package com.example.simon

/**
 * @param PartitaState è la data calss che definisce lo stato dell' istanza,
 * serve a gestire in maniera pulita gli aggiornamenti dell'UI, in base a i cambiamenti dello stato
 *
 * @param partite è la lista visibile delle partite, inizialmente vuota
 * @param rightSeq è la sequenza corretta del livello superato, inizialmente vuota
 * @param playerSeq è la sequenza immessa dal giocatore, inizialmente vuota
 * @param rightLen è la lunghezza della sequenza di colori corretta, inizialmente 0
 * @param isAddingPartita dice se la partita si sta salvando, inizialemnte false
 * @param sortType definisce il tipo di ordinamento desiderato, inizialmente ID_DESC
 */
data class PartitaState(
    val partite: List<Partita> = emptyList(),
    val rightSeq: String = "",
    val playerSeq: String = "",
    val rightLen: Int = 0,
    val isAddingPartita: Boolean = false,
    val sortType: SortType = SortType.ID_DESC
)