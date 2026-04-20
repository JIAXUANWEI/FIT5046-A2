package com.example.fit5046_a2.ui.components
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// 定义 UI 中的绿色主题
val EcoGreenLight = Color(0xFF8BC34A)
val EcoGreenDark = Color(0xFF2EBD59)
val BackgroundGray = Color(0xFFF8F9FA)

@Composable
fun CustomTextField(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, isPassword: Boolean = false) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        placeholder = { Text(label, color = Color.LightGray) },
        leadingIcon = { Icon(icon, contentDescription = null, tint = Color.Gray) },
        trailingIcon = {
            if (isPassword) {
                Icon(Icons.Default.VisibilityOff, contentDescription = null, tint = Color.LightGray)
            }
        },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = EcoGreenDark,
            cursorColor = EcoGreenDark
        )
    )
}