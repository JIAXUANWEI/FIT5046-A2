package com.example.fit5046_a2

import android.os.Bundle
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
            onHistoryClick = { currentScreen = "history" }
        )
        "history" -> WasteHistoryScreen(
            onBackClick = { currentScreen = "home" },
            onAddItemClick = { currentScreen = "add" },
            onItemClick = { currentScreen = "details" }
        )
        "add" -> AddWasteItemScreen(
            onBackClick = { currentScreen = "history" },
            onSaveClick = { currentScreen = "history" }
        )
        "details" -> WasteItemDetailsScreen(
            onBackClick = { currentScreen = "history" },
            onDeleteClick = { currentScreen = "history" }
        )
    }
}
