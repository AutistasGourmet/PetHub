package com.autistasgourmet.pethub.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.autistasgourmet.pethub.ui.features.home.HomeScreen
import com.autistasgourmet.pethub.ui.features.login.LoginScreen
import com.autistasgourmet.pethub.ui.features.login.LoginViewModel
import com.autistasgourmet.pethub.ui.features.register.RegisterScreen
import com.autistasgourmet.pethub.ui.features.register.RegisterViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Route.Login,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable<Route.Login> {
            val viewModel: LoginViewModel = hiltViewModel()
            // comentar para probar el login
            navController.navigate(MainRoute.Home) {
                popUpTo(Route.Login) { inclusive = true }
            }
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = {
                    navController.navigate(MainRoute.Home) {
                        popUpTo(Route.Login) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Route.Register)
                }
            )
        }

        composable<Route.Register> {
            val viewModel: RegisterViewModel = hiltViewModel()
            RegisterScreen(
                viewModel = viewModel,
                onRegisterSuccess = {
                    navController.popBackStack(Route.Login, inclusive = false)
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable<MainRoute.Home> {
            HomeScreen(
                onNavigateToAdopt = { navController.navigate(MainRoute.Adopt) },
                onNavigateToPublish = { navController.navigate(MainRoute.Publish) }
            )
        }

        composable<MainRoute.Adopt> {
            Box { Text("Adoptar") }
        }

        composable<MainRoute.Publish> {
            Box { Text("Publicar") }
        }

        composable<MainRoute.Profile> {
            Box { Text("Perfil") }
        }
    }
}
