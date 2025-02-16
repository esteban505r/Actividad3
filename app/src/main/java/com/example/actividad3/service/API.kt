package com.example.actividad3.service

import Actividad3Content
import com.example.actividad3.model.GeneralModel
import com.example.actividad3.model.GeneralModelParsed
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH

interface API {
    @GET("/gists/1c803f410a94b0fac342e40511cd566c")
    suspend fun getJson(
        @Header("Authorization") token: String = "Bearer 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"
    ): Response<GeneralModel>

    @PATCH("/gists/1c803f410a94b0fac342e40511cd566c")
    suspend fun saveData(
        @Body generalModel: GeneralModelParsed,
        @Header("Authorization") token : String = "Bearer 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", ):Response<Unit>
}