package ec.edu.monster.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ec.edu.monster.screens.*

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(navController = navController)
        }
        
        composable(
            route = "menu/{usuario}",
            arguments = listOf(navArgument("usuario") { type = NavType.StringType })
        ) { backStackEntry ->
            val usuario = backStackEntry.arguments?.getString("usuario") ?: ""
            MenuScreen(navController = navController, usuario = usuario)
        }
        
        composable(
            route = "movimientos/{usuario}",
            arguments = listOf(navArgument("usuario") { type = NavType.StringType })
        ) { backStackEntry ->
            val usuario = backStackEntry.arguments?.getString("usuario") ?: ""
            MovimientosScreen(navController = navController, usuario = usuario)
        }
        
        composable(
            route = "deposito/{usuario}",
            arguments = listOf(navArgument("usuario") { type = NavType.StringType })
        ) { backStackEntry ->
            val usuario = backStackEntry.arguments?.getString("usuario") ?: ""
            DepositoScreen(navController = navController, usuario = usuario)
        }
        
        composable(
            route = "retiro/{usuario}",
            arguments = listOf(navArgument("usuario") { type = NavType.StringType })
        ) { backStackEntry ->
            val usuario = backStackEntry.arguments?.getString("usuario") ?: ""
            RetiroScreen(navController = navController, usuario = usuario)
        }
        
        composable(
            route = "transferencia/{usuario}",
            arguments = listOf(navArgument("usuario") { type = NavType.StringType })
        ) { backStackEntry ->
            val usuario = backStackEntry.arguments?.getString("usuario") ?: ""
            TransferenciaScreen(navController = navController, usuario = usuario)
        }
    }
}
