package com.mutualmobile.iswipe.android.view.screens.weather_screen.utils

import com.mutualmobile.iswipe.android.R

fun String.getWeatherIndicatorIcon(): Int {
    return when (this) {
        "01d" -> R.drawable.weather_01d
        "01n" -> R.drawable.weather_01n
        "02d" -> R.drawable.weather_02d
        "02n" -> R.drawable.weather_02n
        "03d", "03n" -> R.drawable.weather_03d
        "04d", "04n" -> R.drawable.weather_04d
        "09d", "09n" -> R.drawable.weather_09d
        "10d" -> R.drawable.weather_10d
        "10n" -> R.drawable.weather_10n
        "11d", "11n" -> R.drawable.weather_11d
        "13d", "13n" -> R.drawable.weather_13d
        "50d", "50n" -> R.drawable.weather_50d
        else -> R.drawable.ic_weather
    }
}

fun String.getWeatherBackground(): Triple<Int, WeatherType, Int?> {
    return when (this) {
        "01d", "01n" -> Triple(R.drawable.weather_clear_sky, WeatherType.ClearSky, R.raw.weather_clear_animation)
        "02d", "02n" -> Triple(R.drawable.weather_few_clouds, WeatherType.FewClouds, R.raw.weather_clouds_animation)
        "03d", "03n" -> Triple(R.drawable.weather_scattered_clouds, WeatherType.ScatteredClouds, R.raw.weather_clouds_animation)
        "04d", "04n" -> Triple(R.drawable.weather_broken_clouds, WeatherType.BrokenClouds, R.raw.weather_clouds_animation)
        "09d", "09n" -> Triple(R.drawable.weather_shower_rain, WeatherType.ShowerRain, R.raw.weather_shower_rain_animation)
        "10d", "10n" -> Triple(R.drawable.weather_rain, WeatherType.Rain, R.raw.weather_rain_animation)
        "11d", "11n" -> Triple(R.drawable.weather_thunderstorm, WeatherType.Thunderstorm, R.raw.weather_thunderstorm_animation)
        "13d", "13n" -> Triple(R.drawable.weather_snow, WeatherType.Snow, R.raw.weather_snowfall_animation)
        "50d", "50n" -> Triple(R.drawable.weather_mist, WeatherType.Mist, R.raw.weather_mist_animation)
        else -> Triple(R.drawable.ic_weather, WeatherType.Default, null)
    }
}
