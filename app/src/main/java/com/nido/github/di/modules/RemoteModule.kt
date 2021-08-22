package com.nido.github.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nido.github.feature.authentication.api.AuthenticationApi
import com.nido.github.feature.content.owner.api.OwnerApi
import com.nido.github.feature.content.search.api.SearchApi
import com.nido.github.feature.content.user.api.UserApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RemoteModule {

    @Provides
    @Singleton
    fun provideUserApi(
        client: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): UserApi = createApi(client, converterFactory, UserApi::class.java)

    @Provides
    @Singleton
    fun provideAuthenticationApi(
        client: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): AuthenticationApi = createApi(client, converterFactory, AuthenticationApi::class.java)

    @Provides
    @Singleton
    fun provideOwnerApi(
        client: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): OwnerApi = createApi(client, converterFactory, OwnerApi::class.java)

    @Provides
    @Singleton
    fun provideSearchApi(
        client: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): SearchApi = createApi(client, converterFactory, SearchApi::class.java)

    private fun <T> createApi(
        client: OkHttpClient,
        converterFactory: GsonConverterFactory,
        clazz: Class<T>
    ): T =
        createRetrofit(client, converterFactory).create(clazz)

    private fun createRetrofit(
        client: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(converterFactory)
            .client(client)
            .baseUrl(BASE_URL)
            .build()

    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor).build()

    @Provides
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Singleton
    @Provides
    fun provideGsonConverter(): GsonConverterFactory = GsonConverterFactory.create()

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }
}