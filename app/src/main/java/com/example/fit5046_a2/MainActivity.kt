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
    val currentScreen = remember { mutableStateOf("home") }
    val selectedItem = remember { mutableStateOf<WasteItem?>(null) }

    // Initialize the list of waste items with some default data
    val wasteItems = remember {
        mutableStateListOf(
            WasteItem(1, "Plastic Bottle", "Plastic", "PET (polyethylene Terephthalate)", "Commonly used for single-use bottles.", "Remove cap and label", "Recyclable", "Today"),
            WasteItem(2, "Glass Bottle", "Glass", "Glass", "Commonly used for drinks.", "Rinse thoroughly", "Recyclable", "Today"),
            WasteItem(3, "Mobile Phone", "Electronics", "Mixed", "Old electronic devices.", "Take to e-waste center", "Recyclable", "Yesterday"),
            WasteItem(4, "Aluminum Soda Can", "Aluminum", "Aluminum", "Drink cans.", "Rinse and crush", "Recyclable", "Yesterday"),
            WasteItem(5, "Book Paper", "Paper", "Paper", "Old books or magazines.", "Keep dry", "Recyclable", "Yesterday")
        )
    }

    when (currentScreen.value) {
        "home" -> HomeScreen(
            onProfileClick = {
                currentScreen.value = "profile"
                Log.d("Navigation", "Navigating to profile. Current screen: ${currentScreen.value}")
            },
            onGuideClick = {
                Log.d("Navigation", "Guide clicked (not implemented)")
            },
            onHistoryClick = {
                currentScreen.value = "history"
                Log.d("Navigation", "Navigating to history. Current screen: ${currentScreen.value}")
            },
            onMapClick = { 
                currentScreen.value = "map"
                Log.d("Navigation", "Navigating to map. Current screen: ${currentScreen.value}")
            } ,
            onLoginClick = { 
                currentScreen.value = "login"
                Log.d("Navigation", "Navigating to login. Current screen: ${currentScreen.value}")
            },
            onAchieveClick = {
                currentScreen.value = "achieve"
                Log.d("Navigation", "Navigating to achieve. Current screen: ${currentScreen.value}")
            }
        )

        "login" -> LoginScreen(
            onBackClick = { 
                currentScreen.value = "home"
                Log.d("Navigation", "Back to home. Current screen: ${currentScreen.value}")
            },
            onLoginClick = { 
                currentScreen.value = "home"
                Log.d("Navigation", "Login success. Current screen: ${currentScreen.value}")
            },
            onGoogleLoginClick = { },
            onRegisterClick = { 
                currentScreen.value = "register"
                Log.d("Navigation", "Navigating to register. Current screen: ${currentScreen.value}")
            },
            onForgotPasswordClick = { }
        )

        "register" -> RegisterScreen(
            onBackClick = { 
                currentScreen.value = "login"
                Log.d("Navigation", "Back to login. Current screen: ${currentScreen.value}")
            },
            onRegisterClick = { 
                currentScreen.value = "home"
                Log.d("Navigation", "Register success. Current screen: ${currentScreen.value}")
            }
        )

        "profile" -> ProfileScreen(
            onHomeClick = { 
                currentScreen.value = "home"
                Log.d("Navigation", "Navigating to home. Current screen: ${currentScreen.value}")
            },
            onHistoryClick = { 
                currentScreen.value = "history"
                Log.d("Navigation", "Navigating to history. Current screen: ${currentScreen.value}")
            },
            onMapClick = { 
                currentScreen.value = "map"
                Log.d("Navigation", "Navigating to map. Current screen: ${currentScreen.value}")
            },
            onAchieveClick = {
                currentScreen.value = "achieve"
                Log.d("Navigation", "Navigating to achieve. Current screen: ${currentScreen.value}")
            }
        )

        "history" -> WasteHistoryScreen(
            wasteItems = wasteItems,
            onBackClick = {
                currentScreen.value = "home"
                Log.d("Navigation", "Navigating to home. Current screen: ${currentScreen.value}")
            },
            onAddItemClick = {
                selectedItem.value = null
                currentScreen.value = "add"
                Log.d("Navigation", "Navigating to add. Current screen: ${currentScreen.value}")
            },
            onItemClick = { item ->
                selectedItem.value = item
                currentScreen.value = "details"
                Log.d("Navigation", "Viewing details for ${item.name}. Current screen: ${currentScreen.value}")
            },
            onEditItemClick = { item ->
                selectedItem.value = item
                currentScreen.value = "edit"
                Log.d("Navigation", "Navigating to edit for ${item.name}. Current screen: ${currentScreen.value}")
            },
            onDeleteItemClick = { item ->
                wasteItems.remove(item)
                Log.d("Navigation", "Deleted item: ${item.name}. Remaining items: ${wasteItems.size}")
            },
            onMapClick = {
                currentScreen.value = "map"
                Log.d("Navigation", "Navigating to map. Current screen: ${currentScreen.value}")
            },
            onAchieveClick = {
                currentScreen.value = "achieve"
                Log.d("Navigation", "Navigating to achieve. Current screen: ${currentScreen.value}")
            }
        )
        "add", "edit" -> AddWasteItemScreen(
            initialItem = selectedItem.value,
            onBackClick = {
                currentScreen.value = "history"
                Log.d("Navigation", "Back to history. Current screen: ${currentScreen.value}")
            },
            onSaveClick = { updatedItem ->
                if (currentScreen.value == "edit") {
                    val index = wasteItems.indexOfFirst { it.id == updatedItem.id }
                    if (index != -1) {
                        wasteItems[index] = updatedItem
                    }
                } else {
                    wasteItems.add(updatedItem)
                }
                currentScreen.value = "history"
                Log.d("Navigation", "Item saved. Returning to history. Current screen: ${currentScreen.value}")
            }
        )
        "details" -> {
            selectedItem.value?.let { item ->
                WasteItemDetailsScreen(
                    item = item,
                    onBackClick = {
                        currentScreen.value = "history"
                        Log.d("Navigation", "Back to history. Current screen: ${currentScreen.value}")
                    },
                    onDeleteClick = {
                        wasteItems.remove(item)
                        currentScreen.value = "history"
                        Log.d("Navigation", "Item deleted: ${item.name}. Current screen: ${currentScreen.value}")
                    }
                )
            }
        }
        "map" -> MapScreen(
            onHomeClick = {
                currentScreen.value = "home"
                Log.d("Navigation", "Navigating to home. Current screen: ${currentScreen.value}")
            },
            onHistoryClick = {
                currentScreen.value = "history"
                Log.d("Navigation", "Navigating to history. Current screen: ${currentScreen.value}")
            },
            onProfileClick = {
                currentScreen.value = "profile"
                Log.d("Navigation", "Navigating to profile. Current screen: ${currentScreen.value}")
            },
            onGuideClick = {
                Log.d("Navigation", "Guide clicked (not implemented)")
            },
            onAchieveClick = {
                currentScreen.value = "achieve"
                Log.d("Navigation", "Navigating to achieve. Current screen: ${currentScreen.value}")
            }
        )

        "achieve" -> AchievementScreen(
            onHomeClick = {
                currentScreen.value = "home"
                Log.d("Navigation", "Navigating to home. Current screen: ${currentScreen.value}")
            },
            onHistoryClick = {
                currentScreen.value = "history"
                Log.d("Navigation", "Navigating to history. Current screen: ${currentScreen.value}")
            },
            onProfileClick = {
                currentScreen.value = "profile"
                Log.d("Navigation", "Navigating to profile. Current screen: ${currentScreen.value}")
            },
            onGuideClick = {
                Log.d("Navigation", "Guide clicked (not implemented)")
            },
            onMapClick = {
                currentScreen.value = "map"
                Log.d("Navigation", "Navigating to achieve. Current screen: ${currentScreen.value}")
            }
        )
    }
}
