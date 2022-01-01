package com.mutualmobile.iswipe.android.view.screens.weather_screen.utils

fun Double.kelvinToCelsius(): String {
    return "${this.minus(273.15).toString().subSequence(0, if (this < 283.15) 3 else 4)}°"
}

fun Int.meterToKilometre(): String = "${this.toFloat().div(1000).toString().subSequence(0,5)} km"

fun Int.hpaToBar(): String = "${this.toFloat().div(1000).toString().subSequence(0,5)} bar"
