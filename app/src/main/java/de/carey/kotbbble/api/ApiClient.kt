package de.carey.kotbbble.api

import android.content.Context
import de.carey.kotbbble.BuildConfig
import de.carey.kotbbble.app.App
import de.carey.kotbbble.util.Utils
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient private constructor() {

    private lateinit var mRetrofit: Retrofit

    init {
        createRetrofit(App.instance)
    }

    companion object {
        const val BASE_URL = "https://api.dribbble.com/v1/"
        const val WEBSITE_BASE_URL = "https://dribbble.com/"
        const val TIMEOUT = 20L
        val instance = Holder.Instance
    }

    private object Holder {
        val Instance = ApiClient()
    }

    private fun createRetrofit(context: Context) {
        mRetrofit = Retrofit.Builder()
                .client(constructClient(context))
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    fun createWebsiteRetrofit(): ApiStore {
        return Retrofit.Builder()
                .client(constructClient(App.instance))
                .baseUrl(WEBSITE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiStore::class.java)
    }

    private fun constructClient(context: Context): OkHttpClient {
        val cacheSize: Long = 10 * 1024 * 1024
        val file = context.externalCacheDir

        val loggerInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
        else loggerInterceptor.level = HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(loggerInterceptor)
                .cache(Cache(file, cacheSize))
                .addInterceptor(getInterceptor())
                .addNetworkInterceptor(CacheInterceptor())
                .addNetworkInterceptor(TokenInterceptor())
                .retryOnConnectionFailure(true)
                .build()
    }

    private fun getInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            if (!Utils.isNetworkAvailable(App.instance)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
            }
            chain.proceed(request)
        }
    }

    fun getApiStore(): ApiStore = mRetrofit.create(ApiStore::class.java)!!
}