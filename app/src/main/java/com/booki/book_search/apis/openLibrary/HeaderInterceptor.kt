package com.booki.book_search.apis.openLibrary

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestWithHeaders = originalRequest.newBuilder()
            .header("App-Name", "Booki")
            .header("Email", "robin.skaba@gmail.com")
            .build()
        return chain.proceed(requestWithHeaders)
    }
}