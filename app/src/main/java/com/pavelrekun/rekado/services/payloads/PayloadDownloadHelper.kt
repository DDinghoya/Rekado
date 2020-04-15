package com.pavelrekun.rekado.services.payloads

import com.pavelrekun.rekado.data.Schema
import com.pavelrekun.rekado.services.Constants
import com.pavelrekun.rekado.services.interceptors.NoConnectionInterceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import ru.gildor.coroutines.okhttp.await

object PayloadDownloadHelper {

    private val client = OkHttpClient.Builder()
            .addInterceptor(NoConnectionInterceptor())
            .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
            .build()

    suspend fun fetchExternalSchema(): Response {
        val request = Request.Builder().url(Constants.PAYLOADS_EXTERNAL_SCHEMA).build()
        return client.newCall(request).await()
    }

    suspend fun downloadPayload(downloadUrl: String): Response {
        val request = Request.Builder().url(downloadUrl).build()
        return client.newCall(request).await()
    }

    enum class Result {
        SUCCESS, ERROR;

        lateinit var schema: Schema
    }

}