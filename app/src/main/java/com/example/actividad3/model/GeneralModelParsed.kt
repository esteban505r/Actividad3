package com.example.actividad3.model

import Actividad3
import Actividad3Content
import Actividad3Parsed
import java.io.Serializable

data class GeneralModelParsed(
      val files: GeneralModelChildParsed
):Serializable