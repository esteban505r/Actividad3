package com.example.actividad3.viewmodel

import Actividad3
import Actividad3Content
import Actividad3ContentParsed
import Actividad3Parsed
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.actividad3.model.DBModel
import com.example.actividad3.model.GeneralModel
import com.example.actividad3.model.GeneralModelChild
import com.example.actividad3.model.GeneralModelChildParsed
import com.example.actividad3.model.GeneralModelParsed
import com.example.actividad3.repositories.RepositoryImpl
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GeneralViewModel: ViewModel() {

    val repository = RepositoryImpl()

    private val _db = MutableStateFlow(GeneralModel(
        files = GeneralModelChild(
            Actividad3(
                content = "{\"list\":[]}"
            )
        )
    ))
    val db get() = _db.asStateFlow()


    fun getDB(){
        viewModelScope.launch {
            try {
                val response = repository.getJson()
                _db.value = response
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun addDB(dbModel: DBModel) {
        viewModelScope.launch {
            val result = if(db.value.files.actividad3.content==null){
                "{\"list\":[]}"
            }
            else{
                db.value.files.actividad3.content
            }
            val value = Gson().fromJson(result, Actividad3ContentParsed::class.java)
            val list = value.list.toMutableList()
            list.add(dbModel)
            try {
                val response = repository.saveData(GeneralModelParsed(
                    files = GeneralModelChildParsed(
                        actividad3 = Actividad3Parsed(
                            content = Gson().toJson(Actividad3ContentParsed(list = list))
                        )
                    )
                ))
                getDB()
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }
    }


}