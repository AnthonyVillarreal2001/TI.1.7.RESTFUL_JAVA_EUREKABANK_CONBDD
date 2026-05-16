package ec.edu.monster.model

data class OperacionCuentaResponse(
    val estado: Int = -1,        // 1 = éxito, -1 = error
    val saldo: Double = 0.0      // Saldo actualizado
)
