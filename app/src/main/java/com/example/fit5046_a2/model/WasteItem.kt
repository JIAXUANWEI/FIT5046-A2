package com.example.fit5046_a2.model

data class WasteItem(
    val id: Int,
    val name: String,
    val category: String,
    val materialType: String,
    val description: String,
    val recyclingInstructions: String,
    val status: String,
    val dateAdded: String = "April 24, 2024",
    val addedBy: String = "Admin",
    val imageResId: Int? = null
)
