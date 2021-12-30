package com.mutualmobile.iswipe.android.utils

import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import org.schabi.newpipe.extractor.downloader.Downloader
import org.schabi.newpipe.extractor.downloader.Request
import org.schabi.newpipe.extractor.downloader.Response
import org.schabi.newpipe.extractor.exceptions.ReCaptchaException
import java.util.concurrent.TimeUnit

class DownloaderImpl constructor(
    okHttpClientBuilder: OkHttpClient.Builder
) : Downloader() {
    companion object {
        const val USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; rv:78.0) Gecko/20100101 Firefox/78.0"
        const val YOUTUBE_RESTRICTED_MODE_COOKIE_KEY = "youtube_restricted_mode_key"
        const val RECAPTCHA_COOKIES_KEY = "recaptcha_cookies"
        const val YOUTUBE_DOMAIN = "youtube.com"
    }

    private val client = okHttpClientBuilder
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val mCookies: Map<String, String> = hashMapOf()

    private fun getCookies(url: String): String {
        val tempCookieList = mutableListOf<String>()

        if (url.contains(YOUTUBE_DOMAIN) && mCookies.contains(YOUTUBE_RESTRICTED_MODE_COOKIE_KEY)) {
            tempCookieList.add(mCookies.getValue(YOUTUBE_RESTRICTED_MODE_COOKIE_KEY))
        }
        if (mCookies.contains(RECAPTCHA_COOKIES_KEY)) {
            tempCookieList.add(mCookies.getValue(RECAPTCHA_COOKIES_KEY))
        }

        return tempCookieList.joinToString { "; " }
    }

    override fun execute(request: Request): Response {
        val httpMethod = request.httpMethod()
        val url = request.url()
        val headers = request.headers()
        val dataToSend = request.dataToSend()

        val requestBody = dataToSend?.toRequestBody(null, 0)

        val requestBuilder = okhttp3.Request.Builder()
            .method(httpMethod, requestBody)
            .url(url)
            .addHeader("User-Agent", USER_AGENT)

        val cookies = getCookies(url = url)
        if (cookies.isNotBlank()) {
            requestBuilder.addHeader("Cookie", cookies)
        }

        headers.entries.forEach { pair ->
            val headerName = pair.key
            val headerValueList = pair.value

            when {
                headerValueList.size > 1 -> {
                    requestBuilder.removeHeader(headerName)
                    headerValueList.forEach { headerValue ->
                        requestBuilder.addHeader(headerName, headerValue)
                    }
                }
                headerValueList.size == 0 -> {
                    requestBuilder.header(headerName, headerValueList[0])
                }
            }
        }

        val response = client.newCall(requestBuilder.build()).execute()

        if (response.code == 429) {
            response.close()
            throw ReCaptchaException("reCaptcha Challenge requested", url)
        }

        val body = response.body
        val responseBodyToReturn = body?.string()

        val latestUrl = response.request.url.toString()
        return Response(
            response.code,
            response.message,
            response.headers.toMultimap(),
            responseBodyToReturn,
            latestUrl
        )
    }
}
