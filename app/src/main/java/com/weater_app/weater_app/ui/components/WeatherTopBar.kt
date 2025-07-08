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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/*TopBar
* Components:
* Add City
* See all cities
* */

@Composable
fun WeatherTopBar() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = {/*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = "Add Location"
            )
        }

        IconButton(onClick =  {/*TODO*/ }){
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Open Menu"
            )
        }
    }
}