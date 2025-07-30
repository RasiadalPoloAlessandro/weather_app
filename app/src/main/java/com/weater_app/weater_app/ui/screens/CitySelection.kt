package com.weater_app.weater_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.weater_app.weater_app.data.models.WeatherViewModel
import com.weater_app.weater_app.navigation.Routes


@Composable
fun CitySelectionPage(navController: NavController, viewModel: WeatherViewModel){

    var city by remember {
        mutableStateOf("")
    }

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
                //TODO implement city research
                viewModel.getWeather(city)
                navController.navigate(Routes.weatherPage)
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = "Search for any location")
            }
        }
    }

}