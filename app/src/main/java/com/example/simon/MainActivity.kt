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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.simon.ui.theme.SimonTheme


class MainActivity : ComponentActivity() {

    private val database by lazy {
        PartitaDatabase.getDatabase(applicationContext, lifecycleScope)
    }

    private val viewModel by lazy {
        PartitaViewModel(database.partitaDao())
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v("DEV" , "onCreate entrato")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window,false)
        
        setContent {
            Log.v("DEV"  , "setContent: crato il viewModel")

            SimonTheme {
                Log.v("DEV" , "Interfaccia Utente")

                val navController = rememberNavController()

                val state by viewModel.state.collectAsState()

                Scaffold(contentWindowInsets = WindowInsets(0,0,0,0)) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "partite",
                        modifier = Modifier.padding((innerPadding))
                    ){
                        composable("gioco"){
                            SchermataPrincipale(
                                onFineClicked = {
                                    navController.navigate("partite")
                                   // {popUpTo("partite") {inclusive = true}}
                                },
                                state = state,
                                aggPartite = { event -> viewModel.onEvent(event)})
                        }
                        composable("partite"){
                            SchermataPartite(
                                state = state,
                                onEvent = {event -> viewModel.onEvent(event)},
                                onStartClick =  {
                                    navController.navigate("gioco")
                                },
                                onPartitaClick = {i -> i+1})
                        }
                        composable(
                            route = "dettaglio/{idPartita}",
                            arguments = listOf(navArgument("idPartita") {
                                type = NavType.IntType
                            })
                        ) { backStackEntry ->

                            val selectedId =  backStackEntry.arguments?.getInt("idPartita") ?: -1

                            val dettaglio = state.partite.find { it.id == selectedId    }

                            if(dettaglio != null){
                                DettaglioPartita(
                                    state = state,
                                    selectedId,
                                )
                            }
                        }


                    }
                }


            }
        }
    }

}









