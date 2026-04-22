package com.example.fit5046_a2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fit5046_a2.R
import com.example.fit5046_a2.model.WasteItem

@Composable
fun WasteItemDetailsScreen(
    item: WasteItem = WasteItem(
        1, "Plastic Bottle", "Plastic", "PET (polyethylene Terephthalate)",
        "Commonly used for single-use bottles.", "Remove cap and label", "Recyclable"
    ),
    onBackClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    val greenColor = Color(0xFF2EBD59)
    val cardinalColor = Color(0xFFC41E3A)
    val lightCardinal = Color(0xFFE52B50)
    val lightGray = Color(0xFFF5F5F5)

    // Determine the image based on item name using the new drawables
    val imageResId = when {
        item.name.contains("Plastic", ignoreCase = true) -> R.drawable.plastic_bottle
        item.name.contains("Glass", ignoreCase = true) -> R.drawable.glass_bottle
        item.name.contains("Phone", ignoreCase = true) || item.name.contains("Mobile", ignoreCase = true) -> R.drawable.phone
        item.name.contains("Can", ignoreCase = true) || item.name.contains("Soda", ignoreCase = true) -> R.drawable.soda_can
        item.name.contains("Box", ignoreCase = true) || item.name.contains("Paper", ignoreCase = true) -> R.drawable.book_paper
        else -> R.drawable.leaf
    }

    Scaffold(
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
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
                    text = "Waste Item Details",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                
                Spacer(modifier = Modifier.width(36.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Large Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFFF9F9F9)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = null,
                    modifier = Modifier.size(160.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Item Name Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = lightGray)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Item",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = item.name,
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                    Text(
                        text = item.status,
                        color = greenColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Details Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = lightGray)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    DetailRow("Category", item.category)
                    DetailRow("Material Type", item.materialType)
                    DetailRow("Description", item.description)
                    DetailRow("Recycling Instructions", item.recyclingInstructions)
                    DetailRow("Status", item.status)
                    DetailRow("Date Added", item.dateAdded)
                    DetailRow("Added by", item.addedBy)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Cardinal Gradient Delete Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(cardinalColor, lightCardinal)
                        )
                    )
                    .clickable { onDeleteClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Delete Item",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(0.4f),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black
        )
        Text(
            text = value,
            modifier = Modifier.weight(0.6f),
            fontSize = 14.sp,
            color = Color.DarkGray
        )
    }
}
