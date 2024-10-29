package com.wk.apollographql.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.wk.apollographql.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ) = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(httpLoggingInterceptor)
        }
        callTimeout(1, TimeUnit.MINUTES)
        connectTimeout(1, TimeUnit.MINUTES)
        readTimeout(1, TimeUnit.MINUTES)
        writeTimeout(1, TimeUnit.MINUTES)
    }.build()

    @Singleton
    @Provides
    fun provideApolloClient(okHttpClient: OkHttpClient): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://countries.trevorblades.com")
            .okHttpClient(okHttpClient)
            .build()
    }
}