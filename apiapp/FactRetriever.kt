package com.example.apiapp

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class FactRetriever {
    private val service: NorrisFactService

    companion object {
        private const val BASE_URL = "https://api.chucknorris.io/"
    }

    init {

        val client = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        service = retrofit.create(NorrisFactService::class.java)
    }

    suspend fun getNorrisFact() : NorrisFact {
        return service.getNorrisFact()
    }
}