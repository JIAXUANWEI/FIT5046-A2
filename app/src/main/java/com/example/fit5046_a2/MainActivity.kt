package com.example.fit5046_a2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.fit5046_a2.ui.screens.*
import com.example.fit5046_a2.ui.theme.FIT5046A2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FIT5046A2Theme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    var currentScreen by remember { mutableStateOf("history") }
    
    when (currentScreen) {
        "home" -> ProfileScreen(
            onHistoryClick = { 
                currentScreen = "history" 
                Log.d("Navigation", "Current screen: $currentScreen")
            }
        )
        "history" -> WasteHistoryScreen(
            onBackClick = { 
                currentScreen = "home" 
                Log.d("Navigation", "Current screen: $currentScreen")
            },
            onAddItemClick = { 
                currentScreen = "add" 
                Log.d("Navigation", "Current screen: $currentScreen")
            },
            onItemClick = { item ->
                currentScreen = "details" 
                Log.d("Navigation", "Viewing details for ${item.name}. Current screen: $currentScreen")
            }
        )
        "add" -> AddWasteItemScreen(
            onBackClick = { 
                currentScreen = "history" 
                Log.d("Navigation", "Current screen: $currentScreen")
            },
            onSaveClick = { 
                currentScreen = "history" 
                Log.d("Navigation", "Current screen: $currentScreen")
            }
        )
        "details" -> WasteItemDetailsScreen(
            onBackClick = { 
                currentScreen = "history" 
                Log.d("Navigation", "Current screen: $currentScreen")
            },
            onDeleteClick = { 
                currentScreen = "history" 
                Log.d("Navigation", "Current screen: $currentScreen")
            }
        )
    }
}
