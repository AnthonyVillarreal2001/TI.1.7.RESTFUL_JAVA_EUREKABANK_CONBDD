package ec.edu.monster.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ec.edu.monster.R
import ec.edu.monster.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavController, usuario: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.monster),
                            contentDescription = "Monster",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "MONSTER",
                            color = Blanco,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Cerrar Sesión",
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
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF0F1729),
                            Color(0xFF1A237E),
                            Color(0xFF0D47A1)
                        )
                    )
                )
                .padding(paddingValues)
        ) {
            // Círculos decorativos
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .offset(x = (-50).dp, y = 50.dp)
                    .clip(CircleShape)
                    .background(Color(0x30673AB7))
            )
            
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = 100.dp, y = 100.dp)
                    .clip(CircleShape)
                    .background(Color(0x20512DA8))
            )
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "EurekaBank",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Blanco,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                Text(
                    text = "Tu banco, tu equipo.",
                    fontSize = 16.sp,
                    color = Color(0xFFB0BEC5),
                    modifier = Modifier.padding(bottom = 48.dp)
                )
                
                // Grid de opciones
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CardOpcion(
                            titulo = "Consultar Movimientos",
                            icono = Icons.Default.BarChart,
                            gradiente = listOf(Color(0xFF9C1818), Color(0xFF1565C0)),
                            onClick = { navController.navigate("movimientos/$usuario") },
                            modifier = Modifier.weight(1f)
                        )
                        
                        CardOpcion(
                            titulo = "Depósito",
                            icono = Icons.Default.AccountBalance,
                            gradiente = listOf(Color(0xFF5B1885), Color(0xFF1565C0)),
                            onClick = { navController.navigate("deposito/$usuario") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CardOpcion(
                            titulo = "Retiro",
                            icono = Icons.Default.Savings,
                            gradiente = listOf(Color(0xFF6B2884), Color(0xFF1565C0)),
                            onClick = { navController.navigate("retiro/$usuario") },
                            modifier = Modifier.weight(1f)
                        )
                        
                        CardOpcion(
                            titulo = "Transferencia",
                            icono = Icons.Default.CreditCard,
                            gradiente = listOf(Color(0xFF7A3787), Color(0xFF1565C0)),
                            onClick = { navController.navigate("transferencia/$usuario") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CardOpcion(
    titulo: String,
    icono: ImageVector,
    gradiente: List<Color>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(colors = gradiente)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = icono,
                    contentDescription = titulo,
                    tint = Blanco,
                    modifier = Modifier.size(64.dp)
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = titulo,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Blanco,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}
