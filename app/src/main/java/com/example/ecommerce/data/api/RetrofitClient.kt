package com.example.ecommerce.data.api

import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.math.BigDecimal

object BigDecimalAdapter {
    @FromJson
    fun fromJson(string: String) = BigDecimal(string)

    @ToJson
    fun toJson(value: BigDecimal) = value.toString()
}
private val moshi = Moshi.Builder()
    .add(BigDecimalAdapter)
    .add(KotlinJsonAdapterFactory())
    .build()

internal object RetrofitClient {

    fun provideRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(provideOkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
    val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

}