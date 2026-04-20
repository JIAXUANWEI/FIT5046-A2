package com.example.fit5046_a2.ui.screens

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
import com.example.fit5046_a2.ui.components.CustomTextField
import com.example.fit5046_a2.ui.components.EcoGreenDark

// green
val EcoGreenLight = Color(0xFF8BC34A)
val EcoGreenDark = Color(0xFF2EBD59)
val BackgroundGray = Color(0xFFF8F9FA)


@Composable
fun RegisterScreen() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
            .padding(24.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = { /* 返回 */ }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // avator
        Surface(
            modifier = Modifier.size(100.dp),
            shape = RoundedCornerShape(50.dp),
            color = Color.White,
            shadowElevation = 2.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    Icons.Default.PersonAdd,
                    contentDescription = null,
                    tint = EcoGreenDark,
                    modifier = Modifier.size(50.dp).background(EcoGreenDark.copy(alpha = 0.1f), RoundedCornerShape(25.dp)).padding(10.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Create Account", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text(text = "Join EcoSort today!", color = Color.Gray, fontSize = 16.sp)

        Spacer(modifier = Modifier.height(32.dp))

        // form
        CustomTextField(label = "Full Name", icon = Icons.Default.Person)
        CustomTextField(label = "Email or Phone Number", icon = Icons.Default.Email)
        CustomTextField(label = "Password", icon = Icons.Default.Lock, isPassword = true)
        CustomTextField(label = "Confirm Password", icon = Icons.Default.Shield, isPassword = true)

        Spacer(modifier = Modifier.height(16.dp))

        //
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFF1F3F4)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "OPTIONAL PREFERENCES",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))

                // city
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("City / Area") },
                    leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = EcoGreenDark) },
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = EcoGreenDark,
                        cursorColor = EcoGreenDark
                    )
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = false, onCheckedChange = {})
                    Text("Opt-in for recycling day reminders and local center updates.", fontSize = 12.sp, color = Color.Gray)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // -agree
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = false, onCheckedChange = {})
            Text(text = "I agree to the Terms of Service and Privacy Policy.", fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // buttom
        Button(
            onClick = { /* TODO */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(listOf(EcoGreenLight, EcoGreenDark))
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Create Account", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}