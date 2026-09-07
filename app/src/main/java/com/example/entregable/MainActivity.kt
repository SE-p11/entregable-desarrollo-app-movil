package com.example.entregable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.entregable.data.SessionManager
import com.example.entregable.ui.dashboard.DashboardScreen
import com.example.entregable.ui.login.LoginScreen
import com.example.entregable.ui.login.LoginViewModel
import com.example.entregable.ui.products.InventoryScreen
import com.example.entregable.ui.products.InventoryViewModel
import com.example.entregable.ui.register.RegisterScreen
import com.example.entregable.ui.register.RegisterViewModel
import com.example.entregable.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                AppNavigation()
            }


        }
    }
}

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Dashboard : Screen("dashboard")
    object AddProduct : Screen("add_product")
    object ViewProducts : Screen("view_products")
}

@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val sessionManager = SessionManager(context)
    val navController = rememberNavController()

    val startDestination = if (sessionManager.isLoggedIn()) Screen.Dashboard.route else Screen.Login.route

    val inventoryViewModel: InventoryViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()
    val registerViewModel: RegisterViewModel = viewModel()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                viewModel = registerViewModel,
                onRegisterSuccess = {
                    navController.popBackStack()
                },
                onBackToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToAdd = { navController.navigate(Screen.AddProduct.route) },
                onNavigateToView = { navController.navigate(Screen.ViewProducts.route) },
                onLogout = {
                    sessionManager.clearSession()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.AddProduct.route) {
            InventoryScreen(
                viewModel = inventoryViewModel,
                isAddOnly = true,
                onBackToDashboard = { navController.popBackStack() }
            )
        }

        composable(Screen.ViewProducts.route) {
            InventoryScreen(
                viewModel = inventoryViewModel,
                isAddOnly = false,
                onBackToDashboard = { navController.popBackStack() }
            )
        }
    }
}