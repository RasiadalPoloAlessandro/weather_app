package com.weater_app.weater_app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.weater_app.weater_app.navigation.Routes

/*TopBar
* Components:
* Add City
* See all cities
* */

@Composable
fun WeatherTopBar(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = {
            navController.navigate(Routes.weather_CitySelection)
        }) {
            Icon(
                imageVector = Icons.Default.AddCircle,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = "Add Location"
            )
        }

        IconButton(onClick =  {

        }){
            Icon(
                imageVector = Icons.Default.Menu,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = "Open Menu"
            )
        }
    }
}