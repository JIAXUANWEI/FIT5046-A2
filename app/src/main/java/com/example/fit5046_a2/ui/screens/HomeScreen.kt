package com.example.fit5046_a2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fit5046_a2.R
import com.example.fit5046_a2.ui.components.BottomNavigationBar
import com.example.fit5046_a2.ui.components.RecycleStatBox
import com.example.fit5046_a2.ui.components.SettingsItem
import com.example.fit5046_a2.ui.components.TopNavigationBar
import com.example.fit5046_a2.ui.theme.FIT5046A2Theme

@Composable
fun HomeScreen(
    onHistoryClick: () -> Unit,
    onMapClick: () -> Unit,
    onProfileClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    val backgroundColor = Color(0xFFF6F7F7)
    val mintColor = Color(0xFFDDF3E8)
    val greenColor = Color(0xFF34C759)

    Scaffold(
        topBar = { TopNavigationBar (onProfileClick = onProfileClick) },
        bottomBar = {
            BottomNavigationBar(
                selectedItem = "Home",
                onHistoryClick = onHistoryClick,
                onMapClick = onMapClick,
            ) },
        containerColor = backgroundColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Welcome Back!",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF1F1F1F)
                )
            }

            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = mintColor)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp)
                ) {
                    Text(
                        text = "You have recycled:",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF1F1F1F),
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        RecycleStatBox(
                            title = "Bottles",
                            value = "32",
                            greenColor = greenColor,
                            modifier = Modifier.weight(1f)
                        )

                        RecycleStatBox(
                            title = "Paper",
                            value = "32",
                            greenColor = greenColor,
                            modifier = Modifier.weight(1f)
                        )

                        RecycleStatBox(
                            title = "Glass",
                            value = "32",
                            greenColor = greenColor,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Box(
                modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
            ) {
                Text("Items to Return?",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            SettingsItem(
                imageRes = R.drawable.camera,
                title = "Scan Item",
                subtitle = "Find the closest refund point"
            )

            Box(
                modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
            ) {
                Text("Tips",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFDDF3E8))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Spacer(modifier = Modifier.height(10.dp))

                    // Tip 1
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF34C759))
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "Rinse bottles before recycling",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    // Tip 2
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF34C759))
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "Keep paper and cardboard dry",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    // Tip 3
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF34C759))
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "Separate glass from general waste",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    // Tip 4
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF34C759))
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "Do not mix food waste with recyclables",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
