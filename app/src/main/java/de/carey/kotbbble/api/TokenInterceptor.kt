package de.carey.kotbbble.api

import de.carey.kotbbble.application.Constants
import de.carey.kotbbble.util.SPUtils
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        val builder = chain!!.request().newBuilder()
        val token = SPUtils.instance.getString(Constants.SP_ACCESS_TOKEN, Constants.CLIENT_ACCESS_TOKEN)
        builder.header("Authorization", "Bearer $token")
        return chain.proceed(builder.build())
    }
}