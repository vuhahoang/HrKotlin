package com.example.hrkotlin.network

import com.example.hrkotlin.MainActivity
import com.example.hrkotlin.base.log.Logger
import com.example.hrkotlin.common.pref.SharePrefManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object ServiceBuilder {
    private var sInstance: Retrofit? = null
    private val BASE_URL: String = "http://18.141.247.31:9001/"
    private var sService: OttService? = null
    private val TYPE_BASE = 1

    @Synchronized
    private fun getRetrofit(): Retrofit? {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .retryOnConnectionFailure(true)
            .addInterceptor(Interceptor { chain ->


                val original = chain.request()
                val userAgent = System.getProperty("http.agent")
                val builder: Request.Builder = original.newBuilder()
                builder.addHeader("Content-Type", "application/json")
                builder.addHeader("User-Agent", userAgent)
                builder.addHeader("freedata", "1")
                if (SharePrefManager.getIsLogin(MainActivity.getInstance())) {
                    Logger.e("havu","vào đây chưa")
                    builder.addHeader("Authorization", "Bearer " + SharePrefManager.getAccessToken(MainActivity.getInstance()))
                }
                var request: Request = builder.method(original.method, original.body).build()
                val currentUrl = request.url.toString()
                val subUrl: String? = getLastString(currentUrl)
//                if (subUrl != null) {
//                    request = request.newBuilder().url(subUrl).build()
//                }
                Logger.e("minhpc", "ServiceBuilder request==$request")
                chain.proceed(request)
            })
            .build()
        if (sInstance == null) {
            // User agent
            sInstance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return sInstance
    }

    @Synchronized
    public fun getService(): OttService? {
        if (sService == null) {
            sService = getRetrofit()?.create(OttService::class.java)
        }
        return sService
    }


    private fun getLastString(url: String?): String? {
        if (url == null || url.length == 0) {
            return null
        }
        if (url.contains("/public/v1")) {
            return url.substring(url.indexOf("/public/v1"))
        } else if (url.contains("/api/v1")) {
            return url.substring(url.indexOf("/api/v1"))
        } else if (url.contains("/account/")) {
            return url.substring(url.indexOf("/account/"))
        } else if (url.contains("/default/")) {
            return url.substring(url.indexOf("/default/"))
        } else if (url.contains("/auth/")) {
            return url.substring(url.indexOf("/auth/"))
        }
        return null
    }

    private fun getDomainToCall(type: Int, currentUrl: String): String? {
        if (currentUrl == null) {
            return null
        }
        return null
    }

    private fun getMatchingSubstring(str: String, substrings: List<String>): String? {
        for (substring in substrings) {
            if (str.contains(substring)) {
                return substring
            }
        }
        return null
    }
}
