package com.vk.kavkazrf.dto

data class WeatherInfo(
    val title: String,
    val currentWeather: CurrentWeather,
    val weatherForecast: List<PredictedWeather>,
)

data class PredictedWeather(
    val minTemperatureValue: String,
    val maxTemperatureValue: String,
    val windSpeedVal: String,
    val windDirection: String,
    val frozeLevelVal: String,
    val weatherStatus: String,
)

data class CurrentWeather(
    val temperatureValue: String,
    val windSpeedVal: String,
    val windDirectionX: String,
    val windDirectionY: String,
)
