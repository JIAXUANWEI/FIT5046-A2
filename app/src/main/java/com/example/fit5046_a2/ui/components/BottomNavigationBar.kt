package com.example.fit5046_a2.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fit5046_a2.R

@Composable
fun BottomNavigationBar(
    selectedItem: String = "Map",
    onHomeClick: () -> Unit = {},
    onHistoryClick: () -> Unit = {},
    onScanClick: () -> Unit = {},
    onAchieveClick: () -> Unit = {},
    onMapClick: () -> Unit = {}
) {
    val selectedColor = Color(0xFF22C55E)
    val unselectedColor = Color(0xFF8E8E93)
    val barBackground = Color.White
    val centerCircleColor = Color(0xFFF5F5F5)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(88.dp)
                .background(
                    color = barBackground,
                    shape = RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp)
                )
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavItem(
                    imageRes = R.drawable.home,
                    label = "Home",
                    selected = selectedItem == "Home",
                    selectedColor = selectedColor,
                    unselectedColor = unselectedColor,
                    onClick = onHomeClick
                )

                NavItem(
                    imageRes = R.drawable.history,
                    label = "History",
                    selected = selectedItem == "History",
                    selectedColor = selectedColor,
                    unselectedColor = unselectedColor,
                    onClick = onHistoryClick
                )

                Spacer(modifier = Modifier.width(72.dp))

                NavItem(
                    imageRes = R.drawable.achieve,
                    label = "Achieve",
                    selected = selectedItem == "Achieve",
                    selectedColor = selectedColor,
                    unselectedColor = unselectedColor,
                    onClick = onAchieveClick
                )

                NavItem(
                    imageRes = R.drawable.map,
                    label = "Map",
                    selected = selectedItem == "Map",
                    selectedColor = selectedColor,
                    unselectedColor = unselectedColor,
                    onClick = onMapClick
                )
            }
        }

        Box(
            modifier = Modifier
                .offset(y = (-20).dp)
                .size(92.dp)
                .shadow(10.dp, CircleShape, clip = false)
                .background(
                    color = centerCircleColor,
                    shape = CircleShape
                )
                .clickable { onScanClick() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.scan),
                contentDescription = "Scan",
                modifier = Modifier.size(34.dp)
            )
        }
    }
}

@Composable
fun NavItem(
    imageRes: Int,
    label: String,
    selected: Boolean,
    selectedColor: Color,
    unselectedColor: Color,
    onClick: () -> Unit
) {
    val textColor = if (selected) selectedColor else unselectedColor

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 6.dp, vertical = 4.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = label,
            modifier = Modifier.size(26.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal
        )
    }
}