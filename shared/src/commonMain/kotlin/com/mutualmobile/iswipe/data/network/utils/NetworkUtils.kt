package com.mutualmobile.iswipe.data.network.utils

object NetworkUtils {
    const val WEATHER_API_KEY = "6bf5e395a1bce8e879a6175a06006663"
    const val GENERIC_ERROR_MSG = "Unexpected error occurred!"

    fun getCurrentWeatherApiUrl(city: String = "delhi", apiKey: String = WEATHER_API_KEY): String {
        return "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$apiKey"
    }
}

suspend fun <T> safeApiCall(block: suspend () -> T): Either<T, String> {
    kotlin.runCatching { block() }
        .onSuccess {
            return Either.Type(type = it)
        }
        .onFailure { error ->
            error.message?.let { nnErrorMsg ->
                return Either.Error(errorMsg = nnErrorMsg)
            }
        }
    return Either.Error(errorMsg = NetworkUtils.GENERIC_ERROR_MSG)
}

sealed class Either<A, B> {
    class Type<A, B>(val type: A) : Either<A, B>()
    class Error<A, B>(val errorMsg: B) : Either<A, B>()
}
