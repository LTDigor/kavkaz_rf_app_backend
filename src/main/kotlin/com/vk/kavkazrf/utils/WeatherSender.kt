package com.vk.kavkazrf.utils

import com.vk.kavkazrf.dto.WeatherConfiguration
import com.vk.kavkazrf.dto.WeatherInfo
import org.jsoup.Jsoup


object WeatherSender {
    private const val BASE_URL = "https://www.snow-forecast.com/resorts"
    fun getWeather(weather: WeatherConfiguration): WeatherInfo {
        val url = "$BASE_URL/${weather.location}/forecasts/feed/${weather.level}/${weather.system}"
        return WeatherParser.parseWeather(Jsoup.connect(url).get())
    }
}
