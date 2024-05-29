package com.omaradev.pet_adoption.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Rect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.omaradev.pet_adoption.R
import com.omaradev.pet_adoption.ui.details.DetailsScreen
import com.omaradev.pet_adoption.ui.home.HomeScreen
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavigationComposeShared(systemInDarkTheme: Boolean) {
    SharedTransitionLayout {
        val boundsTransform =
            { _: Rect, _: Rect ->
                tween<Rect>(500)
            }

        var isDarkTheme by remember { mutableStateOf(systemInDarkTheme) }

        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen(
                    isDarkTheme,
                    navController = navController,
                    boundsTransform = boundsTransform,
                    animatedContentScope = this@composable,
                    this@SharedTransitionLayout
                ) {
                    isDarkTheme = it
                }
            }
            composable(
                "details/{PetId}",
                arguments = listOf(navArgument("PetId") { type = NavType.IntType })
            ) { backStackEntry ->
                val petId = backStackEntry.arguments?.getInt("PetId")

                petId?.let {
                    DetailsScreen(
                        systemInDarkTheme = isDarkTheme,
                        petId = it,
                        navController = navController,
                        boundsTransform = boundsTransform,
                        animatedContentScope = this@composable,
                        this@SharedTransitionLayout
                    )
                }

            }
        }
    }
}
