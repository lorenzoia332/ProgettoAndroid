package com.example.simon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 *@param PartitaViewModel è la calsse che definisce il ViewModel,
 *  il comportamento dello stato e l' implementazione delle funzionalità
 *
 * @param _sortType è una variabile per salvare l'attuale ordinamento di visualizzazione
 * @param _partite è la lista di partite giàa ordianta sencondo il sortType
 * @param _state è una lista degli stati della partita
 *
 * @param state è un osservatore/ascoltatore attivo dei cambiamenti
 * @property onEvent definisce il modo di rispondere ad un dato evento
 */

@OptIn(ExperimentalCoroutinesApi::class)
class PartitaViewModel(
    private val dao: PartitaDao
): ViewModel() {


    private val _sortType = MutableStateFlow(SortType.ID_DESC)

    private val _partite = _sortType
        .flatMapLatest { sortType ->
            when(sortType){
                SortType.ID_ASC -> dao.getPartiteOrderByIdASC()
                SortType.ID_DESC -> dao.getPartiteOrderByIdDESC()
                SortType.LEN_DESC -> dao.getPartiteOrderByLenDESC()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(PartitaState())


    /**
     * Ci serve per cambiare i valori di partite e sortType quando incorre un cambiamento
     */
    val state = combine(_state, _sortType, _partite){state, sortType, partite ->
        state.copy(
            partite = partite,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PartitaState())


    fun onEvent(event: PartitaEvent){
        when(event){
            is PartitaEvent.DeletePartita ->{
                viewModelScope.launch {
                    dao.deletePartita(event.partita)
                }

            }
            is PartitaEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingPartita = false
                ) }
            }
            is PartitaEvent.SavePartita -> {

                val rightSeq = state.value.rightSeq
                val playerSeq = state.value.playerSeq
                val rightLen = state.value.rightLen

                if(rightSeq.isBlank() || playerSeq.isBlank()){
                    return
                }

                val partita  = Partita(
                    rightSeq = rightSeq,
                    playerSeq = playerSeq,
                    rightLen = rightLen
                )
                viewModelScope.launch {
                    dao.upsertPartita(partita)
                }
                _state.update { it.copy(
                    isAddingPartita = false,
                    rightSeq = "",
                    playerSeq = "",
                    rightLen = 0

                ) }


            }
            is PartitaEvent.SetPlayerSeq -> {
                _state.update { it.copy(
                    playerSeq = event.playerSeq
                ) }
            }
            is PartitaEvent.SetRightSeq -> {
                _state.update { it.copy(
                    rightSeq = event.rightSeq
                ) }
            }
            PartitaEvent.ShowDialog -> {
                _state.update {it.copy(
                    isAddingPartita = true
                ) }
            }
            is PartitaEvent.SortPartite -> {
                _sortType.value = event.sortType
            }

            is PartitaEvent.SetRightLen -> {
                _state.update {it.copy(
                    rightLen = event.rightLen
                ) }
            }
        }
    }
}