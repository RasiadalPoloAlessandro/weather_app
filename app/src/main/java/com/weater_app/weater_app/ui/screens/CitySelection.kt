package com.weater_app.weater_app.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.weater_app.weater_app.data.controllers.CityController
import com.weater_app.weater_app.data.controllers.WeatherController
import com.weater_app.weater_app.navigation.Routes

@Composable
fun CitySelectionPage(navController: NavController, viewModel: WeatherController, cityViewModel: CityController){

    var city by remember {
        mutableStateOf("")
    }

    val uiState by cityViewModel.uiState.collectAsState()

    Column (
        modifier = Modifier.fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp),
        horizontalAlignment =  Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ){
            OutlinedTextField(
                modifier =
                    Modifier.weight(1f)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                value = city,
                onValueChange = {
                    city = it
                    cityViewModel.getCityInformation(city)
                },
                label = {
                    Text(
                        text = "Cerca la città",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
            )

            IconButton(onClick = {
                /*Search for cities
                Once I've selected the city, it'll change page and call the api
                */
                viewModel.getWeather(city)
                navController.navigate(Routes.firstPage)
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = "Search for any location")
            }
        }

        // Lista delle città con ciclo forEach
        val cities = uiState.cityData
        //Log.e("Valore" , cities.toString())
        if (!cities.isNullOrEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(cities) { cityItem ->
                    Log.e("Città trovata: ", cityItem.name)
                    CityCard(
                        cityName = cityItem.name,
                        country = cityItem.country,
                        state = cityItem.state ?: "",
                        onClick = {
                            viewModel.getWeather(cityItem.name  )
                            navController.navigate(Routes.firstPage)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CityCard(
    cityName: String,
    country: String,
    state: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = cityName,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (state.isNotEmpty()) {
                Text(
                    text = "$state, $country",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                Text(
                    text = country,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}