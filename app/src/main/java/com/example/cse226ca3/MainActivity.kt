package com.example.cse226ca3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RideStatusScreen()
                }
            }
        }
    }
}

data class RiderShipDetails(
    val driverName: String = "Sumit",
    val time: String = "5:30 pm",
    val location: String = "Reliance Digital"
)

@Preview(showBackground = true)
@Composable
fun RideStatusScreen() {
    val search by remember { mutableStateOf("Searching for Driver") }
    val assign by remember { mutableStateOf("Driver Assigned") }
    val arrive by remember { mutableStateOf("Driver Arriving") }
    val rideStarted by remember { mutableStateOf("Ride Started") }
    val rideCompleted by remember { mutableStateOf("Ride Completed ✅") }

    var isAssigned by remember { mutableStateOf(false) }
    var isCompleted by remember { mutableStateOf(false) }
    var status by remember { mutableStateOf(false) }
    var currentStatusText by remember { mutableStateOf("") }

    val details = remember { RiderShipDetails() }

    LaunchedEffect(status) {
        if (status) {
            currentStatusText = search
            delay(1000)

            currentStatusText = assign
            isAssigned = true
            delay(2000)

            if (isAssigned) {
                currentStatusText = arrive
                delay(1500)

                currentStatusText = rideStarted
                delay(2000)

                currentStatusText = rideCompleted
                isCompleted = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Welcome To Online Ridership\nStatus",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Status: $currentStatusText",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { status = true },
                enabled = !isCompleted
            ) {
                Text(if (isCompleted) "Ride Completed ✅" else "Check Status")
            }

            if (isCompleted) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Driver ${details.driverName} dropped you at\n${details.location} at ${details.time}",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}