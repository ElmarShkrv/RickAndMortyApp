package com.chiore.rickandmortyapp.di

import android.content.Context
import com.chiore.rickandmortyapp.api.SimpleApi
import com.chiore.rickandmortyapp.utils.NetworkConnectionInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            interceptors().add(httpLoggingInterceptor)
            interceptors().add(NetworkConnectionInterceptor(context))
        }.build()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient) : Retrofit =
        Retrofit.Builder().apply {
            baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
        }.build()

    @Provides
    @Singleton
    fun provideCharacterApi(retrofit: Retrofit) : SimpleApi =
        retrofit.create(SimpleApi::class.java)

    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .serializeNulls()
            .setLenient()
            .create()

/*
    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideSimpleApi (retrofit: Retrofit): SimpleApi =
        retrofit.create(SimpleApi::class.java)

 */

}