package com.example.simon


import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
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
fun PrevSchermata(){
    val p = Partita(rightSeq = "A-B-C", playerSeq = "A-C-D")
    val ps = PartitaState(listOf(p), rightSeq = "A-B-C", playerSeq = "A-C-D")
    PartitePortrait(ps,  { })

}


private val myModifier = Modifier
    .clip(shape = CutCornerShape(8.dp))
    .background(Color(0xAAFFFFFF))
    .border(1.dp, Color.White, CutCornerShape(8.dp))
    .padding(horizontal = 20.dp, vertical = 10.dp)

@Composable
fun SchermataPartite(state: PartitaState, onStartClick: () -> Unit){



    if(LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT){
        PartitePortrait(state , onStartClick)
    }else PartiteLandscape(state , onStartClick)
}




@Composable
fun PartitePortrait(state: PartitaState, onStartClick: () -> Unit){
    Scaffold(Modifier
        .background(Color.Black), {},{},{},
        {
            FloatingActionButton( onStartClick,
                containerColor = Color.White,
                contentColor = Color.Black,
                shape = FloatingActionButtonDefaults.shape) {

                Icon(painter = painterResource(R.drawable.play_arrow_24px),
                    contentDescription = "")

        }
        }, floatingActionButtonPosition = FabPosition.End) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .background(Color.Black)
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .fillMaxSize()

        , contentPadding = innerPadding) {
            items(state.partite) { partita ->
                Elementi_Lista(partita.rightSeq, partita.playerSeq)

            }

        }


    }
}


@Composable
fun PartiteLandscape(state: PartitaState , onStartClick: () -> Unit){

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .background(Color(0x00, 0x00, 0x00))

    ) {
        items(state.partite) { partita ->
            Elementi_Lista(partita.rightSeq, partita.playerSeq
            )

        }

    }

    FloatingActionButton(modifier = Modifier
        .safeContentPadding(),
        onClick = onStartClick
    ) { }
}


@Composable
fun Elementi_Lista(s: String , sg: String){
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
    i = if(s.length == sg.length){
        (s.length/2 + s.length%2) // stesso lunghezza e caratteri
    }else{
        (s.length/2 + s.length%2) - 1
    }


    Row{



        Text(i.toString() , modifier = myModifier
            .weight(1f),
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            )

        Text(t , modifier = myModifier
            .weight(3f),
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ),
                    maxLines = 1,//definisce il numero di Linee di testo, massimo 1 in questo caso
            overflow = TextOverflow.Ellipsis) // gestisce le stringe troppo lunghe troncando la stringa e inserendo "..."


    }

}





