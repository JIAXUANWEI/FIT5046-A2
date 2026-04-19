package com.example.fit5046_a2.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fit5046_a2.R
import androidx.compose.ui.text.font.FontWeight

@Composable
fun TopNavigationBar(
    selectedItem: String = "",
    onProfileClick: () -> Unit = {},
    onGuideClick: () -> Unit = {},
) {
    val selectedColor = Color(0xFF22C55E)
    val unselectedColor = Color(0xFF8E8E93)
    val barBackground = Color.White

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(
                    color = barBackground
                )
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Row(
                modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp),
                horizontalArrangement = Arrangement.Absolute.SpaceAround,
                verticalAlignment = Alignment.CenterVertically) {

                Spacer(Modifier.padding(horizontal = 10.dp))

                Text(
                    text = "EcoSort",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(Modifier.padding(horizontal = 10.dp))


            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun TopPreview() {
    TopNavigationBar {  }
}