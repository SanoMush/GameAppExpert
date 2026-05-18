package com.expert.gameapp.core.di

import com.expert.gameapp.core.data.source.remote.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val certificatePinner = CertificatePinner.Builder()
            .add("api.rawg.io", "sha256/klEldM61oXP+Ol1yIV5G4odrqhNnbHJbu50iV+pupVA=")
            .add("api.rawg.io", "sha256/kIdp6NNEd8wsugYyyIYFsi1ylMCED3hZbSR8ZFsa/A4=")
            .add("api.rawg.io", "sha256/mEflZT5enoR1FuXLgYYGqnVEoZvmf9c2bVBpiOjYQ0c=")
            .build()

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(client: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.rawg.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}
