package com.example.fit5046_a2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fit5046_a2.R
import com.example.fit5046_a2.model.WasteItem
import com.example.fit5046_a2.ui.components.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WasteHistoryScreen(
    wasteItems: List<WasteItem>,
    onBackClick: () -> Unit = {},
    onAddItemClick: () -> Unit = {},
    onItemClick: (WasteItem) -> Unit = {},
    onDeleteClick: (WasteItem) -> Unit = {},
    onEditClick: (WasteItem) -> Unit = {},
    onMapClick: () -> Unit = {}
) {
    val backgroundColor = Color(0xFFF6F7F7)
    val greenColor = Color(0xFF4CAF50)
    
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedItem = "History",
                onHomeClick = { onBackClick() },
                onHistoryClick = { },
                onMapClick = onMapClick
            )
        },
        containerColor = backgroundColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .size(36.dp)
                        .background(greenColor.copy(alpha = 0.1f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = greenColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
                
                Text(
                    text = "Waste Items",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.width(36.dp)) // To balance the back button
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Search and Add Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var searchQuery by remember { mutableStateOf("") }
                
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search", color = Color.Gray) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = greenColor) },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.LightGray,
                        focusedBorderColor = greenColor,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = onAddItemClick,
                    modifier = Modifier.height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = greenColor),
                    shape = RoundedCornerShape(24.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add Item", fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 20.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(wasteItems) { item ->
                    WasteItemCard(
                        item = item, 
                        onClick = { onItemClick(item) },
                        onDeleteClick = { onDeleteClick(item) },
                        onEditClick = { onEditClick(item) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WasteItemCard(
    item: WasteItem, 
    onClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }
    
    // Determine the image based on item name using the new drawables
    val imageResId = when {
        item.name.contains("Plastic Bottle", ignoreCase = true) -> R.drawable.plastic_bottle
        item.name.contains("Glass Bottle", ignoreCase = true) -> R.drawable.glass_bottle
        item.name.contains("Phone", ignoreCase = true) || item.name.contains("Mobile", ignoreCase = true) -> R.drawable.phone
        item.name.contains("Soda Can", ignoreCase = true) -> R.drawable.soda_can
        item.name.contains("Book", ignoreCase = true) || item.name.contains("Paper", ignoreCase = true) -> R.drawable.book_paper
        else -> R.drawable.leaf
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(Color(0xFFF0F0F0), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = item.category,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
                Text(
                    text = item.status,
                    color = Color(0xFF4CAF50),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Box {
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More", tint = Color.Gray)
                }
                
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Edit") },
                        onClick = {
                            menuExpanded = false
                            onEditClick()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Delete", color = Color.Red) },
                        onClick = {
                            menuExpanded = false
                            onDeleteClick()
                        }
                    )
                }
            }
        }
    }
}
