package com.example.fit5046_a2.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun BadgeItem(
    iconRes: Int,
    label: String,
    isUnlocked: Boolean,
    modifier: Modifier = Modifier
) {
    val green = Color(0xFF34C759)
    val lightGreen = Color(0xFFE6F4EA)
    val grayBg = Color(0xFFF7F7F7)
    val grayBorder = Color(0xFFE7E7E7)
    val grayText = Color(0xFFA8ADB4)
    val darkText = Color(0xFF2F2F2F)

    Box(
        modifier = modifier
            .aspectRatio(0.92f)
            .clip(RoundedCornerShape(22.dp))
            .background(if (isUnlocked) Color.White else grayBg)
            .border(
                width = 1.dp,
                color = if (isUnlocked) lightGreen else grayBorder,
                shape = RoundedCornerShape(22.dp)
            )
            .padding(horizontal = 10.dp, vertical = 12.dp)
    ) {
        if (isUnlocked) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(30.dp)
                    .clip(RoundedCornerShape(bottomStart = 14.dp))
                ,
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "✓",
                    color = green,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(6.dp))

            Box(
                modifier = Modifier
                    .size(62.dp)
                    .clip(CircleShape)
                    .background(if (isUnlocked) lightGreen else Color(0xFFF0F1F3)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = label,
                    modifier = Modifier.size(28.dp),
                    alpha = if (isUnlocked) 1f else 0.35f
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = label,
                color = if (isUnlocked) darkText else grayText,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}
