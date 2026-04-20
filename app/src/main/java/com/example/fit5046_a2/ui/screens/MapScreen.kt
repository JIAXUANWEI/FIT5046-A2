package com.example.fit5046_a2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fit5046_a2.R
import com.example.fit5046_a2.ui.components.BottomNavigationBar
import com.example.fit5046_a2.ui.components.TopNavigationBar
import com.google.maps.android.compose.*

import com.google.android.gms.maps.model.*

@Composable
fun MapScreen(
    onHomeClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onProfileClick: () -> Unit,
    onGuideClick: () -> Unit
) {

    val melbourne = LatLng(-37.8136, 144.9631)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(melbourne, 14f)
    }

    Scaffold(
        topBar = {
            TopNavigationBar(
                selectedItem = "",
                onProfileClick = onProfileClick,
                onGuideClick = onGuideClick
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedItem = "Map",
                onHomeClick = onHomeClick,
                onHistoryClick = onHistoryClick,
                onMapClick = { }
            )
        },
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            // 🗺️ Google Map
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {

                // Example recycling points
                val locations = listOf(
                    LatLng(-37.81, 144.96),
                    LatLng(-37.82, 144.97),
                    LatLng(-37.83, 144.97),
                    LatLng(-37.80, 144.95)
                )

                locations.forEach {
                    Marker(
                        state = MarkerState(position = it),
                        title = "Recycling Point",
                        icon = BitmapDescriptorFactory.fromResource(R.drawable.recyclebin)
                    )
                }
            }

//            // HEADER (Top Bar)
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .align(Alignment.TopCenter)
//                    .background(Color(0xFFF6F7F7))
//                    .padding(top = 12.dp, bottom = 12.dp)
//            ) {
//
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 20.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//
//                    // LEFT: Help
//                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                        Icon(
//                            imageVector = Icons.Default.Favorite,
//                            contentDescription = "History",
//                            tint = Color.Gray
//                        )
//                        Text("Help", fontSize = 12.sp, color = Color.Gray)
//                    }
//
//                    // CENTER: Title
//                    Text(
//                        text = "EcoSort",
//                        style = MaterialTheme.typography.titleLarge,
//                        color = Color(0xFF1F1F1F)
//                    )
//
//                    // RIGHT: Profile
//                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                        Icon(
//                            imageVector = Icons.Default.Person,
//                            contentDescription = "Profile",
//                            tint = Color.Gray
//                        )
//                        Text("Profile", fontSize = 12.sp, color = Color.Gray)
//                    }
//                }
//            }

            // Search Bar (Top Overlay)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
                    .padding(horizontal = 16.dp)
                    .shadow(6.dp, RoundedCornerShape(24.dp))
                    .background(Color.White, RoundedCornerShape(24.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .align(Alignment.TopCenter)
            ) {
//                Text(
//                    text = "Search by Location",
//                    color = Color.Gray,
//                    fontSize = 14.sp
//                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Search by Location",
                        color = Color.Gray,
                        modifier = Modifier.weight(1f)
                    )

                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}