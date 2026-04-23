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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import com.example.fit5046_a2.model.SensorLocation
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Close
import kotlinx.coroutines.*
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
    var showNearbyList by remember { mutableStateOf(false) }
    var selectedLocation by remember { mutableStateOf<SensorLocation?>(null) }
    val melbourne = LatLng(-37.8136, 144.9631)
    val sheetState = rememberModalBottomSheetState()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(melbourne, 14f)
    }
    val sensor = remember { FakeSensorManager() }
    var sensorEnabled by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val greenColor = Color(0xFF4CAF50)
    val mintColor = Color(0xFFDDF3E8)


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

    var isMoving by remember { mutableStateOf(true) }


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
            val currentLocation = LatLng(-37.8136, 144.9631)

            val nearbyLocations = locations
                .distinctBy { it.latLng }
                .sortedBy { distanceBetween(currentLocation, it.latLng) }
                .take(20)

            LaunchedEffect(sensorEnabled) {
                if (sensorEnabled) {
                    sensor.start { moving -> isMoving = moving }
                } else {
                    sensor.stop()
                    isMoving = false // force default (idle mode)
                }
            }
            var showPopup by remember { mutableStateOf(false) }

            LaunchedEffect(isMoving, sensorEnabled) {
                if (!sensorEnabled) {
                    showPopup = false
                    return@LaunchedEffect
                }

                if (!isMoving) {
                    delay(5000)
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
                        when {
                            !sensorEnabled -> filtered.take(50)
                            isMoving -> filtered
                                .sortedBy { distanceBetween(userLocation, it.latLng) }
                                .take(20)
                            else -> filtered.take(50)
                        }
                    }
                    .toMutableList()

                selectedLocation?.let { selected ->
                    if (filteredLocations.none { it.latLng == selected.latLng }) {
                        filteredLocations.add(selected)
                    }
                }

                filteredLocations.forEach { location ->
                    Marker(
                        state = MarkerState(position = location.latLng),
                        title = location.name,
                        snippet = location.description,
                        icon = if (selectedLocation?.latLng == location.latLng)
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

            if (showNearbyList) {
                ModalBottomSheet(
                    onDismissRequest = { showNearbyList = false },
                    sheetState = sheetState,
                    containerColor = mintColor
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Nearby Centers (${nearbyLocations.size})",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            IconButton(
                                onClick = { showNearbyList = false },
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(Color.LightGray.copy(alpha = 0.3f), CircleShape)
                            ){
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close",
                                    tint = Color.Gray
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        LazyColumn(
                            modifier = Modifier.weight(1f)
                        ) {
                            items(nearbyLocations) { location ->

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        Box(
                                            modifier = Modifier
                                                .size(70.dp)
                                                .background(Color.LightGray, RoundedCornerShape(12.dp))
                                        )

                                        Spacer(modifier = Modifier.width(12.dp))

                                        Column(modifier = Modifier.weight(1f)) {

                                            Text(
                                                location.description,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp
                                            )

                                            val distance = distanceBetween(
                                                currentLocation,
                                                location.latLng
                                            ) / 1000

                                            Text(
                                                text = "${"%.1f".format(distance)} km away",
                                                fontSize = 13.sp,
                                                color = Color.Gray
                                            )

                                            Text(
                                                text = location.type,
                                                fontSize = 12.sp,
                                                color = Color.DarkGray
                                            )
                                        }

                                        IconButton(
                                            onClick = {
                                                showNearbyList = false
                                                selectedLocation = location
                                                cameraPositionState.position =
                                                    CameraPosition.fromLatLngZoom(location.latLng, 16f)
                                            }
                                        ) {
                                            Text("➡️")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Surface(
                color = Color.White,
                shape = RoundedCornerShape(24.dp),
                shadowElevation = 6.dp,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(25.dp)
            ) {
                Text(
                    text = when {
                        !sensorEnabled -> "⏸ Sensor OFF: manual mode"
                        isMoving -> "🚶 Moving: showing closest 20 bins"
                        else -> "🧍 Idle: showing more locations"
                    },
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                    color = if (sensorEnabled) greenColor else Color.Gray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            FloatingActionButton(
                onClick = { showNearbyList = true },
                containerColor = mintColor,
                contentColor = greenColor,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 6.dp, bottom = 80.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Icon(Icons.Default.Search, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Nearby")
                }
            }

            // Search Bar + Sensor Button (Top Overlay)
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
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    TextField(
                        value = query,
                        onValueChange = { query = it },
                        placeholder = { Text("Search by location") },
                        modifier = Modifier.weight(1f), // IMPORTANT: take available space
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
                                tint = greenColor
                            )
                        }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Sensor Toggle Button
                    IconButton(
                        onClick = { sensorEnabled = !sensorEnabled },
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                if (sensorEnabled) Color.Red else greenColor,
                                CircleShape
                            )
                    ) {
                        Text(
                            text = if (sensorEnabled) "⏸" else "▶",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }
            }
            }
        }
    }

class FakeSensorManager {

    private val scope = CoroutineScope(Dispatchers.Main)
    private var job: Job? = null

    fun start(onUpdate: (Boolean) -> Unit) {
        if (job != null) return // already running

        job = scope.launch {
            while (true) {
                delay(3000)
                onUpdate(listOf(true, false).random())
            }
        }
    }

    fun stop() {
        job?.cancel()
        job = null
    }
}