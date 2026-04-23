package com.example.fit5046_a2.sensor

import android.content.Context
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MovementSensorSimulator(private val context: Context) {

    private val _movementState = MutableStateFlow("STILL")
    val movementState: StateFlow<String> = _movementState

    private var job: Job? = null
    private var data: List<String> = emptyList()

    // Load dataset
    private fun loadData() {
        val input = context.resources.openRawResource(
            com.example.fit5046_a2.R.raw.movement_data
        )
        val reader = input.bufferedReader()

        data = reader.readLines()
            .drop(1) // skip header
            .mapNotNull {
                val tokens = it.split(",")
                tokens.getOrNull(1)
            }
    }

    fun start() {
        loadData()

        job = CoroutineScope(Dispatchers.Default).launch {
            var index = 0

            while (isActive && data.isNotEmpty()) {
                _movementState.value = data[index]

                index = (index + 1) % data.size
                delay(3000) // simulate real-time sensor updates
            }
        }
    }

    fun stop() {
        job?.cancel()
    }
}