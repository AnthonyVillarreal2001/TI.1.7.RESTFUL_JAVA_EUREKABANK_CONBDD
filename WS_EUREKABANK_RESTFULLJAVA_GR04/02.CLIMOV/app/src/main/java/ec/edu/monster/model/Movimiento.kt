package ec.edu.monster.model

import com.google.gson.annotations.SerializedName

data class Movimiento(
    @SerializedName("nromov")
    val nroMovimiento: String = "",
    val fecha: String = "",
    val tipo: String = "",
    val accion: String = "",
    val importe: Double = 0.0
)
