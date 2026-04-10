package com.example.simon

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp






@Composable
fun SchermataPartite(listaPartite: List<String>){

    //var variabili = mutableListOf("A,B,C" , "B,A,C" , "C,A,S,B" , "D,E,F"
    //,"sasa" ,"dfudn" ,"dfdggr")

    Column(modifier = Modifier
        .fillMaxSize()) {

        LazyColumn(modifier = Modifier
            .weight(3f)
            .fillMaxWidth()
            ) {
            item(6){
                for(partita in listaPartite){
                    Elementi_Lista(partita)
                }


            }
        }

    }
}


@Composable
fun Elementi_Lista(s: String){
    Row{
        Text((s.length/2 +1).toString() , modifier = Modifier
            .weight(1f)
            .padding(8.dp))

        Text(s , modifier = Modifier
            .weight(3f)
            .padding(8.dp))
    }
}





