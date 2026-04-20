package com.example.fit5046_a2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fit5046_a2.ui.components.CustomTextField
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.fit5046_a2.R

@Composable
fun LoginScreen(
    onBackClick: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onGoogleLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val backgroundColor = Color(0xFFF4F5F7)
    val greenColor = Color(0xFF34C759)
    val lightTextColor = Color(0xFF8E8E93)
    val borderColor = Color(0xFFE5E5EA)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box {

                // back button
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                        .size(40.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.backarrow),
                        contentDescription = "Back",
                        modifier = Modifier.size(24.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "EcoSort",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F1F1F)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Responsible recycling made easy",
                        fontSize = 12.sp,
                        color = lightTextColor
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    CustomTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email or Phone Number",
                        icon = Icons.Default.Email
                    )

                    CustomTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Password",
                        icon = Icons.Default.Lock,
                        isPassword = true,
                        passwordVisible = passwordVisible,
                        onTogglePasswordVisibility = {
                            passwordVisible = !passwordVisible
                        }
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "Forgot Password?",
                            color = greenColor,
                            fontSize = 12.sp,
                            modifier = Modifier.clickable { onForgotPasswordClick() }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onLoginClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = greenColor)
                    ) {
                        Text("Log In", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = onGoogleLoginClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text("Continue with Google")
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = onRegisterClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = greenColor
                        )
                    ) {
                        Text("Create New Account")
                    }
                }
            }
        }
    }
}