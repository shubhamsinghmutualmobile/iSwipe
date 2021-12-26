package com.mutualmobile.iswipe.data.network.utils

object NetworkUtils {
    const val WEATHER_API_KEY = "6bf5e395a1bce8e879a6175a06006663"
    const val GENERIC_ERROR_MSG = "Unexpected error occurred!"
    const val YOUTUBE_DATA_API_KEY = "AIzaSyBq6FuPOlmgsXycRmibShB1BjDRjvqzSsw"

    fun getCurrentWeatherApiUrl(city: String = "delhi", apiKey: String = WEATHER_API_KEY): String {
        return "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$apiKey"
    }

    fun getTrendingYoutubeVideosUrl(countryCode: String, apiKey: String, pageToken: String): String {
        return "https://youtube.googleapis.com/youtube/v3/videos?part=statistics%2Csnippet&chart=mostPopular&regionCode=$countryCode&key=$apiKey&pageToken=$pageToken"
    }
}

suspend fun <T> safeApiCall(block: suspend () -> T): Either<T, String> {
    runCatching { block() }
        .onSuccess {
            return Either.Type(data = it)
        }
        .onFailure { error ->
            error.message?.let { nnErrorMsg ->
                return Either.Error(errorMsg = nnErrorMsg)
            }
        }
    return Either.Error(errorMsg = NetworkUtils.GENERIC_ERROR_MSG)
}

sealed class Either<A, B> {
    class Type<A, B>(val data: A) : Either<A, B>()
    class Error<A, B>(val errorMsg: B) : Either<A, B>()
}
