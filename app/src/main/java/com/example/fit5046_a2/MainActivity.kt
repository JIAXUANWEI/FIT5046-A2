package com.example.fit5046_a2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.fit5046_a2.model.WasteItem
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
    var currentScreen by remember { mutableStateOf("home") }
    var selectedItem by remember { mutableStateOf<WasteItem?>(null) }
    
    // Initialize the list of waste items with some default data
    val wasteItems = remember {
        mutableStateListOf(
            WasteItem(1, "Plastic Bottle", "Plastic", "PET (polyethylene Terephthalate)", "Commonly used for single-use bottles.", "Remove cap and label", "Recyclable"),
            WasteItem(2, "Glass Bottle", "Glass", "Glass", "Commonly used for drinks.", "Rinse thoroughly", "Recyclable"),
            WasteItem(3, "Mobile Phone", "Electronics", "Mixed", "Old electronic devices.", "Take to e-waste center", "Recyclable"),
            WasteItem(4, "Aluminum Soda Can", "Aluminum", "Aluminum", "Drink cans.", "Rinse and crush", "Recyclable"),
            WasteItem(5, "Book Paper", "Paper", "Paper", "Old books or magazines.", "Keep dry", "Recyclable")
        )
    }
    
    when (currentScreen) {
        "home" -> ProfileScreen(
            onHistoryClick = { 
                currentScreen = "history" 
                Log.d("Navigation", "Navigating to history. Current screen: $currentScreen")
            },
            onMapClick = { 
                currentScreen = "map" 
                Log.d("Navigation", "Navigating to map. Current screen: $currentScreen")
            },
            onLoginClick = { currentScreen = "login" }
        )

        "login" -> LoginScreen(
            onBackClick = { currentScreen = "home" },
            onLoginClick = { currentScreen = "home" },
            onGoogleLoginClick = { },
            onRegisterClick = { currentScreen = "register" },
            onForgotPasswordClick = { }
        )

        "register" -> RegisterScreen(
            onBackClick = { currentScreen = "login" },
            onRegisterClick = { currentScreen = "home" }
        )

        "profile" -> ProfileScreen(
            onHomeClick = { currentScreen = "home" },
            onHistoryClick = { currentScreen = "history" },
            onMapClick = { currentScreen = "map" }

        )
        "history" -> WasteHistoryScreen(
            wasteItems = wasteItems,
            onBackClick = { 
                currentScreen = "home" 
                Log.d("Navigation", "Navigating to home. Current screen: $currentScreen")
            },
            onAddItemClick = { 
                selectedItem = null
                currentScreen = "add" 
                Log.d("Navigation", "Navigating to add. Current screen: $currentScreen, SelectedItem: $selectedItem")
            },
            onItemClick = { item ->
                selectedItem = item
                currentScreen = "details" 
                Log.d("Navigation", "Viewing details for ${selectedItem?.name}. Current screen: $currentScreen")
            },
            onDeleteClick = { item ->
                wasteItems.remove(item)
                Log.d("Navigation", "Item deleted: ${item.name}")
            },
            onEditClick = { item ->
                selectedItem = item
                currentScreen = "edit"
                Log.d("Navigation", "Editing item: ${selectedItem?.name}. Current screen: $currentScreen")
            },
            onMapClick = { 
                currentScreen = "map" 
                Log.d("Navigation", "Navigating to map. Current screen: $currentScreen")
            }
        )
        "add", "edit" -> AddWasteItemScreen(
            initialItem = selectedItem,
            onBackClick = { 
                currentScreen = "history" 
                Log.d("Navigation", "Back to history. Current screen: $currentScreen")
            },
            onSaveClick = { updatedItem -> 
                if (currentScreen == "edit") {
                    val index = wasteItems.indexOfFirst { it.id == updatedItem.id }
                    if (index != -1) {
                        wasteItems[index] = updatedItem
                    }
                } else {
                    wasteItems.add(updatedItem)
                }
                currentScreen = "history" 
                Log.d("Navigation", "Item saved. Returning to history. Current screen: $currentScreen")
            }
        )
        "details" -> {
            selectedItem?.let { item ->
                WasteItemDetailsScreen(
                    item = item,
                    onBackClick = { 
                        currentScreen = "history" 
                        Log.d("Navigation", "Back to history. Current screen: $currentScreen")
                    },
                    onDeleteClick = { 
                        wasteItems.remove(item)
                        currentScreen = "history" 
                        Log.d("Navigation", "Item deleted: ${item.name}. Current screen: $currentScreen")
                    }
                )
            }
        }
        "map" -> MapScreen(
            onHomeClick = { 
                currentScreen = "home" 
                Log.d("Navigation", "Navigating to home. Current screen: $currentScreen")
            },
            onHistoryClick = { 
                currentScreen = "history" 
                Log.d("Navigation", "Navigating to history. Current screen: $currentScreen")
            },
            onProfileClick = {
                currentScreen = "home"
                Log.d("Navigation", "Navigating to profile. Current screen: $currentScreen")
            },
            onGuideClick = {
                Log.d("Navigation", "Guide clicked (not implemented)")
            }
        )
    }
}
