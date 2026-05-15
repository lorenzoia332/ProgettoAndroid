package com.example.simon


import android.os.Bundle
import android.util.Log

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat


import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import androidx.room3.Room


import com.example.simon.ui.theme.SimonTheme


class MainActivity : ComponentActivity() {

    private lateinit var viewModel: PartitaViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v("DEV" , "onCreate entrato")



        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window,false)


        

        
        setContent {
            Log.v("DEV"  , "setContent: crato il viewModel")


            SimonTheme {

                val state by viewModel.state.collectAsState()


                Log.v("DEV" , "Interfaccia Utente")


                val navController = rememberNavController()



                Scaffold(contentWindowInsets = WindowInsets(0,0,0,0)) { innerPadding ->
                    NavHost(
                        navController = navController, startDestination = "partite",
                        modifier = Modifier.padding((innerPadding))
                    ){
                        composable("gioco"){
                            SchermataPrincipale( onFineClicked = {
                                navController.navigate("partite")
                            } , state , aggPartite = viewModel::onEvent)
                        }
                        composable("partite"){
                            SchermataPartite(
                                state = state,
                                onEvent = viewModel::onEvent,
                                onStartClick =  {
                                    navController.navigate("gioco")
                                })
                        }


                    }
                }


            }
        }
    }

}









