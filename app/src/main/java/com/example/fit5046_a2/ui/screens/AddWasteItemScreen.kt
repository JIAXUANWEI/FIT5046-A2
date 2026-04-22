package com.example.fit5046_a2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.fit5046_a2.model.WasteItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWasteItemScreen(
    initialItem: WasteItem? = null,
    onBackClick: () -> Unit = {},
    onSaveClick: (WasteItem) -> Unit = {}
) {
    val greenColor = Color(0xFF2EBD59)
    val isEditing = initialItem != null
    
    var itemName by remember { mutableStateOf(initialItem?.name ?: "") }
    var category by remember { mutableStateOf(initialItem?.category ?: "") }
    var materialType by remember { mutableStateOf(initialItem?.materialType ?: "") }
    var description by remember { mutableStateOf(initialItem?.description ?: "") }
    var instructions by remember { mutableStateOf(initialItem?.recyclingInstructions ?: "") }
    var status by remember { mutableStateOf(initialItem?.status ?: "") }

    val initialCategories = listOf("Plastic", "Glass", "Electronics", "Aluminum", "Paper", "Metal", "Organic")
    val categoryList = remember { mutableStateListOf(*initialCategories.toTypedArray()) }
    
    // Add initialItem's category if it's not in the list
    LaunchedEffect(initialItem) {
        if (initialItem != null && !categoryList.contains(initialItem.category)) {
            categoryList.add(initialItem.category)
        }
    }

    var showNewCategoryDialog by remember { mutableStateOf(false) }
    var newCategoryName by remember { mutableStateOf("") }

    if (showNewCategoryDialog) {
        AlertDialog(
            onDismissRequest = { showNewCategoryDialog = false },
            title = { Text("Add New Category") },
            text = {
                OutlinedTextField(
                    value = newCategoryName,
                    onValueChange = { newCategoryName = it },
                    label = { Text("Category Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (newCategoryName.isNotBlank()) {
                            if (!categoryList.contains(newCategoryName)) {
                                categoryList.add(newCategoryName)
                            }
                            category = newCategoryName
                            newCategoryName = ""
                            showNewCategoryDialog = false
                        }
                    }
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNewCategoryDialog = false }) {
                    Text("Cancel")
                }
            }
        )
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
                    text = if (isEditing) "Edit Waste Item" else "Add New Waste Item",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                
                Spacer(modifier = Modifier.width(36.dp))
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Image Upload Placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(Color(0xFFF9F9F9), RoundedCornerShape(16.dp))
                    .padding(2.dp),
                contentAlignment = Alignment.Center
            ) {
                // Dashed border (simulated)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(14.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Photo",
                        modifier = Modifier.size(48.dp),
                        tint = Color.DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            InputField(label = "Item name", value = itemName, onValueChange = { itemName = it }, placeholder = "Plastic Bottle")
            
            // Category Dropdown
            CategoryDropdown(
                label = "Category",
                selectedCategory = category,
                categories = categoryList,
                onCategorySelected = { category = it },
                onAddNewCategory = { showNewCategoryDialog = true }
            )

            InputField(label = "Material Type", value = materialType, onValueChange = { materialType = it }, placeholder = "PET (polyethylene Terephthalate)")
            InputField(label = "Description", value = description, onValueChange = { description = it }, placeholder = "Commonly used for single-use bottles.")
            InputField(label = "Recycling Instructions", value = instructions, onValueChange = { instructions = it }, placeholder = "Remove cap and label")
            InputField(label = "Status", value = status, onValueChange = { status = it }, placeholder = "Recyclable")

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val savedItem = WasteItem(
                        id = initialItem?.id ?: (0..1000000).random(),
                        name = itemName,
                        category = category,
                        materialType = materialType,
                        description = description,
                        recyclingInstructions = instructions,
                        status = status,
                        dateAdded = initialItem?.dateAdded ?: "Today",
                        addedBy = initialItem?.addedBy ?: "Admin"
                    )
                    onSaveClick(savedItem)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = greenColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(if (isEditing) "Save Changes" else "Save Item", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun CategoryDropdown(
    label: String,
    selectedCategory: String,
    categories: List<String>,
    onCategorySelected: (String) -> Unit,
    onAddNewCategory: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero) }

    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedCategory,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Select Category", color = Color.LightGray, fontSize = 14.sp) },
                trailingIcon = {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        textFieldSize = coordinates.size.toSize()
                    },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFEEEEEE),
                    focusedBorderColor = Color(0xFF2EBD59),
                    focusedContainerColor = Color(0xFFFAFAFA),
                    unfocusedContainerColor = Color(0xFFFAFAFA)
                )
            )
            // Invisible clickable layer on top of the TextField
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable { expanded = !expanded }
            )
        }
        
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                .background(Color.White)
        ) {
            categories.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(text = selectionOption) },
                    onClick = {
                        onCategorySelected(selectionOption)
                        expanded = false
                    }
                )
            }
            HorizontalDivider()
            DropdownMenuItem(
                text = { 
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF2EBD59))
                        Spacer(Modifier.width(8.dp))
                        Text(text = "Add New Category", color = Color(0xFF2EBD59), fontWeight = FontWeight.Bold)
                    }
                },
                onClick = {
                    onAddNewCategory()
                    expanded = false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color.LightGray, fontSize = 14.sp) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFEEEEEE),
                focusedBorderColor = Color(0xFF2EBD59),
                focusedContainerColor = Color(0xFFFAFAFA),
                unfocusedContainerColor = Color(0xFFFAFAFA)
            )
        )
    }
}
