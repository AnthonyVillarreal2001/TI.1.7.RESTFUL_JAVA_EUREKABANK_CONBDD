package ec.edu.monster.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ec.edu.monster.R
import ec.edu.monster.service.RestApiService
import ec.edu.monster.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    var usuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var modoDemo by remember { mutableStateOf(false) } // Modo demo para pruebas
    
    val scope = rememberCoroutineScope()
    val restApiService = remember { RestApiService() }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A237E),
                        Color(0xFF0D47A1),
                        Color(0xFF1565C0),
                        Color(0xFF1976D2)
                    )
                )
            )
    ) {
        // Círculos decorativos
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = (-100).dp, y = 100.dp)
                .clip(CircleShape)
                .background(Color(0x30673AB7))
        )
        
        Box(
            modifier = Modifier
                .size(250.dp)
                .offset(x = 250.dp, y = (-50).dp)
                .clip(CircleShape)
                .background(Color(0x20283593))
        )
        
        Box(
            modifier = Modifier
                .size(400.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 150.dp, y = 150.dp)
                .clip(CircleShape)
                .background(Color(0x30512DA8))
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo y título en contenedor blanco
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Blanco),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_ldu),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Column {
                        Text(
                            text = "EurekaBank",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF7B2C7F) // Mezcla de rojo y azul (morado)
                        )
                        Text(
                            text = "Tu banco, tu equipo.",
                            fontSize = 13.sp,
                            color = Color(0xFF5B1885) // Morado más oscuro
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Tarjeta de login
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Blanco),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Ícono de usuario
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Usuario",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(AzulMedio.copy(alpha = 0.1f))
                            .padding(20.dp),
                        tint = AzulMedio
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Text(
                        text = "Ingreso al sistema",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = RojoPrimario,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    // Campo Usuario
                    OutlinedTextField(
                        value = usuario,
                        onValueChange = { usuario = it },
                        label = { Text("Usuario") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Usuario",
                                tint = AzulMedio
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AzulMedio,
                            unfocusedBorderColor = GrisClaro,
                            focusedLabelColor = AzulMedio,
                            unfocusedTextColor = Color(0xFF212121), // Texto oscuro cuando no tiene foco
                            focusedTextColor = Color(0xFF000000) // Texto negro cuando tiene foco
                        ),
                        singleLine = true
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Campo Contraseña
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Contraseña",
                                tint = AzulMedio
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AzulMedio,
                            unfocusedBorderColor = GrisClaro,
                            focusedLabelColor = AzulMedio,
                            unfocusedTextColor = Color(0xFF212121), // Texto oscuro cuando no tiene foco
                            focusedTextColor = Color(0xFF000000) // Texto negro cuando tiene foco
                        ),
                        singleLine = true
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Botón Ingresar
                    Button(
                        onClick = {
                            if (usuario.isNotBlank() && password.isNotBlank()) {
                                isLoading = true
                                errorMessage = null
                                scope.launch {
                                    try {
                                        println("🔐 Intentando login con usuario: $usuario")
                                        val resultado = restApiService.validarIngreso(usuario, password)
                                        println("✅ Resultado del login: $resultado")
                                        isLoading = false
                                        if (resultado) {
                                            navController.navigate("menu/$usuario") {
                                                popUpTo("login") { inclusive = true }
                                            }
                                        } else {
                                            errorMessage = "Credenciales incorrectas. Usuario: $usuario"
                                        }
                                    } catch (e: Exception) {
                                        println("❌ Error en login: ${e.message}")
                                        e.printStackTrace()
                                        isLoading = false
                                        errorMessage = "Error de conexión: ${e.message}"
                                    }
                                }
                            } else {
                                errorMessage = "Complete todos los campos"
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
                                text = "INGRESAR",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    
                    // Mensaje de error
                    errorMessage?.let {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = it,
                            color = RojoPrimario,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
