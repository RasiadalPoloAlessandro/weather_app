package com.weater_app.weater_app.data.cache

import co.yml.charts.common.extensions.isNotNull
import com.weater_app.weater_app.data.api.NetWorkResponse
import com.weater_app.weater_app.data.api.weatherApi.WeatherApi
import com.weater_app.weater_app.data.api.weatherApi.city_data.CityResponse

class GetCity(
    private val weatherApi: WeatherApi
) {

    suspend fun getCities(city: String) : NetWorkResponse<List<CityResponse>> {
        return try {
            val response = weatherApi.getCityInformation(city)
            if (response.isSuccessful) {

                response.body()?.let { apiResponseList ->
                    if (apiResponseList.isNotNull() && apiResponseList.isNotEmpty()) {

                        // Mappa ogni elemento della lista API a CityResponse
                        val cityDataList = apiResponseList.map { apiResponse ->
                            CityResponse(
                                name = apiResponse.name,
                                country = apiResponse.country,
                                state = apiResponse.state,
                                lat = apiResponse.lat,
                                lon = apiResponse.lon
                            )
                        }

                        NetWorkResponse.success(cityDataList)
                    } else {
                        NetWorkResponse.Error("No cities found")
                    }
                } ?: NetWorkResponse.Error("Empty response body")

            } else {
                NetWorkResponse.Error("API Error: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            NetWorkResponse.Error("Network error: ${e.message}")
        }
    }
}