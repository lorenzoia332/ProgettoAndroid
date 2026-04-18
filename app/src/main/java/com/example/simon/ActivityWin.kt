package com.example.simon


import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


private val myModifier = Modifier
    .clip(shape = CutCornerShape(8.dp))
    .background(Color(0xAAFFFFFF))
    .border(1.dp,Color.White, CutCornerShape(8.dp))
    .padding(horizontal = 20.dp, vertical = 10.dp)

@Composable
fun SchermataPartite(listaPartite: List<String>){



    if(LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT){
        PartitePortrait(listaPartite)
    }else PartiteLandscape(listaPartite)
}



@Composable
fun PartitePortrait(listaPartite: List<String>){

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .background(Color(0x00, 0x00, 0x00))

    ) {
        items(listaPartite) { partita ->
            Elementi_Lista(partita)

        }

    }


}


@Composable
fun PartiteLandscape(listaPartite: List<String>){

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .background(Color(0x00, 0x00, 0x00))

    ) {
        items(listaPartite) { partita ->
            Elementi_Lista(partita)

        }

    }
}

/*
@Composable
@Preview
fun Previv(){
    PartiteLandscape(listaPartite = List(1,{"A,B,C"}))
}
*/

@Composable
fun Elementi_Lista(s: String){
    Row{



        Text((s.length/2 +1).toString() , modifier = myModifier
            .weight(1f),
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            )

        Text(s , modifier = myModifier
            .weight(3f),
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ),
                    maxLines = 1,
            overflow = TextOverflow.Ellipsis)


    }
}





