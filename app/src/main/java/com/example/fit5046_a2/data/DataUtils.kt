package com.example.fit5046_a2.data

import android.content.Context
import com.example.fit5046_a2.R
import com.example.fit5046_a2.model.SensorLocation
import com.google.android.gms.maps.model.LatLng

fun readRecyclingData(context: Context): List<SensorLocation> {
    val inputStream = context.resources.openRawResource(R.raw.recycling_bins)
    val reader = inputStream.bufferedReader()

    val locations = mutableListOf<SensorLocation>()

    reader.readLine() // skip header

    reader.forEachLine { line ->
        val tokens = line.split(",")

        try {
            val id = tokens[0].toInt()
            val description = tokens[1]
            val name = tokens[2]
            val type = tokens[5]
            val status = tokens[6]
            val lat = tokens[9].toDouble()
            val lng = tokens[10].toDouble()
            val locationText = tokens[11]

            locations.add(
                SensorLocation(
                    id = id,
                    name = name,
                    description = description,
                    latLng = LatLng(lat, lng),
                    type = type,
                    status = status,
                    locationText = locationText
                )
            )

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    return locations
}