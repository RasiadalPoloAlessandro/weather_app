package com.weater_app.weater_app.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.weater_app.weater_app.data.api.weatherApi.weather_data.WeatherPoint
import java.time.LocalDateTime

@SuppressLint("DefaultLocale")
@Composable
fun CreateChart(points: List<WeatherPoint>){

    //Data points aren't sorted
    val sortedPoints = points.sortedBy { it.time }

    val maxPoints = 10
    val filteredPoints = if (sortedPoints.size > maxPoints) {
        sortedPoints.chunked(sortedPoints.size / maxPoints).map { it.first() }
    } else {
        sortedPoints
    }

    // Get the range of all temperatures
    val minTemp = filteredPoints.minOfOrNull { it.temperatureValue } ?: 0f
    val maxTemp = filteredPoints.maxOfOrNull { it.temperatureValue} ?: 30f
    val tempRange = maxTemp - minTemp
    val padding = tempRange * 0.1f // 10% di padding
    val adjustedMin = minTemp - padding
    val adjustedMax = maxTemp + padding

    val steps = 8

    val pointsData: List<Point> = filteredPoints.mapIndexed { index, item ->
        val dateTime = LocalDateTime.parse(item.time.replace(" ", "T"))

        //Normalize all the values
        val normalizedTemp = ((item.temperatureValue - adjustedMin) / (adjustedMax - adjustedMin)) * steps

        Point(index.toFloat(), normalizedTemp)
    }

   val xAxisData = AxisData.Builder()
        .axisStepSize(70.dp)
        .steps(pointsData.size - 1)
        .labelData { i ->
            //Insert all the points
            if (i < filteredPoints.size) {
                val dateTime = LocalDateTime.parse(sortedPoints[i].time.replace(" ", "T"))
                String.format("%02d:%02d", dateTime.hour, dateTime.minute)
            } else {
                ""
            }
        }
        .labelAndAxisLinePadding(0.dp)
        .build()

    /*val yAxisData = AxisData.Builder()
        .steps(steps)
        .labelAndAxisLinePadding(0.dp)
        .labelData { i ->
            // Show the temperatures
            val tempStep = (adjustedMax - adjustedMin) / steps
            val temperature = adjustedMin + (i * tempStep)
            String.format("%.1f°C", temperature)
        }
        .build()*/

    //Prepare the chart
    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(
                        color = Color.Blue,
                        lineType = LineType.SmoothCurve(isDotted = false)
                    ),
                    IntersectionPoint(
                        color = Color.Red,
                        radius = 4.dp
                    ),
                    SelectionHighlightPoint(),
                    ShadowUnderLine(
                        alpha = 0.3f,
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Blue.copy(alpha = 0.3f), Color.Transparent)
                        )
                    ),
                    SelectionHighlightPopUp(
                        popUpLabel = { x, y ->
                            val index = x.toInt()
                            if (index < sortedPoints.size) {
                                val dateTime = LocalDateTime.parse(sortedPoints[index].time.replace(" ", "T"))
                                "${String.format("%02d:%02d", dateTime.hour, dateTime.minute)}\n${String.format("%.1f°C", y)}"
                            } else {
                                val realTemp = adjustedMin + (y / steps) * (adjustedMax - adjustedMin)
                                String.format("%.1f°C", realTemp)
                            }
                        }
                    )
                )
            ),
        ),
        xAxisData = xAxisData,
        //yAxisData = yAxisData,
        //gridLines = GridLines(color = Color.Gray.copy(alpha = 0.3f)),
        backgroundColor = Color.White
    )


        LineChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color.White)
                .clip(RoundedCornerShape(8.dp)), // previene overflow visivo
            lineChartData = lineChartData
        )
    }