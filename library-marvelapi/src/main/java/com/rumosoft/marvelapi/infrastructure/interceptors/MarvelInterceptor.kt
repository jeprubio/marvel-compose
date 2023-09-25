package com.rumosoft.marvelapi.infrastructure.interceptors

import com.rumosoft.maverlapi.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException
import java.math.BigInteger
import java.security.MessageDigest

class MarvelInterceptor : Interceptor {
    private val apiKey = BuildConfig.MARVEL_PUBLIC_KEY

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val tsLong = System.currentTimeMillis() / 1000
        val ts = tsLong.toString()
        var request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("ts", ts)
            .addQueryParameter("apikey", apiKey)
            .addQueryParameter("hash", hash(ts))
            .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }

    fun hash(ts: String): String {
        val md = MessageDigest.getInstance("MD5")
        val input = ts + BuildConfig.MARVEL_PRIVATE_KEY + apiKey

        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}
