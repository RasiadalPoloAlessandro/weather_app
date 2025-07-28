package com.weater_app.weater_app.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

    val pointsData: List<Point> = sortedPoints.mapIndexed { index, item ->
        val dateTime = LocalDateTime.parse(item.time.replace(" ", "T"))
        val hourFloat = dateTime.hour + (dateTime.minute / 60f)

        //It has to have regular intervals
        Point(index.toFloat(), item.temperatureValue)
    }

    // Get the range of all temperatures
    val minTemp = pointsData.minOfOrNull { it.y } ?: 0f
    val maxTemp = pointsData.maxOfOrNull { it.y } ?: 30f
    val tempRange = maxTemp - minTemp
    val padding = tempRange * 0.1f // 10% di padding
    val adjustedMin = minTemp - padding
    val adjustedMax = maxTemp + padding

    val xAxisData = AxisData.Builder()
        .axisStepSize(100.dp)
        .steps(pointsData.size - 1)
        .labelData { i ->
            //Insert all the points
            if (i < sortedPoints.size) {
                val dateTime = LocalDateTime.parse(sortedPoints[i].time.replace(" ", "T"))
                String.format("%02d:%02d", dateTime.hour, dateTime.minute)
            } else {
                ""
            }
        }
        .labelAndAxisLinePadding(15.dp)
        .build()

    val steps = 8
    val yAxisData = AxisData.Builder()
        .steps(steps)
        .labelAndAxisLinePadding(20.dp)
        .labelData { i ->
            // Show the temperatures
            val tempStep = (adjustedMax - adjustedMin) / steps
            val temperature = adjustedMin + (i * tempStep)
            String.format("%.1f°C", temperature)
        }
        .build()

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
                                String.format("%.1f°C", y)
                            }
                        }
                    )
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(color = Color.Gray.copy(alpha = 0.3f)),
        backgroundColor = Color.White
    )

    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        lineChartData = lineChartData
    )
}