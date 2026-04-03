package com.example.simon


import android.content.Intent
import android.content.res.Configuration
import android.graphics.Insets.add
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.simon.ui.theme.Blue
import com.example.simon.ui.theme.Cyan
import com.example.simon.ui.theme.Green
import com.example.simon.ui.theme.Magenta
import com.example.simon.ui.theme.Red

import com.example.simon.ui.theme.SimonTheme
import com.example.simon.ui.theme.Yellow
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {





        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimonTheme {
                SchermataPrincipale()
            }
        }
    }
}




@Composable
fun SchermataPrincipale() {

    val sequenzaColori = rememberSaveable {
        mutableStateListOf<Int>()
    }
    var livello by rememberSaveable { mutableStateOf(1) }



    var indiceColori by rememberSaveable {
        mutableStateOf(-1)
    }
    var testoEsposto by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(livello) {
        for(i in (1..6)) {
            sequenzaColori.add((1..6).random())
        }
        delay(1000)

        for(colore in sequenzaColori){
            indiceColori = colore
            delay(600)
            indiceColori = -1
            delay(300)
        }
    }


    if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {

        Portrait_layout(indiceColori, testo = testoEsposto,
                    aggTesto = {aggiunta: String -> testoEsposto+=aggiunta},
                    cancTesto = { testoEsposto = it})
    } else {
        Landscape_layout(indiceColori, testo = testoEsposto,
                    aggTesto = {aggiunta: String -> testoEsposto+=aggiunta},
                    cancTesto = { testoEsposto = it})
    }


}






@Composable
fun Portrait_layout(colori: Int, testo: String, aggTesto: (String) -> Unit, cancTesto: (String)-> Unit){



    Column(modifier = Modifier.fillMaxSize()) {

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
            Text(testo)
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
                val myIntent = Intent(this, ActivityWin::class.java)
                startActivity(myIntent)
            },
                modifier = Modifier.padding(horizontal = 10.dp)) {
                Text( stringResource(R.string.fine))
            }

        }
    }
}


    @Composable
fun Landscape_layout(colori: Int, testo: String, aggTesto: (String) -> Unit, cancTesto: (String) -> Unit){
    Row(modifier = Modifier.fillMaxSize()) {
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



        // senconda meta
        Column(modifier = Modifier
            .weight(2f)){

            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .padding(16.dp),
                contentAlignment = Alignment.Center){
                Text(testo)
            }
            Row(modifier = Modifier
                .weight(2f)
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
        animationSpec = tween(durationMillis = 500,)
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
            .size(100.dp)
            .padding(8.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(c, contentColor = Color.White),
        elevation = ButtonDefaults.buttonElevation(elevazioneAnimata)

    ){

    }
}







