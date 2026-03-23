package com.autistasgourmet.pethub.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable data object Login : Route
    @Serializable data object Register : Route
}

sealed interface MainRoute {
    @Serializable data object Home : MainRoute
    @Serializable data object Adopt : MainRoute
    @Serializable data object Publish : MainRoute
    @Serializable data object Profile : MainRoute
    @Serializable data object CompleteAdopterProfile : MainRoute
    @Serializable data class PetDetail(val petId: String) : MainRoute
}
