package com.example.fit5046_a2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.font.FontWeight
import com.example.fit5046_a2.model.SensorLocation
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun MapScreen(
    onHomeClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onProfileClick: () -> Unit,
    onGuideClick: () -> Unit,
    onAchieveClick: () -> Unit
) {

    var query by remember { mutableStateOf("") }
    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    val melbourne = LatLng(-37.8136, 144.9631)
    val sheetState = rememberModalBottomSheetState()
    var selectedLocation by remember { mutableStateOf<SensorLocation?>(null) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(melbourne, 14f)
    }
    val context = LocalContext.current

    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasLocationPermission = granted
    }

    LaunchedEffect(Unit) {
        if (!hasLocationPermission) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
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
                onAchieveClick = onAchieveClick,
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

                val userLocation = LatLng(-37.8136, 144.9631)

                val filteredLocations = locations
                    .filter {
                        it.description.contains(query, ignoreCase = true) ||
                                it.name.contains(query, ignoreCase = true)
                    }
                    .let { filtered ->
                        if (isMoving) {
                            filtered
                                .sortedBy { distanceBetween(userLocation, it.latLng) }
                                .take(20)
                        } else {
                            filtered.take(50)
                        }
                    }

                filteredLocations.forEach { location ->
                    Marker(
                        state = MarkerState(position = location.latLng),
                        title = location.name,
                        snippet = location.description,
                        icon = if (location == selectedLocation)
                            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                        else
                            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED),
                        onClick = {
                            selectedLocation = location

                            cameraPositionState.position =
                                CameraPosition.fromLatLngZoom(location.latLng, 16f)

                            false
                        }
                    )
                }
            }
            if (selectedLocation != null) {
                ModalBottomSheet(
                    onDismissRequest = { selectedLocation = null },
                    sheetState = sheetState,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                ) {

                    val location = selectedLocation!!

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {

                        // Title
                        Text(
                            text = location.description,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Type
                        Text(
                            text = "🏷 ${location.type}",
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Coordinates
                        Text(
                            text = "📍 ${location.locationText}",
                            fontSize = 13.sp,
                            color = Color.DarkGray
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Action button row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Button(
                                onClick = {
                                    // Navigate with Google Maps
                                    val uri = "google.navigation:q=${location.latLng.latitude},${location.latLng.longitude}"
                                    val intent = android.content.Intent(
                                        android.content.Intent.ACTION_VIEW,
                                        android.net.Uri.parse(uri)
                                    )
                                    intent.setPackage("com.google.android.apps.maps")
                                    context.startActivity(intent)
                                }
                            ) {
                                Text("Navigate")
                            }

                            Button(
                                onClick = {
                                    // Add to favorites (placeholder)
                                }
                            ) {
                                Text("❤️ Save")
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                    }
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
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .align(Alignment.TopCenter)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextField(
                        value = query,
                        onValueChange = { query = it },
                        placeholder = { Text("Search by location") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        }
                    )
                }
            }
        }
    }
}