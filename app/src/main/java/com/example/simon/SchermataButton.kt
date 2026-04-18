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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simon.ui.theme.Blue
import com.example.simon.ui.theme.Cyan
import com.example.simon.ui.theme.Green
import com.example.simon.ui.theme.Magenta
import com.example.simon.ui.theme.Red
import com.example.simon.ui.theme.Yellow
import kotlinx.coroutines.delay



private val myBackModifier = Modifier
    .background(Color(0xFF000000))
private val myTextModifier = Modifier
    .clip(shape = CutCornerShape(8.dp))
    .background(Color(0xAAFFFFFF))
    .border(1.dp,Color.White, CutCornerShape(8.dp))
    .padding(horizontal = 20.dp, vertical = 10.dp)
@Composable
fun SchermataPrincipale(onFineClicked: () -> Unit , partite: List<String>, aggPartite: (String) -> Unit) {

    val sequenzaColori = rememberSaveable {
        mutableStateListOf<Int>()
    }
    var livello by rememberSaveable { mutableIntStateOf(1) }



    var indiceColori by rememberSaveable {
        mutableIntStateOf(-1)
    }
    var testoEsposto by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(livello) {

        repeat(6) { sequenzaColori.add((1..6).random()) }

        delay(1000)

        for(colore in sequenzaColori){
            indiceColori = colore
            delay(600)
            indiceColori = -1
            delay(300)
        }
        testoEsposto = ""
    }


    if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {

        Portrait_layout(indiceColori, testo = testoEsposto,
            aggTesto = {aggiunta: String -> testoEsposto+=aggiunta},
            cancTesto = { testoEsposto = it},
            onFineClicked, aggPartite)
    } else {
        Landscape_layout(indiceColori, testo = testoEsposto,
            aggTesto = {aggiunta: String -> testoEsposto+=aggiunta},
            cancTesto = { testoEsposto = it},
            onFineClicked, aggPartite)
    }


}






@Composable
fun Portrait_layout(colori: Int, testo: String, aggTesto: (String) -> Unit, cancTesto: (String)-> Unit ,onFineClicked: () -> Unit, aggPartite: (String) -> Unit){



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
                items(1){ Elementi_Griglia(1,(colori == 1),testo = testo, aggTesto)
                }
                items(1){ Elementi_Griglia(2,(colori == 2),testo = testo, aggTesto)
                }
                items(1){ Elementi_Griglia(3,(colori == 3),testo = testo, aggTesto)
                }
                items(1){ Elementi_Griglia(4,(colori == 4),testo = testo, aggTesto)
                }
                items(1){ Elementi_Griglia(5,(colori == 5),testo = testo, aggTesto)
                }
                items(1){ Elementi_Griglia(6,(colori == 6),testo = testo, aggTesto)
                }
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .padding(16.dp),
            contentAlignment = Alignment.Center){
            Text(testo,
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

            Button(onClick = {
                cancTesto("")

            },
                modifier = Modifier.padding(horizontal = 10.dp)) {
                Text(stringResource(R.string.canc))
            }

            Button(onClick = {

                //termino la partita solo se esiste una sequenza di colori da passare altrimenti non faccio terminare
                if(!testo.isEmpty()) {
                    aggPartite(testo)  //passo la stringa
                    cancTesto("")       // faccio il 'clear' del testo
                    onFineClicked()     // vado alla nuova schermata
                }
            }, modifier = Modifier.padding(horizontal = 10.dp)) {
                Text( stringResource(R.string.fine))
            }

        }
    }
}


@Composable
fun Landscape_layout(colori: Int, testo: String, aggTesto: (String) -> Unit, cancTesto: (String) -> Unit, onFineClicked: () -> Unit, aggPartite: (String) -> Unit){
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
                items(1) {
                    Elementi_Griglia(1, (colori == 1), testo = testo, aggTesto)
                }
                items(1) {
                    Elementi_Griglia(2, (colori == 2), testo = testo, aggTesto)
                }
                items(1) {
                    Elementi_Griglia(3, (colori == 3), testo = testo, aggTesto)
                }
                items(1) {
                    Elementi_Griglia(4, (colori == 4), testo = testo, aggTesto)
                }
                items(1) {
                    Elementi_Griglia(5, (colori == 5), testo = testo, aggTesto)
                }
                items(1) {
                    Elementi_Griglia(6, (colori == 6), testo = testo, aggTesto)
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
                Text(testo,
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

            Button(onClick = {
                    cancTesto("")
                },
                    modifier = Modifier.padding(horizontal = 10.dp)) {
                    Text(stringResource(R.string.canc))
                }

                Button(onClick = {
                    if(!testo.isEmpty()) {
                        aggPartite(testo) // salvo il contenuto da passare
                        cancTesto("")   // pulisco la casella di testo
                        onFineClicked()  //passo alla schermata di elenco delle partite
                    }
                },
                    modifier = Modifier.padding(horizontal = 10.dp)) {
                    Text( stringResource(R.string.fine))
                }

            }



        }
    }
}


@Composable
fun Elementi_Griglia(id :Int, isActive: Boolean, testo: String, onUpdate: (String) -> Unit) {
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
        if(testo.isEmpty()){
            onUpdate("$s")
        }else {
            onUpdate(",$s")
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
