package com.autistasgourmet.pethub.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.autistasgourmet.pethub.ui.features.adopt.AdoptPetScreen
import com.autistasgourmet.pethub.ui.features.adopt.PetDetailScreen
import com.autistasgourmet.pethub.ui.features.completeProfile.CompleteAdopterProfileScreen
import com.autistasgourmet.pethub.ui.features.completeProfile.CompleteAdopterProfileViewModel
import com.autistasgourmet.pethub.ui.features.home.HomeScreen
import com.autistasgourmet.pethub.ui.features.login.LoginScreen
import com.autistasgourmet.pethub.ui.features.login.LoginViewModel
import com.autistasgourmet.pethub.ui.features.profile.ProfileScreen
import com.autistasgourmet.pethub.ui.features.register.RegisterScreen
import com.autistasgourmet.pethub.ui.features.register.RegisterViewModel
import com.autistasgourmet.pethub.ui.features.registerPet.RegisterPetScreen

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
            AdoptPetScreen(
                onNavigateToDetail = { petId ->
                    navController.navigate(MainRoute.PetDetail(petId))
                }
            )
        }

        composable<MainRoute.PetDetail> { backStackEntry ->
            val detail: MainRoute.PetDetail = backStackEntry.toRoute()
            PetDetailScreen(
                petId = detail.petId,
                onBack = { navController.popBackStack() }
            )
        }

        composable<MainRoute.Publish> {
            RegisterPetScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable<MainRoute.Profile> {
            ProfileScreen(
                onNavigateToCompleteProfile = {
                    navController.navigate(MainRoute.CompleteAdopterProfile)
                }
            )
        }

        composable<MainRoute.CompleteAdopterProfile> {
            val viewModel: CompleteAdopterProfileViewModel = hiltViewModel()
            CompleteAdopterProfileScreen(
                onBack = { navController.popBackStack() },
                viewModel = viewModel
            )
        }
    }
}
