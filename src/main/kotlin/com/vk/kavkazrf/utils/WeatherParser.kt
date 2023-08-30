package com.vk.kavkazrf.utils

import com.vk.kavkazrf.dto.CurrentWeather
import com.vk.kavkazrf.dto.PredictedWeather
import com.vk.kavkazrf.dto.WeatherInfo
import com.vk.kavkazrf.logger.logger
import org.jsoup.nodes.Document

object WeatherParser {
    fun parseWeather(htmlDoc: Document): WeatherInfo {
        return WeatherInfo(
            title = htmlDoc.select("#wf-location").text(),
            currentWeather = CurrentWeather(
                temperatureValue = htmlDoc.select("td.bigtext").text(),
                windSpeedVal = htmlDoc.select("#table-current .wind-icon__val").text(),
                windDirectionX = htmlDoc.select("#table-current .wind-icon__val").attr("x"),
                windDirectionY = htmlDoc.select("#table-current .wind-icon__val").attr("y"),
            ),
            weatherForecast = parseForecastList(htmlDoc),
        )
    }

    private fun parseForecastList(htmlDoc: Document): List<PredictedWeather> {
        val dayTable = htmlDoc.select("#table-day").first()!!
        val outlookTable = htmlDoc.select("#table-outlook").first()!!
        val tempTable = htmlDoc.select("#table-temp").first()!!
        val windTable = htmlDoc.select("#table-wind").first()!!
        val freezingLevelTable = htmlDoc.select("th:contains(Freezing Level)").first()?.parent()!!

        val tablesLen = dayTable.select("td").size
        logger.info("Table len is $tablesLen")

        return List(tablesLen) {
            PredictedWeather(
                minTemperatureValue = tempTable.select("td")[it].text().split("|")[0].trim(),
                maxTemperatureValue = tempTable.select("td")[it].text().split("|")[1].trim(),
                windSpeedVal = windTable.select("text.wind-icon__val")[it].text(),
                windDirection = degreesToStringPresentation(
                    windTable.select("g.wind-icon__arrow")[it].attr("transform")
                        .split('(', ')')[1].toInt()
                ),
                frozeLevelVal = freezingLevelTable.select("td")[it].text(),
                weatherStatus = outlookTable.select("img")[it].attr("alt"),
            )
        }
    }

    @Suppress("MagicNumber")
    private fun degreesToStringPresentation(degrees: Int): String {
        return when (degrees % 360) {
            in 0..22 -> "North"
            in 23..67 -> "Northeast"
            in 68..112 -> "East"
            in 113..157 -> "Southeast"
            in 158..202 -> "South"
            in 203..247 -> "Southwest"
            in 248..292 -> "West"
            in 293..337 -> "Northwest"
            in 338..359 -> "North"
            else -> "Invalid direction"
        }
    }
}
