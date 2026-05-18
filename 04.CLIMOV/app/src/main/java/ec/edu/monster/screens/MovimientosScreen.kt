package ec.edu.monster.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ec.edu.monster.components.DialogoError
import ec.edu.monster.model.Movimiento
import ec.edu.monster.service.RestApiService
import ec.edu.monster.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovimientosScreen(navController: NavController, usuario: String) {
    var cuenta by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var mostrarError by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf("") }
    var mostrarResultado by remember { mutableStateOf(false) }
    var movimientos by remember { mutableStateOf<List<Movimiento>>(emptyList()) }
    
    val scope = rememberCoroutineScope()
    val restApiService = remember { RestApiService() }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Consultar Movimientos", color = Blanco) },
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
            if (!mostrarResultado) {
                // Formulario de búsqueda
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
                                imageVector = Icons.Default.BarChart,
                                contentDescription = "Movimientos",
                                tint = AzulMedio,
                                modifier = Modifier.size(64.dp)
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Text(
                                text = "Consultar Movimientos",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = AzulOscuro
                            )
                            
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            OutlinedTextField(
                                value = cuenta,
                                onValueChange = { cuenta = it },
                                label = { Text("Número de cuenta:") },
                                placeholder = { Text("Ingresa el número de cuenta") },
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
                            
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            Button(
                                onClick = {
                                    if (cuenta.isNotBlank()) {
                                        isLoading = true
                                        scope.launch {
                                            val resultado = restApiService.traerMovimientos(cuenta)
                                            isLoading = false
                                            if (resultado.isEmpty()) {
                                                mensajeError = "No hay movimientos registrados para esta cuenta."
                                                mostrarError = true
                                            } else {
                                                movimientos = resultado
                                                mostrarResultado = true
                                            }
                                        }
                                    } else {
                                        mensajeError = "Ingrese un número de cuenta"
                                        mostrarError = true
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = RojoPrimario
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
                                        text = "VER MOVIMIENTOS",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                // Tabla de movimientos
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = Blanco)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.BarChart,
                                contentDescription = "Movimientos",
                                tint = RojoPrimario,
                                modifier = Modifier.size(48.dp)
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = "Movimientos de Cuenta",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = RojoPrimario
                            )
                            
                            Text(
                                text = cuenta,
                                fontSize = 16.sp,
                                color = Blanco,
                                modifier = Modifier
                                    .background(
                                        color = Color(0xFF3F51B5),
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .padding(horizontal = 16.dp, vertical = 4.dp)
                            )
                        }
                    }
                    
                    // Tabla con scroll horizontal
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = Blanco)
                    ) {
                        TablaMovimientosVertical(movimientos)
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Button(
                        onClick = { 
                            mostrarResultado = false
                            cuenta = ""
                            movimientos = emptyList()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AzulMedio
                        )
                    ) {
                        Text("NUEVA CONSULTA")
                    }
                }
            }
        }
    }
    
    if (mostrarError) {
        DialogoError(
            mensaje = mensajeError,
            mensajeAdvertencia = "Verifique que el número de cuenta sea correcto o que la cuenta tenga movimientos registrados.",
            onDismiss = { mostrarError = false }
        )
    }
}

@Composable
fun TablaMovimientosVertical(movimientos: List<Movimiento>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .horizontalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Encabezado
        Row(
            modifier = Modifier
                .background(
                    color = RojoPrimario,
                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                )
                .padding(12.dp)
        ) {
            Text(
                "NRO",
                color = Blanco,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.width(80.dp),
                textAlign = TextAlign.Center
            )
            Text(
                "FECHA",
                color = Blanco,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.width(180.dp),
                textAlign = TextAlign.Center
            )
            Text(
                "TIPO",
                color = Blanco,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.width(140.dp),
                textAlign = TextAlign.Center
            )
            Text(
                "ACCIÓN",
                color = Blanco,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.width(100.dp),
                textAlign = TextAlign.Center
            )
            Text(
                "IMPORTE",
                color = Blanco,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.width(100.dp),
                textAlign = TextAlign.Center
            )
        }
        
        // Filas
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(movimientos) { movimiento ->
                Row(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(12.dp)
                ) {
                    Text(
                        movimiento.nroMovimiento,
                        fontSize = 12.sp,
                        color = AzulOscuro,
                        modifier = Modifier.width(80.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        movimiento.fecha,
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier.width(180.dp),
                        textAlign = TextAlign.Center
                    )
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = when (movimiento.tipo.uppercase()) {
                                "TRANSFERENCIA" -> Color(0xFFE91E63)
                                "INTERES" -> Color(0xFF2196F3)
                                "RETIRO" -> Color(0xFFF44336)
                                "DEPOSITO" -> Color(0xFF4CAF50)
                                else -> Color.Gray
                            }
                        ),
                        modifier = Modifier.width(140.dp)
                    ) {
                        Text(
                            movimiento.tipo,
                            color = Blanco,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        )
                    }
                    Text(
                        movimiento.accion,
                        fontSize = 12.sp,
                        color = if (movimiento.accion == "INGRESO") Color(0xFF388E3C) else Color(0xFFD32F2F),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(100.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        "$${String.format("%.2f", movimiento.importe)}",
                        fontSize = 12.sp,
                        color = Color(0xFF1B5E20),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(100.dp),
                        textAlign = TextAlign.Center
                    )
                }
                Divider(color = Color(0xFFE0E0E0))
            }
        }
    }
}
