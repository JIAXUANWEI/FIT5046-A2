package com.example.fit5046_a2.data

import android.content.Context
import com.example.fit5046_a2.R
import com.google.android.gms.maps.model.LatLng

fun readRecyclingData(context: Context): List<LatLng> {
    val inputStream = context.resources.openRawResource(R.raw.recycling_bins)
    val reader = inputStream.bufferedReader()

    val locations = mutableListOf<LatLng>()

    reader.readLine() // skip header

    reader.forEachLine { line ->
        val tokens = line.split(",")

        try {
            val lat = tokens[4].toDouble()
            val lng = tokens[5].toDouble()

            // FAKE move to Melbourne
            val melLat = -37.8136 + (Math.random() - 0.5) * 0.05
            val melLng = 144.9631 + (Math.random() - 0.5) * 0.05

            locations.add(LatLng(melLat, melLng))

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    return locations
}