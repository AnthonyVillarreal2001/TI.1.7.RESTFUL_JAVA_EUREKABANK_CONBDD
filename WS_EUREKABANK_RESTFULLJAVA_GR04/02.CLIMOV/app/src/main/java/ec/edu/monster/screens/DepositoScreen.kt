package ec.edu.monster.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ec.edu.monster.components.DialogoError
import ec.edu.monster.components.DialogoExito
import ec.edu.monster.service.RestApiService
import ec.edu.monster.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepositoScreen(navController: NavController, usuario: String) {
    var cuenta by remember { mutableStateOf("") }
    var importe by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var mostrarExito by remember { mutableStateOf(false) }
    var mostrarError by remember { mutableStateOf(false) }
    var mensajeExito by remember { mutableStateOf("") }
    var saldoNuevo by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }
    
    val scope = rememberCoroutineScope()
    val restApiService = remember { RestApiService() }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Depósito", color = Blanco) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Blanco
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = RojoPrimario
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1A237E))
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Blanco)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountBalance,
                            contentDescription = "Depósito",
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(64.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = "Depósito",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = AzulOscuro
                        )
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        OutlinedTextField(
                            value = cuenta,
                            onValueChange = { cuenta = it },
                            label = { Text("Cuenta:") },
                            placeholder = { Text("Número de cuenta") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = AzulMedio,
                                unfocusedBorderColor = GrisClaro,
                                focusedLabelColor = AzulMedio,
                                unfocusedTextColor = Color(0xFF212121),
                                focusedTextColor = Color(0xFF000000)
                            ),
                            singleLine = true
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        OutlinedTextField(
                            value = importe,
                            onValueChange = { importe = it },
                            label = { Text("Importe:") },
                            placeholder = { Text("0.00") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = AzulMedio,
                                unfocusedBorderColor = GrisClaro,
                                focusedLabelColor = AzulMedio,
                                unfocusedTextColor = Color(0xFF212121),
                                focusedTextColor = Color(0xFF000000)
                            ),
                            singleLine = true
                        )
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Button(
                            onClick = {
                                if (cuenta.isNotBlank() && importe.isNotBlank()) {
                                    val montoDouble = importe.toDoubleOrNull()
                                    if (montoDouble != null && montoDouble > 0) {
                                        isLoading = true
                                        scope.launch {
                                            val resultado = restApiService.registrarDeposito(cuenta, montoDouble)
                                            isLoading = false
                                            if (resultado.estado == 1) {
                                                // Éxito
                                                mensajeExito = "Depósito de $${String.format("%.2f", montoDouble)} realizado correctamente."
                                                saldoNuevo = "Nuevo saldo disponible\nCuenta $cuenta: $ ${String.format("%.2f", resultado.saldo)}"
                                                mostrarExito = true
                                                cuenta = ""
                                                importe = ""
                                            } else {
                                                // Error
                                                mensajeError = "Error al realizar el depósito. La cuenta no existe o no está activa."
                                                mostrarError = true
                                            }
                                        }
                                    } else {
                                        mensajeError = "El importe debe ser mayor a 0"
                                        mostrarError = true
                                    }
                                } else {
                                    mensajeError = "Complete todos los campos"
                                    mostrarError = true
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4CAF50)
                            ),
                            shape = RoundedCornerShape(28.dp),
                            enabled = !isLoading
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    color = Blanco,
                                    modifier = Modifier.size(24.dp)
                                )
                            } else {
                                Text(
                                    text = "DEPOSITAR",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    
    if (mostrarExito) {
        DialogoExito(
            mensaje = mensajeExito,
            mensajeSecundario = saldoNuevo,
            onDismiss = { mostrarExito = false }
        )
    }
    
    if (mostrarError) {
        DialogoError(
            mensaje = mensajeError,
            mensajeAdvertencia = "Por favor, verifique los datos ingresados e intente nuevamente.",
            onDismiss = { mostrarError = false }
        )
    }
}
