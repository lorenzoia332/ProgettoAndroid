package com.example.simon

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val detModifier = Modifier.background(Color.White)

@Composable
fun DettaglioPartita(state: PartitaState, id: Int){
    val sc = state.partite[id].rightSeq.split("-")



    val l = sc.map{it.toString() }.runningReduce { acc, c ->
        "$acc-$c"
    }

    Scaffold(modifier = detModifier.fillMaxSize(),


    ) { padding ->

        Spacer(detModifier.padding(padding))
        LazyColumn(detModifier.padding(padding),
            contentPadding = padding,
            horizontalAlignment = Alignment.CenterHorizontally) {

            item(1){

                Box(detModifier){
                    Text(text = "Partita ${id}",
                        modifier = detModifier
                            .padding(padding)
                            .fillMaxWidth()
                            .height(20.dp)

                        ,
                        color = Color.Black)

                }
            }

            items(items = l){ c ->

                ElementoDettaglioPartita((c.length/2 +c.length%2) ,c, state.partite[id].playerSeq)





            }

        }

    }
}


@Composable
fun ElementoDettaglioPartita(len: Int,sc: String, sg: String){

    var i = 0
    val t= buildAnnotatedString {
        for( c in sg){
            if(sc[i] == c){
                append(sc[i])
            }else{
                withStyle( style = SpanStyle(color = Color.Red , fontWeight = FontWeight.Bold)){
                    append(sc[i])
                }
            }

            i++
        }
    }

    Row{



        Text(len.toString() , modifier = Modifier
            .clip(shape = CutCornerShape(8.dp))
            .background(Color(0xAAFFFFFF))
            .border(1.dp, Color.Black, CutCornerShape(8.dp))
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .weight(1f),
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

        )

        Text(t , modifier =  Modifier
            .clip(shape = CutCornerShape(8.dp))
            .background(Color(0xAAFFFFFF))
            .border(1.dp, Color.Black, CutCornerShape(8.dp))
            .padding(horizontal = 20.dp, vertical = 10.dp)
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

@Preview
@Composable
fun PrevDett(){

    DettaglioPartita(PartitaState(partite = listOf(Partita(rightSeq = "A-B-C" , playerSeq = "A-B-D", rightLen = 3))),0)
}