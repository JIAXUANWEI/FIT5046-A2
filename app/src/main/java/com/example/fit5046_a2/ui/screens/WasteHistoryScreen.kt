package com.example.fit5046_a2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Eco
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
    onMapClick: () -> Unit = {},
    onAchieveClick: () -> Unit
) {
    val backgroundColor = Color(0xFFFAF9F6)
    val primaryGreen = Color(0xFF2EBD59)
    
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All Time") }
    val categories = listOf("All Time", "Plastic", "Paper", "Glass", "Metal")

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedItem = "History",
                onHomeClick = { onBackClick() },
                onHistoryClick = { },
                onMapClick = onMapClick,
                onAchieveClick = onAchieveClick
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddItemClick,
                containerColor = primaryGreen,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Item",
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        containerColor = backgroundColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    onClick = onBackClick,
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 2.dp,
                    modifier = Modifier.size(48.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                
                Text(
                    text = "History",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF2D2D2D)
                )
                
                Surface(
                    onClick = { /* Calendar click */ },
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 2.dp,
                    modifier = Modifier.size(48.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "Calendar",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Search Bar
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(30.dp),
                color = Color.White,
                shadowElevation = 1.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.Gray.copy(alpha = 0.6f),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    BasicTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.weight(1f),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.DarkGray),
                        decorationBox = { innerTextField ->
                            if (searchQuery.isEmpty()) {
                                Text(
                                    "Search history...", 
                                    color = Color.Gray.copy(alpha = 0.6f), 
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                            innerTextField()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Categories
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                categories.forEach { category ->
                    val isSelected = category == selectedCategory
                    Surface(
                        onClick = { selectedCategory = category },
                        shape = RoundedCornerShape(25.dp),
                        color = if (isSelected) primaryGreen else Color.White,
                        modifier = Modifier.height(50.dp)
                    ) {
                        Box(
                            modifier = Modifier.padding(horizontal = 24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = category,
                                color = if (isSelected) Color.White else Color.Gray,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                val filteredItems = if (selectedCategory == "All Time") {
                    wasteItems
                } else {
                    wasteItems.filter { it.category.contains(selectedCategory, ignoreCase = true) }
                }

                if (filteredItems.isNotEmpty()) {
                    val todayItems = filteredItems.filter { it.dateAdded.contains("Today", ignoreCase = true) }
                    val yesterdayItems = filteredItems.filter { it.dateAdded.contains("Yesterday", ignoreCase = true) }
                    val otherItems = filteredItems.filter { !it.dateAdded.contains("Today", ignoreCase = true) && !it.dateAdded.contains("Yesterday", ignoreCase = true) }

                    if (todayItems.isNotEmpty()) {
                        item { SectionHeader("TODAY") }
                        items(todayItems) { item ->
                            WasteItemCard(
                                item = item, 
                                onClick = { onItemClick(item) }
                            )
                        }
                    }

                    if (yesterdayItems.isNotEmpty()) {
                        item { SectionHeader("YESTERDAY") }
                        items(yesterdayItems) { item ->
                            WasteItemCard(
                                item = item, 
                                onClick = { onItemClick(item) }
                            )
                        }
                    }

                    if (otherItems.isNotEmpty()) {
                        item { SectionHeader("PAST") }
                        items(otherItems) { item ->
                            WasteItemCard(
                                item = item,
                                onClick = { onItemClick(item) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = 1.dp,
            color = Color.LightGray.copy(alpha = 0.5f)
        )
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.labelLarge,
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = 1.dp,
            color = Color.LightGray.copy(alpha = 0.5f)
        )
    }
}

@Composable
fun WasteItemCard(
    item: WasteItem, 
    onClick: () -> Unit
) {
    val imageResId = when {
        item.name.contains("Plastic", ignoreCase = true) -> R.drawable.plastic_bottle
        item.name.contains("Glass", ignoreCase = true) -> R.drawable.glass_bottle
        item.name.contains("Box", ignoreCase = true) || item.name.contains("Paper", ignoreCase = true) -> R.drawable.book_paper
        item.name.contains("Phone", ignoreCase = true) || item.name.contains("Mobile", ignoreCase = true) -> R.drawable.phone
        item.name.contains("Can", ignoreCase = true) || item.name.contains("Soda", ignoreCase = true) -> R.drawable.soda_can
        else -> R.drawable.leaf
    }
    
    val categoryColor = when {
        item.name.contains("Plastic", ignoreCase = true) -> Color(0xFFE8F4FF)
        item.name.contains("Glass", ignoreCase = true) -> Color(0xFFE8F5E9)
        item.name.contains("Box", ignoreCase = true) || item.name.contains("Paper", ignoreCase = true) -> Color(0xFFFFF4E5)
        item.name.contains("Phone", ignoreCase = true) || item.name.contains("Mobile", ignoreCase = true) -> Color(0xFFF3E5F5)
        item.name.contains("Can", ignoreCase = true) || item.name.contains("Soda", ignoreCase = true) -> Color(0xFFFFEBEE)
        else -> Color(0xFFF5F5F5)
    }

    val points = when {
        item.name.contains("Plastic", ignoreCase = true) -> 15
        item.name.contains("Glass", ignoreCase = true) -> 20
        item.name.contains("Box", ignoreCase = true) || item.name.contains("Paper", ignoreCase = true) -> 25
        item.name.contains("Phone", ignoreCase = true) || item.name.contains("Mobile", ignoreCase = true) -> 50
        item.name.contains("Can", ignoreCase = true) || item.name.contains("Soda", ignoreCase = true) -> 10
        else -> 10
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(categoryColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF2D2D2D)
                )
                Text(
                    text = "10:42 AM • Recycled at Home",
                    color = Color.Gray.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
            }

            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFFE8F5E9),
                modifier = Modifier.height(36.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "+$points",
                        color = Color(0xFF2EBD59),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Icon(
                        imageVector = Icons.Default.Eco,
                        contentDescription = null,
                        tint = Color(0xFF2EBD59),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
