package com.currencyconverter.app.ui.screens

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.currencyconverter.app.R
import com.currencyconverter.app.app.App

@Composable
fun NavigationScreen(
    application: App,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.Main.baseRoute,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .then(modifier)
            .fillMaxSize()
    ) {
        composable(
            route = AppScreens.Main.baseRoute,
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up)
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down)
            }
        ) {
            MainScreen(
                viewModel = viewModel {
                    application.converterComponent.mainViewModel()
                },
                title = stringResource(AppScreens.Main.titleRes ?: R.string.app_name),
                navigateToSettings = {
                    navController.navigate(AppScreens.Settings.baseRoute) {
                        popUpTo(AppScreens.Main.baseRoute)
                        launchSingleTop = true
                    }
                },
            )
        }


        composable(
            route = AppScreens.Settings.baseRoute,
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down)
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up)
            }
        ) {
            SettingsScreen(
                viewModel = viewModel { application.converterComponent.settingsViewModel() },
                title = stringResource(AppScreens.Settings.titleRes),
                popBackStack = navController::popBackStack
            )
        }
    }
}