package com.example.actividad3

import Actividad3
import Actividad3Content
import Actividad3ContentParsed
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.actividad3.model.DBModel
import com.example.actividad3.model.GeneralModel
import com.example.actividad3.ui.theme.Actividad3Theme
import com.example.actividad3.viewmodel.GeneralViewModel
import com.google.gson.Gson

class MainActivity : ComponentActivity() {

    val viewModel:GeneralViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val dbState = viewModel.db.collectAsState()
            var showNewDialog: Boolean by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                viewModel.getDB()
            }


            if(showNewDialog){

                var idAgenda:String by remember { mutableStateOf("") }
                var asunto:String by remember { mutableStateOf("") }
                var actividad:String by remember { mutableStateOf("") }
                var fecha:String by remember { mutableStateOf("") }

                Dialog(
                    onDismissRequest = { showNewDialog = false }
                ) {
                    Card {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            OutlinedTextField(
                                value = idAgenda,
                                onValueChange = {
                                    idAgenda = it
                                },
                                label = { Text("ID Agenda") }
                            )
                            OutlinedTextField(
                                value = asunto,
                                onValueChange = {
                                    asunto = it
                                },
                                label = { Text("Asunto") }
                            )
                            OutlinedTextField(
                                value = actividad,
                                onValueChange = {
                                    actividad = it
                                },
                                label = { Text("Actividad") }
                            )
                            OutlinedTextField(
                                value = fecha,
                                onValueChange = {
                                    fecha = it
                                },
                                label = { Text("Fecha") }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = {
                                    viewModel.addDB(
                                        DBModel(
                                            idAgenda = idAgenda,
                                            asunto = asunto,
                                            actividad = actividad,
                                            fecha = fecha
                                        )
                                    )
                                    showNewDialog = false
                                }
                            ) {
                                Text("Guardar")
                            }

                        }
                    }
                }
            }

            Actividad3Theme {
                Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            showNewDialog = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add"
                        )
                    }
                }) { innerPadding ->
                    Info(
                        dbState.value,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Info(db: GeneralModel, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(top = 32.dp)
    ) {
        val result = if(db.files.actividad3.content==null){
            "{\"list\":[]}"
        }
        else{
            db.files.actividad3.content
        }
        val value = Gson().fromJson(result,Actividad3ContentParsed::class.java)
        items(value.list.size) { index ->
            Card(
                modifier = Modifier.padding(8.dp).fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = value.list[index].idAgenda.toString())
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = value.list[index].asunto)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = value.list[index].actividad)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = value.list[index].fecha)
                }
            }
        }
    }
}

