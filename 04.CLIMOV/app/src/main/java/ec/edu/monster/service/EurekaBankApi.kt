package ec.edu.monster.service

import ec.edu.monster.model.LoginResponse
import ec.edu.monster.model.Movimiento
import ec.edu.monster.model.OperacionCuentaResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * API Interface para el servicio RESTful de EurekaBank
 */
interface EurekaBankApi {
    
    @POST("corebancario/login")
    suspend fun login(
        @Query("usuario") usuario: String,
        @Query("password") password: String
    ): Response<LoginResponse>
    
    @POST("corebancario/deposito")
    suspend fun registrarDeposito(
        @Query("cuenta") cuenta: String,
        @Query("importe") importe: Double
    ): Response<OperacionCuentaResponse>
    
    @POST("corebancario/retiro")
    suspend fun registrarRetiro(
        @Query("cuenta") cuenta: String,
        @Query("importe") importe: Double
    ): Response<OperacionCuentaResponse>
    
    @POST("corebancario/transferencia")
    suspend fun registrarTransferencia(
        @Query("cuentaOrigen") cuentaOrigen: String,
        @Query("cuentaDestino") cuentaDestino: String,
        @Query("importe") importe: Double
    ): Response<OperacionCuentaResponse>
    
    @GET("corebancario/movimientos/{cuenta}")
    suspend fun obtenerMovimientos(
        @Path("cuenta") cuenta: String
    ): Response<List<Movimiento>>
    
    @GET("corebancario/ping")
    suspend fun ping(): Response<Map<String, String>>
}
