package com.example.actividad3.model

import java.io.Serializable

data class DBModel(
    val idAgenda: String,
    val fecha: String,
    val asunto: String,
    val actividad: String,
) : Serializable