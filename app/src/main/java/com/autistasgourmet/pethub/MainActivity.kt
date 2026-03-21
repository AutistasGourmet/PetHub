package com.autistasgourmet.pethub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.autistasgourmet.pethub.ui.components.AppBottomBar
import com.autistasgourmet.pethub.ui.components.AppTopBar
import com.autistasgourmet.pethub.ui.navigation.AppNavHost
import com.autistasgourmet.pethub.ui.navigation.MainRoute
import com.autistasgourmet.pethub.ui.navigation.Route
import com.autistasgourmet.pethub.ui.theme.PetHubTheme
import com.autistasgourmet.pethub.ui.util.SnackbarManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PetHubTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val destination = navBackStackEntry?.destination

                val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

                val currentTitle = when {
                    destination?.hasRoute<MainRoute.Home>() == true -> "PetHub"
                    destination?.hasRoute<MainRoute.Adopt>() == true -> "Adoptar"
                    destination?.hasRoute<MainRoute.Publish>() == true -> "Publicar"
                    destination?.hasRoute<MainRoute.Profile>() == true -> "Perfil"
                    else -> ""
                }

                val isMainRoute = destination?.let {
                    !it.hasRoute<Route.Login>() && !it.hasRoute<Route.Register>()
                } ?: false

                val snackbarHostState = remember { SnackbarHostState() }

                LaunchedEffect(Unit) {
                    SnackbarManager.messages.collectLatest { message ->
                        snackbarHostState.showSnackbar(message)
                    }
                }

                Scaffold(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    containerColor = MaterialTheme.colorScheme.background,
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    topBar = {
                        if (isMainRoute) {
                            AppTopBar(title = currentTitle)
                        }
                    },
                    bottomBar = {
                        if (isMainRoute) {
                            AppBottomBar(
                                currentDestination = destination,
                                onNavigate = { route ->
                                    navController.navigate(route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        AppNavHost(
                            navController = navController,
                            paddingValues = innerPadding
                        )
                    }
                }
            }
        }
    }
}
