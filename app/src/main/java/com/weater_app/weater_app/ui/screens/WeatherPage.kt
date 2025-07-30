package com.weater_app.weater_app.ui.screens

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.weater_app.weater_app.data.api.NetWorkResponse
import com.weater_app.weater_app.data.location.Location_Manager
import com.weater_app.weater_app.data.models.WeatherViewModel
import com.weater_app.weater_app.ui.components.CreateChart
import com.weater_app.weater_app.ui.components.WeatherAttributes
import com.weater_app.weater_app.ui.components.WeatherMainCard
import com.weater_app.weater_app.ui.components.WeatherTopBar
import com.weater_app.weater_app.ui.components.WeatherWarning
import kotlinx.coroutines.launch


@SuppressLint("PermissionLaunchedDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WeatherPage(navController: NavController, viewModel: WeatherViewModel) {
    val context = LocalContext.current

    val locationManager = remember {
        Location_Manager(
            context = context,
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        )
    }

    val locationPermission = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    var location by remember {
        mutableStateOf<Location?>(null)
    }

    // Gestione dei permessi
    LaunchedEffect(locationPermission.allPermissionsGranted) {
        if (locationPermission.allPermissionsGranted) {
            location = locationManager.getLocation()
        }
    }

    // Richiesta permessi
    LaunchedEffect(Unit) {
        if (!locationPermission.allPermissionsGranted) {
            locationPermission.launchMultiplePermissionRequest()
        }
    }

    val uiState by viewModel.uiState.collectAsState()

    // Chiamata API quando la location cambia
    LaunchedEffect(location) {
        location?.let {
            viewModel.getWeatherByCoordinates(it.latitude, it.longitude)
        }
    }

    // 🔹 Qui applichiamo il background dal tema attivo
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when {
            // Stato permessi
            !locationPermission.allPermissionsGranted -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "Permessi di localizzazione necessari",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { locationPermission.launchMultiplePermissionRequest() },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Concedi Permessi")
                    }
                }
            }

            // Stato con dati caricati
            uiState.weatherData != null -> {
                val data = uiState.weatherData

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    WeatherTopBar(navController)
                    Spacer(modifier = Modifier.height(32.dp))

                    data?.let {
                        WeatherMainCard(it.city, it.temperature, it.description)
                        Spacer(modifier = Modifier.height(32.dp))
                        CreateChart(it.temperatures)
                        Spacer(modifier = Modifier.height(40.dp))
                        WeatherAttributes(
                            data.humidity,
                            data.windSpeed,
                            data.pressure,
                            data.visibility
                        )
                    }
                }
            }

            // Stato errore
            uiState.errorMessage != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Errore: ${uiState.errorMessage}",
                        color = MaterialTheme.colorScheme.error, // <-- colore errore del tema
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            location?.let {
                                viewModel.getWeatherByCoordinates(it.latitude, it.longitude)
                            }
                        }
                    ) {
                        Text("Riprova")
                    }
                }
            }

            // Stato loading
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }

            else -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}
