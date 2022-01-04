package com.mutualmobile.iswipe.android.view.screens.weather_screen.utils

import java.math.RoundingMode

fun Double.kelvinToCelsius(): String {
    return "${this.minus(273.15).toBigDecimal().setScale(1, RoundingMode.UP)}°"
}

fun Int.meterToKilometre(): String =
    "${this.toFloat().div(1000).toString().subSequence(0, if (this.toString().lastIndex > 5) 5 else this.toString().lastIndex)} km"

fun Int.hpaToBar(): String = "${this.toFloat().div(1000).toString().subSequence(0, 5)} bar"
