package com.example.fit5046_a2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fit5046_a2.R
import com.example.fit5046_a2.ui.components.BottomNavigationBar
import com.example.fit5046_a2.ui.components.SectionTitle
import com.example.fit5046_a2.ui.components.SettingsItem
import com.example.fit5046_a2.ui.components.ToggleItem
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.ui.res.painterResource
@Composable
fun ProfileScreen() {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var locationEnabled by remember { mutableStateOf(true) }

    val backgroundColor = Color(0xFFF6F7F7)
    val mintColor = Color(0xFFDDF3E8)
    val greenColor = Color(0xFF34C759)
    val lightGray = Color(0xFFBDBDBD)
    val redColor = Color(0xFFFF5A5F)

    Scaffold(
        bottomBar = { BottomNavigationBar() },
        containerColor = backgroundColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Profile",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color(0xFF1F1F1F)
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "Manage your account settings",
                        fontSize = 13.sp,
                        color = Color(0xFF8E8E93)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.edit),
                        contentDescription = "Edit",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = mintColor)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(contentAlignment = Alignment.BottomEnd) {
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile Avatar",
                                modifier = Modifier.size(36.dp),
                                tint = Color.Gray
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFFFD54F)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("♥", fontSize = 10.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Mia Eco",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "mia.eco@example.com",
                        color = lightGray,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(containerColor = greenColor),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("2,450 Eco Points")
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            SectionTitle("MY ACTIVITY")

            SettingsItem(
                imageRes = R.drawable.favorites,
                title = "Favorites",
                subtitle = "Saved recycling points"
            )

            SettingsItem(
                imageRes = R.drawable.document,
                title = "Data Export",
                subtitle = "Download your progress"
            )

            Spacer(modifier = Modifier.height(20.dp))

            SectionTitle("PREFERENCES")

            ToggleItem(
                imageRes = R.drawable.notification,
                title = "Notifications",
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
            )

            ToggleItem(
                imageRes = R.drawable.location,
                title = "Location Services",
                checked = locationEnabled,
                onCheckedChange = { locationEnabled = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            SectionTitle("SUPPORT")

            SettingsItem(
                imageRes = R.drawable.help,
                title = "Help & FAQ"
            )

            SettingsItem(
                imageRes = R.drawable.feedback,
                title = "Send Feedback"
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedButton(
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = redColor
                )
            ) {
                Text("Log Out")
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}