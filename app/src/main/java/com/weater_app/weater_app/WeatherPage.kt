package com.weater_app.weater_app

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun WeatherPage() {

    var city by remember {
        mutableStateOf("")
    }


    Column {
        WeatherTopBar()
        Spacer(modifier = Modifier.height(32.dp))
        WeatherMainCard()
        Spacer(modifier = Modifier.height(32.dp))
        WeatherWarning()
        Spacer(modifier = Modifier.height(40.dp))
        CreateChart()
        Spacer(modifier = Modifier.height(40.dp))
        WeatherAttributes()
    }
}
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


@Composable
fun WeatherWarning(){

    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier
                .border(
                    width = 3.dp,
                    color = Color.Yellow,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Warning",
                modifier = Modifier.size(20.dp)
            )

            Text(
                text = "PERICOLO ALTE TEMPERATURE",
                fontSize = 20.sp,
            )
        }
    }
}

@Composable
fun WeatherMainCard() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Location",
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Dublin",
            fontSize = 30.sp
        )
    }
    Spacer(modifier = Modifier.height(32.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "20°C",
            fontSize = 60.sp
        )
    }
}

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
