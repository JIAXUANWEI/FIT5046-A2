package com.example.fit5046_a2.model

import com.google.android.gms.maps.model.LatLng

data class SensorLocation(
    val id: Int,
    val name: String,
    val description: String,
    val latLng: LatLng,
    val type: String,
    val status: String,
    val locationText: String
)
