package com.example.actividad3.repositories

import Actividad3
import Actividad3Content
import com.example.actividad3.model.DBModel
import com.example.actividad3.model.GeneralModel
import com.example.actividad3.model.GeneralModelChild
import com.example.actividad3.model.GeneralModelParsed
import com.example.actividad3.service.Service

class RepositoryImpl:Repository {
    override suspend fun getJson(): GeneralModel {
        return Service.getAPI().getJson().body()?:GeneralModel(
            files = GeneralModelChild(
                Actividad3(
                    content = "{\"list\":[]}"
                )
            )
        )
    }

    override suspend fun saveData(generalModel: GeneralModelParsed) {
        Service.getAPI().saveData(generalModel)
    }

}