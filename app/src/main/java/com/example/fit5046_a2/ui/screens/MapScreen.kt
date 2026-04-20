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
import androidx.compose.runtime.remember
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
import androidx.compose.ui.platform.LocalContext
import com.example.fit5046_a2.data.readRecyclingData
import android.location.Location
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import java.util.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import com.google.android.gms.maps.model.*

fun distanceBetween(a: LatLng, b: LatLng): Float {
    val result = FloatArray(1)
    Location.distanceBetween(
        a.latitude, a.longitude,
        b.latitude, b.longitude,
        result
    )
    return result[0]
}

fun isNightTime(): Boolean {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return hour < 6 || hour > 18
}

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
            val context = LocalContext.current
            val locations = remember {
                readRecyclingData(context)
            }
            var isMoving by remember { mutableStateOf(true) }
            var showPopup by remember { mutableStateOf(false) }

            LaunchedEffect(isMoving) {
                if (!isMoving) {
                    kotlinx.coroutines.delay(5000)
                    showPopup = true
                } else {
                    showPopup = false
                }
            }

            if (showPopup) {
                AlertDialog(
                    onDismissRequest = { showPopup = false },
                    confirmButton = {
                        Button(onClick = { showPopup = false }) {
                            Text("OK")
                        }
                    },
                    title = { Text("Suggestion") },
                    text = { Text("You’ve been inactive. Check nearby recycling points!") }
                )
            }

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                val filteredLocations = if (isMoving) {
                    locations.filter {
                        distanceBetween(melbourne, it) < 1000
                    }.take(20)
                } else {
                    locations.take(50)
                }

                val closestLocation = filteredLocations.minByOrNull {
                    distanceBetween(melbourne, it)
                }

                filteredLocations.forEach { location ->
                    Marker(
                        state = MarkerState(position = location),
                        title = "Recycling Point",
                        icon = if (isNightTime() && location == closestLocation) {
                            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                        } else {
                            BitmapDescriptorFactory.fromResource(R.drawable.recyclebin)
                        }
                    )
                }
            }

            Button(
                onClick = { isMoving = !isMoving },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                Text(if (isMoving) "Stop Moving" else "Start Moving")
            }

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