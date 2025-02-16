package com.example.actividad3.repositories

import Actividad3Content
import com.example.actividad3.model.DBModel
import com.example.actividad3.model.GeneralModel
import com.example.actividad3.model.GeneralModelParsed

interface Repository{
    suspend fun getJson(): GeneralModel
    suspend fun saveData(generalModel: GeneralModelParsed)
}
