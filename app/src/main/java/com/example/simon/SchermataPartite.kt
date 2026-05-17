package com.example.simon


import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview
@Composable
fun PrevSchermata() {
    val p = Partita(rightSeq = "A-B-C", playerSeq = "A-C-D", rightLen = 1)
    val ps = PartitaState(listOf(p), rightSeq = "A-B-C", playerSeq = "A-C-D")
    PartitePortrait(ps, {}, {}, onPartitaClick = {} )

}

private val myModifier = Modifier
    .clip(shape = CutCornerShape(8.dp))
    .background(Color(0xAAFFFFFF))
    .border(1.dp, Color.White, CutCornerShape(8.dp))
    .padding(horizontal = 20.dp, vertical = 10.dp)

@Composable
fun SchermataPartite(state: PartitaState, onEvent: (PartitaEvent) -> Unit, onStartClick: () -> Unit , onPartitaClick: (Int) -> Unit) {

    if(LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT){
        PartitePortrait(state , onEvent, onStartClick,  onPartitaClick )
    }else PartiteLandscape(state , onEvent, onStartClick, onPartitaClick )
}




@Composable
fun PartitePortrait(state: PartitaState, onEvent: (PartitaEvent) -> Unit, onStartClick: () -> Unit, onPartitaClick: (Int) -> Unit){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Black,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onStartClick,
                containerColor = Color.White,
                contentColor = Color.Black,
                shape = FloatingActionButtonDefaults.shape) {

                Icon(painter = painterResource(R.drawable.play_arrow_24px),
                    contentDescription = "")

        }
        }, floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .windowInsetsPadding(WindowInsets.safeDrawing),
            contentPadding = innerPadding
        ) {
            items(state.partite) { partita ->
                Elementi_Lista(partita, onClick = {onPartitaClick(partita.id)} )
            }
        }
    }
}


@Composable
fun PartiteLandscape(state: PartitaState, onEvent: (PartitaEvent) -> Unit, onStartClick: () -> Unit, onPartitaClick: (Int) -> Unit){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Black,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onStartClick,
                containerColor = Color.White,
                contentColor = Color.Black,
                modifier = Modifier.safeContentPadding()) {

                Icon(painter = painterResource(R.drawable.play_arrow_24px),
                    contentDescription = "")

            }
        }, floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .background(Color.Black),
            contentPadding = innerPadding
            ) {
            items(state.partite) { partita ->
                Elementi_Lista(partita, onClick = {onPartitaClick(partita.id)})

            }

        }

    }
}


@Composable
fun Elementi_Lista(partita: Partita , onClick:() -> Unit){
    val s = partita.rightSeq
    val sg = partita.playerSeq
    var i = 0
    val t= buildAnnotatedString {
        for( c in sg){
            if(s[i] == c){
                append(s[i])
            }else{
                withStyle( style = SpanStyle(color = Color.Red , fontWeight = FontWeight.Bold)){
                    append(s[i])
                }
            }

            i++
        }
    }

    val punteggio = partita.rightLen


    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable {  onClick() },
        Arrangement.Center,
        Alignment.CenterVertically) {


        Text(
            text = punteggio.toString(),
            modifier = myModifier
                .weight(1.2f),
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            ),
            color = Color.Black

        )

        Text(
            text = t,
            modifier = myModifier
                .weight(3f),
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ),
            maxLines = 1,//definisce il numero di Linee di testo, massimo 1 in questo caso
            overflow = TextOverflow.Ellipsis,
            color = Color.Black
        ) // gestisce le stringe troppo lunghe troncando la stringa e inserendo "..."
    }
}






