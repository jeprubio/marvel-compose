package com.rumosoft.marvelapi.infrastructure.interceptors

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import timber.log.Timber

class LoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        val t1 = System.nanoTime()
        Timber.i(
            String.format(
                "API Sending request %s on %s%n%s",
                request.url,
                chain.connection(),
                request.headers,
            ),
        )
        val response: Response = chain.proceed(request)
        val t2 = System.nanoTime()
        Timber.i(
            String.format(
                "API Received response for %s in %.1fms%n%s",
                response.request.url,
                (t2 - t1) / 1e6,
                response.headers,
            ),
        )
        val bodyString = response.body?.string()

        Timber.d("API response: $bodyString")

        return response.newBuilder()
            .body(bodyString?.toResponseBody(response.body?.contentType()))
            .build()
    }
}
