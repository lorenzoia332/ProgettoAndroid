package com.example.simon


import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier


import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController


import com.example.simon.ui.theme.SimonTheme




class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {





        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimonTheme {

                val navController = rememberNavController()
                var listaPartite = rememberSaveable {mutableListOf<String>()}

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
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









