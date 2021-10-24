package com.rumosoft.marvelcompose.infrastructure.interceptors

import com.rumosoft.marvelcompose.BuildConfig
import java.math.BigInteger
import java.security.MessageDigest
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException

class MarvelInterceptor : Interceptor {
    private val apiKey = "cfcb9596194b34c19e3f80cb295d33a1"

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
        val input = ts + BuildConfig.MARVEL_KEY + apiKey

        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}