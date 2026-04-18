package com.example.simon


import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat


import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController


import com.example.simon.ui.theme.SimonTheme




class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {





        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window,false)
        setContent {
            SimonTheme {

                val navController = rememberNavController()
                val listaPartite = rememberSaveable(
                    saver = listSaver(
                        save = { it.toList() },
                        restore = {it.toMutableStateList()}
                    )
                ) {
                    mutableStateListOf<String>()
                }

                Scaffold(contentWindowInsets = WindowInsets(0,0,0,0)) { innerPadding ->
                    NavHost(
                        navController = navController, startDestination = "principale",
                        modifier = Modifier.padding((innerPadding))
                    ){
                        composable("principale"){
                            SchermataPrincipale( onFineClicked = {
                                navController.navigate("partite")
                            } , listaPartite , aggPartite = { partita: String-> listaPartite.add(partita)})
                        }
                        composable("partite"){
                            SchermataPartite(listaPartite )
                        }
                    }
                }


            }
        }
    }
}









