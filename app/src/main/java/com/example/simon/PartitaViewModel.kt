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

@OptIn(ExperimentalCoroutinesApi::class)
class PartitaViewModel(
    private val dao: PartitaDao
): ViewModel() {


    private val _sortType = MutableStateFlow(SortType.ASC)

    private val _partite = _sortType
        .flatMapLatest { sortType ->
            when(sortType){
                SortType.ASC -> dao.getPartiteOrderByIdASC()
                SortType.DESC -> dao.getPartiteOrderByIdDESC()
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
            PartitaEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingPartita = false
                ) }
            }
            PartitaEvent.SavePartita -> {

                val rightSeq = state.value.rightSeq
                val playerSeq = state.value.playerSeq

                if(rightSeq.isBlank() || playerSeq.isBlank()){
                    return
                }

                val partita  = Partita(
                    rightSeq = rightSeq,
                    playerSeq = playerSeq
                )
                viewModelScope.launch {
                    dao.upsertPartita(partita)
                }
                _state.update { it.copy(
                    isAddingPartita = false,
                    rightSeq = "",
                    playerSeq = ""
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
        }
    }
}