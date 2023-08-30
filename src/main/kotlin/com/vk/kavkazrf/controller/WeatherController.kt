package com.vk.kavkazrf.controller

import com.vk.kavkazrf.dto.WeatherConfiguration
import com.vk.kavkazrf.dto.WeatherInfo
import com.vk.kavkazrf.utils.WeatherSender
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/weather")
@Validated
class WeatherController {
    @GetMapping("/get")
    fun getWeather(
        @RequestParam(name = "level") level: String,
        @RequestParam(name = "location", required = false) location: String?,
        @RequestParam(name = "system", required = false) system: String?,
    ): WeatherInfo {
        // build weather object
        val weather = WeatherConfiguration(
            level = level,
            location = location ?: "Mount-Elbrus",
            system = system ?: "m",
        )

        return WeatherSender.getWeather(weather)
    }
}
