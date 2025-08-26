package com.weater_app.weater_app.ui.components

import android.annotation.SuppressLint
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weater_app.weater_app.data.api.weatherApi.weather_data.WeatherPoint
import java.time.LocalDateTime
import kotlin.math.roundToInt

@SuppressLint("DefaultLocale")
@Composable
fun CreateChart(points: List<WeatherPoint>) {
    Chart(points = points)
}




@Composable
fun Chart(
    points: List<WeatherPoint>,
    modifier: Modifier = Modifier
) {
    val sortedPoints = points.sortedBy { it.time }

    if (sortedPoints.isEmpty()) return

    val minTemp = sortedPoints.minOf { it.temperatureValue }
    val maxTemp = sortedPoints.maxOf { it.temperatureValue }
    val tempRange = maxTemp - minTemp
    val padding = tempRange * 0.1f
    val adjustedMin = minTemp - padding
    val adjustedMax = maxTemp + padding

    var selectedPointIndex by remember { mutableIntStateOf(-1) }
    val textColor = MaterialTheme.colorScheme.onBackground.toArgb()


    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val chartLeft = size.width * 0.1f
                    val chartWidth = size.width * 0.8f

                    val pointWidth = chartWidth / (sortedPoints.size - 1)
                    val tappedIndex = ((offset.x - chartLeft) / pointWidth).roundToInt()

                    selectedPointIndex = if (tappedIndex in 0 until sortedPoints.size) {
                        tappedIndex
                    } else {
                        -1
                    }
                }
            }
    ) {
        val width = size.width
        val height = size.height
        val chartWidth = width * 0.8f
        val chartHeight = height * 0.7f
        val chartLeft = width * 0.1f
        val chartTop = height * 0.1f
        val chartRight = chartLeft + chartWidth
        val chartBottom = chartTop + chartHeight


        drawRect(
            color = Color.Transparent,
            topLeft = Offset(0f, 0f),
            size = Size(width, height)
        )

        // Axies
        drawLine(
            color = Color.Transparent,
            start = Offset(chartLeft, chartBottom),
            end = Offset(chartRight, chartBottom),
            strokeWidth = 2.dp.toPx()
        )

        drawLine(
            color = Color.Transparent,
            start = Offset(chartLeft, chartTop),
            end = Offset(chartLeft, chartBottom),
            strokeWidth = 2.dp.toPx()
        )


        val chartPoints = sortedPoints.mapIndexed { index, point ->
            val x = chartLeft + (chartWidth / (sortedPoints.size - 1)) * index
            val normalizedTemp = (point.temperatureValue - adjustedMin) / (adjustedMax - adjustedMin)
            val y = chartBottom - (chartHeight * normalizedTemp)
            Offset(x, y)
        }

        if (chartPoints.size > 1) {
            val areaPath = Path()
            areaPath.moveTo(chartPoints[0].x, chartBottom)
            areaPath.lineTo(chartPoints[0].x, chartPoints[0].y)

            for (i in 1 until chartPoints.size) {
                areaPath.lineTo(chartPoints[i].x, chartPoints[i].y)
            }

            areaPath.lineTo(chartPoints.last().x, chartBottom)
            areaPath.close()

            drawPath(
                path = areaPath,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Green.copy(alpha = 0.3f),
                        Color.Transparent
                    )
                )
            )
        }


        if (chartPoints.size > 1) {
            val path = Path()
            path.moveTo(chartPoints[0].x, chartPoints[0].y)

            for (i in 1 until chartPoints.size) {
                path.lineTo(chartPoints[i].x, chartPoints[i].y)
            }

            drawPath(
                path = path,
                color = Color.Black,
                style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
            )
        }


        chartPoints.forEachIndexed { index, point ->
            val isSelected = index == selectedPointIndex
            drawCircle(
                color = if (isSelected) Color.Yellow else Color.White,
                radius = if (isSelected) 6.dp.toPx() else 4.dp.toPx(),
                center = point
            )


            if (isSelected) {
                val dateTime = LocalDateTime.parse(sortedPoints[index].time.replace(" ", "T"))
                val timeText = "%02d:%02d".format(dateTime.hour, dateTime.minute)
                val tempText = "%.1f°C".format(sortedPoints[index].temperatureValue)


                drawRoundRect(
                    color = Color.Transparent.copy(alpha = 0.8f),
                    topLeft = Offset(point.x - 40.dp.toPx(), point.y - 60.dp.toPx()),
                    size = Size(80.dp.toPx(), 40.dp.toPx()),
                    cornerRadius = CornerRadius(8.dp.toPx())
                )

                val textX = point.x
                val textY = point.y - 60.dp.toPx() + 25.dp.toPx()

                drawContext.canvas.nativeCanvas.drawText(
                    "${sortedPoints.elementAt(index).temperatureValue}°C",
                    textX,
                    textY,
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.WHITE
                        textSize = 14.sp.toPx()
                        textAlign = android.graphics.Paint.Align.CENTER
                        isAntiAlias = true
                    }
                )

            }
        }

        // X axe
        sortedPoints.forEachIndexed { index, point ->
            if (index % 2 == 0) {
                val dateTime = LocalDateTime.parse(point.time.replace(" ", "T"))
                val timeText = "%02d:%02d".format(dateTime.hour, dateTime.minute)
                val x = chartLeft + (chartWidth / (sortedPoints.size - 1)) * index
                drawContext.canvas.nativeCanvas.drawText(
                    timeText,
                    x,
                    chartBottom + 20.dp.toPx(),
                    Paint().apply {
                        color = textColor
                        textSize = 10.sp.toPx()
                        textAlign = Paint.Align.CENTER
                    }
                )
            }
        }
    }
}