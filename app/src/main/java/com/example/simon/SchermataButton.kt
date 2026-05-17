package com.example.simon

import android.content.res.Configuration
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simon.ui.theme.Blue
import com.example.simon.ui.theme.Cyan
import com.example.simon.ui.theme.Green
import com.example.simon.ui.theme.Magenta
import com.example.simon.ui.theme.Red
import com.example.simon.ui.theme.Yellow




/**
 * modificatore per gli sfondi
 */
private val myBackModifier = Modifier
    .background(Color(0xFF000000))

/**
 * modificatore per i TESTI
 */
private val myTextModifier = Modifier
    .clip(shape = CutCornerShape(8.dp))
    .background(Color(0xAAFFFFFF))
    .border(1.dp,Color.White, CutCornerShape(8.dp))
    .padding(horizontal = 20.dp, vertical = 10.dp)

/**
 * funizione composeable per gestire il cambio di configurazione
 *
 */
@Composable
fun SchermataPrincipale(onFineClicked: () -> Unit , state: PartitaState, aggPartite: (PartitaEvent) -> Unit) {

    /*
    Questo if gestisce il cambio di configurazione da portrait a landscape della schermata 1
     */
    if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {

        Portrait_layout(
            state,
            onFineClicked,
            aggPartite)
    } else {
        Landscape_layout(
            state,
            onFineClicked,
            aggPartite)
    }


}


/**
 * Layout per la versione PORTRAIT
 */
@Composable
fun Portrait_layout(state: PartitaState, onFineClicked: () -> Unit, aggPartite: (PartitaEvent) -> Unit){



    Column(modifier = myBackModifier.fillMaxSize()) {

        Box(modifier = Modifier
            .weight(2f)
            .fillMaxWidth()
            .padding(16.dp)){
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(6){ index ->
                    val idButton = index+1
                    Elementi_Griglia(
                        id = idButton,
                        state = state,
                        isActive = (state.activeButtonIndex == idButton),
                        onPress = {carattere ->
                            aggPartite(PartitaEvent.PressedButton(carattere))
                        })
                }

            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .padding(16.dp),
            contentAlignment = Alignment.Center){
            Text(state.playerSeq,
                modifier = myTextModifier,
                color = Color.Black,
                style = TextStyle(
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ))
        }
        Row(modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
            .padding(16.dp),
            horizontalArrangement = Arrangement.Absolute.Center
        ){
            //INIZIO PARTITA
            Button(onClick = {
                aggPartite(PartitaEvent.StartPartita)},
                enabled = !state.isPartitaStarted,
                modifier = Modifier.padding(horizontal = 10.dp)) {
                Icon(painter = painterResource(R.drawable.play_arrow_24px),
                    contentDescription = stringResource(R.string.inizia))
            }

            //PAUSA/RIPRENDI
            Button(onClick = {
                aggPartite(PartitaEvent.PausePartita)},
                enabled = state.isPartitaStarted,
                modifier = Modifier.padding(horizontal = 10.dp)) {
                Icon(painter = painterResource(if (state.isPartitaOnPause){
                    R.drawable.resume_24px
                }else{
                    R.drawable.pause_24px
                }),
                    contentDescription = stringResource(if (state.isPartitaOnPause){
                        R.string.riprendi
                    }else{
                        R.string.pausa
                    })
                )}
            //FINE PARTITA
            Button(onClick = {
                aggPartite(PartitaEvent.EndPartita)
                onFineClicked()},
                enabled = state.isPartitaStarted,
                modifier = Modifier.padding(horizontal = 10.dp)) {
                Icon(painter = painterResource(R.drawable.stop_24px),
                    contentDescription = stringResource(R.string.fine))
                }


        }
    }
}

/**
 * funzione per il layout Landscape
 */
@Composable
fun Landscape_layout(state: PartitaState, onFineClicked: () -> Unit, aggPartite: (PartitaEvent) -> Unit){
    Row(modifier = myBackModifier
        .safeDrawingPadding() // per evitare che la fotocamera interna del dispositivo sovrapponga le bande nere alla grafica dell'UI
        .fillMaxWidth()
        .fillMaxHeight()) {
        //box a sx con la griglia occupa mezza schermata
        Box(modifier = Modifier
            .weight(2f)
            .fillMaxHeight()
            .padding(16.dp)){

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(6){ index ->
                    val idButton = index+1
                    Elementi_Griglia(
                        id = idButton,
                        state = state,
                        isActive = (state.activeButtonIndex == idButton),
                        onPress = {carattere ->
                            aggPartite(PartitaEvent.PressedButton(carattere))
                        })
                }
            }

        }


        // senconda meta
        Column(
            modifier = Modifier
                .weight(2f)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(state.playerSeq,
                    modifier = myTextModifier,
                    color = Color.Black,
                    style = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ))
            }
            Row(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Absolute.Center
            ) {

                //INIZIO PARTITA
                Button(onClick = {
                    aggPartite(PartitaEvent.SavePartita)},
                    enabled = !state.isPartitaStarted,
                    modifier = Modifier.padding(horizontal = 10.dp)) {
                    Icon(painter = painterResource(R.drawable.play_arrow_24px),
                        contentDescription = stringResource(R.string.inizia))
                }

                //PAUSA/RIPRENDI
                Button(onClick = {
                    aggPartite(PartitaEvent.PausePartita)},
                    enabled = state.isPartitaStarted,
                    modifier = Modifier.padding(horizontal = 10.dp)) {
                    Icon(painter = painterResource(if (state.isPartitaOnPause){
                        R.drawable.resume_24px
                    }else{
                        R.drawable.pause_24px
                    }),
                        contentDescription = stringResource(if (state.isPartitaOnPause){
                            R.string.riprendi
                        }else{
                            R.string.pausa
                        })
                    )}
                //FINE PARTITA
                Button(onClick = {
                    aggPartite(PartitaEvent.EndPartita)
                    onFineClicked()},
                    enabled = state.isPartitaStarted,
                    modifier = Modifier.padding(horizontal = 10.dp)) {
                    Icon(painter = painterResource(R.drawable.stop_24px),
                        contentDescription = stringResource(R.string.fine))
                }


            }



        }
    }
}

/**
 * Funzione che definisce gli elementi che formano i pulsanti colorati
 * ogni pulsante ha un:
 * @param id un valore Int che lo identifica
 * @param isActive che dice al pulsante se è attivo, utile per un aniamzione
 * @param onPress definisce l'azione alla pressione del tasto
 */
@Composable
fun Elementi_Griglia(id :Int, state : PartitaState,  isActive: Boolean, onPress: (Char) -> Unit) {
    var c = Color(0xFF000000)
    var s = ' '

    val percentualeAngolo by animateIntAsState(targetValue = if(isActive) 15 else 50,
        animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing) )

    val formaAnimata = RoundedCornerShape(percentualeAngolo)

    val elevazioneAnimata by animateDpAsState(
        targetValue = if(isActive) 12.dp else 2.dp,
        animationSpec = tween(durationMillis = 500)
    )



    when (id) {
        1 -> {
            c = Red
            s = 'R'
        }

        2 -> {
            c = Green
            s = 'G'
        }

        3 -> {
            c = Blue
            s = 'B'
        }

        4 -> {
            c = Magenta
            s = 'M'
        }

        5 -> {
            c = Yellow
            s = 'Y'
        }

        6 -> {
            c = Cyan
            s = 'C'
        }
    } // assegnamento dei valori di c e s per i rispettivi colori
    Button(onClick = {
        if(!state.cpuPhase){
            onPress(s)
        }
    },
        shape = formaAnimata,
        modifier = Modifier
            .size(90.dp)
            .border(1.dp, Color.White, formaAnimata)
            .padding(8.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(c, contentColor = Color.White),
        elevation = ButtonDefaults.buttonElevation(elevazioneAnimata)

    ){

    }
}

@Preview(showBackground = true, name = "Anteprima Verticale (Portrait)", device = "spec:width=411dp,height=891dp")
@Preview(showBackground = true, name = "Anteprima Orizzontale (Landscape)", device = "spec:width=891dp,height=411dp")
@Composable
fun SchermataPrincipalePreview() {
    // Simulazione di una partita attiva per testare la resa visiva dei controlli
    val mockupState = PartitaState(
        isPartitaStarted = false,
        isPartitaOnPause = false,
        playerSeq = "R-G-B",
        activeButtonIndex = 0, // Illumina il tasto Rosso per verificare l'animazione di transizione
        cpuPhase = false
    )
    SchermataPrincipale(
        onFineClicked = {},
        state = mockupState,
        aggPartite = {}
    )
}
