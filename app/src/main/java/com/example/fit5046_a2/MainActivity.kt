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
                Log.d("Navigation", "Navigating to history")
                currentScreen = "history" 
            }
        )
        "history" -> WasteHistoryScreen(
            onBackClick = { 
                Log.d("Navigation", "Navigating to home")
                currentScreen = "home" 
            },
            onAddItemClick = { 
                Log.d("Navigation", "Navigating to add item")
                currentScreen = "add" 
            },
            onItemClick = { item ->
                Log.d("Navigation", "Navigating to details for ${item.name}")
                currentScreen = "details" 
            }
        )
        "add" -> AddWasteItemScreen(
            onBackClick = { 
                Log.d("Navigation", "Returning to history from add")
                currentScreen = "history" 
            },
            onSaveClick = { 
                Log.d("Navigation", "Saved item, returning to history")
                currentScreen = "history" 
            }
        )
        "details" -> WasteItemDetailsScreen(
            onBackClick = { 
                Log.d("Navigation", "Returning to history from details")
                currentScreen = "history" 
            },
            onDeleteClick = { 
                Log.d("Navigation", "Deleted item, returning to history")
                currentScreen = "history" 
            }
        )
    }
}
