package com.weater_app.weater_app.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine

    @SuppressLint("DefaultLocale")
    @Composable
    fun CreateChart(){

        //TODO Take temperatures from Json
        val pointsData: List<Point> =
            listOf(Point(0f, 40f), Point(1f, 90f), Point(2f, 0f), Point(3f, 60f), Point(4f, 10f))

        val xAxisData = AxisData.Builder()
            .axisStepSize(100.dp)
            .steps(pointsData.size - 1)
            .labelData { i -> i.toString() }
            .labelAndAxisLinePadding(15.dp)
            .build()

        val steps = 8 //Number of divisions, a weather chart may requires 8-10 divisions
        val yAxisData = AxisData.Builder()
            .steps(steps)
            .labelAndAxisLinePadding(20.dp)
            .labelData { i ->
                val yScale = 100f / steps
                String.format("%.1f", i * yScale)
            }
            .build()

        val lineChartData = LineChartData(
            linePlotData = LinePlotData(
                lines = listOf(
                    Line(
                        dataPoints = pointsData,
                        LineStyle(),
                        IntersectionPoint(),
                        SelectionHighlightPoint(),
                        ShadowUnderLine(),
                        SelectionHighlightPopUp()
                    )
                ),
            ),
            xAxisData = xAxisData,
            yAxisData = yAxisData,
            gridLines = GridLines(),
            backgroundColor = Color.White
        )

        LineChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            lineChartData = lineChartData
        )
    }