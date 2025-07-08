package com.weater_app.weater_app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WeatherAttributes(){
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        // Prima colonna
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Precipitazioni",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Text(
                text = "20°C",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Umidità",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Text(
                text = "65%",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Vento",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Text(
                text = "15 km/h",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Seconda colonna
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Pressione",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Text(
                text = "1013 hPa",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Visibilità",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Text(
                text = "10 km",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "UV Index",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Text(
                text = "Moderato",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}