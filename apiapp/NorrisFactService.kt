package com.example.apiapp

import retrofit2.http.GET

interface NorrisFactService {
    @GET("/jokes/random")
    suspend fun getNorrisFact(): NorrisFact
}