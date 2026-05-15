package com.example.simon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import kotlin.random.Random

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

    private val coloriGioco = listOf('R' ,'G' ,'B' ,'M', 'Y', 'C')


    /**
     * Ci serve per cambiare i valori di partite e sortType quando incorre un cambiamento
     */
    val state = combine(_state, _sortType, _partite){state, sortType, partite ->
        state.copy(
            partite = partite,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PartitaState())


    init {
        onEvent(PartitaEvent.StartLivello)
    }

    fun onEvent(event: PartitaEvent){
        when(event) {
            is PartitaEvent.StartLivello -> {
                viewModelScope.launch {
                    val newCarattere = coloriGioco[Random.nextInt(coloriGioco.size)]

                    val tempSeq = _state.value.rightSeq
                    val newRightSeq = if (tempSeq.isEmpty()) {
                        "$newCarattere"
                    } else {
                        "$tempSeq-$newCarattere"
                    }

                    _state.update {
                        it.copy(
                            rightSeq = newRightSeq,
                            playerSeq = "",
                            rightLen = newRightSeq.split("-").size,
                            cpuPhase = true
                        )
                    }


                    playSequence(newRightSeq)
                }
            }

            is PartitaEvent.TastoGiocoPremuto -> {
                if (_state.value.cpuPhase) return

                val pressChar = event.carattere
                val listaRightSeq = _state.value.rightSeq.split("-")

                val listaPlayerTemp = if (_state.value.playerSeq.isEmpty()) {
                    emptyList()
                } else {
                    _state.value.playerSeq.split("-")
                }

                val tempIndex = listaPlayerTemp.size


                //controllo che  ogni tasto premuto sia corretto rispetto alla sequenza del livello altrimenti termino
                if (pressChar.toString() != listaRightSeq[tempIndex]) {
                    onEvent(PartitaEvent.ShowDialog)
                    return
                }

                val newPlayerSeq = if (_state.value.playerSeq.isEmpty()) {
                    "$pressChar"
                } else {
                    "${_state.value.playerSeq}-$pressChar"
                }

                _state.update { it.copy(playerSeq = newPlayerSeq) }


                val checkRight = newPlayerSeq.split("-")
                if (checkRight.size == listaRightSeq.size) {
                    viewModelScope.launch {
                        delay(600)
                        onEvent(PartitaEvent.StartLivello)
                    }
                }
            }

            is PartitaEvent.ResetGioco -> {
                _state.update {
                    it.copy(
                        rightSeq = "",
                        playerSeq = "",
                        rightLen = 0,
                        activeButtonIndex = -1,
                        cpuPhase = false
                    )
                }
                onEvent(PartitaEvent.StartLivello)
            }


            is PartitaEvent.DeletePartita -> {
                viewModelScope.launch {
                    dao.deletePartita(event.partita)
                }

            }

            is PartitaEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingPartita = false
                    )
                }
            }

            is PartitaEvent.SavePartita -> {

                val rightSeq = state.value.rightSeq
                val playerSeq = state.value.playerSeq
                val rightLen = state.value.rightLen

                if (rightSeq.isBlank() || playerSeq.isBlank()) {
                    return
                }

                val partita = Partita(
                    rightSeq = rightSeq,
                    playerSeq = playerSeq,
                    rightLen = rightLen
                )
                viewModelScope.launch {
                    dao.insertPartita(partita)
                }
                _state.update {
                    it.copy(
                        isAddingPartita = false,
                        rightSeq = "",
                        playerSeq = "",
                        rightLen = 0

                    )
                }

                onEvent(PartitaEvent.StartLivello)


            }

            is PartitaEvent.SetPlayerSeq -> {
                _state.update {
                    it.copy(
                        playerSeq = event.playerSeq
                    )
                }
            }

            is PartitaEvent.SetRightSeq -> {
                _state.update {
                    it.copy(
                        rightSeq = event.rightSeq
                    )
                }
            }

            PartitaEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAddingPartita = true
                    )
                }
            }

            is PartitaEvent.SortPartite -> {
                _sortType.value = event.sortType
            }

            is PartitaEvent.SetRightLen -> {
                _state.update {
                    it.copy(
                        rightLen = event.rightLen
                    ) }
            }
        }
    }

    private suspend fun playSequence(sequenza : String){

        if (sequenza.isEmpty())return

        val elementi = sequenza.split("-")
        delay(1000)

        for(e in elementi){
            if(e.isBlank())continue
            val c = e[0]
            val idTasto = cToId(c)

            _state.update {it.copy(activeButtonIndex = idTasto)}

            delay(600)

            _state.update { it.copy(activeButtonIndex = -1) }

            delay(250)
        }

        _state.update { it.copy(cpuPhase = false) }

    }

    private fun cToId(car: Char) : Int{
        return when(car){
            'R' -> 1
            'G' -> 2
            'B' -> 3
            'M' -> 4
            'Y' -> 5
            'C' -> 6
            else -> -1
        }

    }
}