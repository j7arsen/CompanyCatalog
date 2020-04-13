package com.j7arsen.companycatalog.di.app.module

import com.j7arsen.companycatalog.BuildConfig
import com.j7arsen.companycatalog.app.CONNECT_TIMEOUT
import com.j7arsen.companycatalog.app.READ_TIMEOUT
import com.j7arsen.companycatalog.app.WRITE_TIMEOUT
import com.j7arsen.companycatalog.di.app.AppScope
import com.j7arsen.companycatalog.utils.ResourceProvider
import com.j7arsen.companycatalog.utils.error.ErrorHandler
import com.j7arsen.data.api.BASE_URL
import com.j7arsen.data.api.ICompanyService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


@Module
class ApiModule {

    @Provides
    @AppScope
    fun provideApi(retrofit: Retrofit): ICompanyService =
        retrofit.create(ICompanyService::class.java)

    @AppScope
    @Provides
    fun provideErrorHandler(resourceProvider: ResourceProvider) = ErrorHandler(resourceProvider)

    @Provides
    @AppScope
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()

    @AppScope
    @Provides
    fun moshi(): Moshi {
        val moshi = Moshi.Builder()
        return moshi.build()
    }

    @AppScope
    @Provides
    fun okHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor?): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    var request: Request = chain.request()
                    request = request.newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept", "application/json")
                        .build()
                    return chain.proceed(request)
                }
            })
            if (BuildConfig.DEBUG) {
                addNetworkInterceptor(httpLoggingInterceptor!!)
            }
        }.build()
    }

    @AppScope
    @Provides
    fun httpLoggingInterceptor(): HttpLoggingInterceptor? {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return httpLoggingInterceptor
    }


}