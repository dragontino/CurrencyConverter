package com.currencyconverter.app.ui.screens

import android.net.Uri
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.currencyconverter.app.R
import com.currencyconverter.app.app.App
import com.currencyconverter.domain.model.ConversionResponse
import com.google.gson.Gson

@Composable
fun NavigationScreen(
    application: App,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.Main.destinationRoute,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .then(modifier)
            .fillMaxSize()
    ) {
        composable(
            route = AppScreens.Main.destinationRoute,
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
                    navController.navigate(AppScreens.Settings.destinationRoute) {
                        popUpTo(AppScreens.Main.destinationRoute)
                        launchSingleTop = true
                    }
                },
                navigateToResult = {
                    navController.navigate(AppScreens.Result.createUrl(it)) {
                        popUpTo(AppScreens.Main.destinationRoute)
                        launchSingleTop = true
                    }
                }
            )
        }


        composable(
            route = AppScreens.Result.destinationRoute,
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down)
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up)
            },
            arguments = listOf(
                navArgument(AppScreens.Result.Args.RESPONSE) {
                    type = NavType.StringType
                }
            )
        ) {
            val resultJson = it.arguments?.getString(AppScreens.Result.Args.RESPONSE)
            if (resultJson != null) {
                ResultScreen(
                    result = Gson().fromJson(
                        Uri.decode(resultJson), ConversionResponse::class.java
                    ),
                    title = stringResource(R.string.result_title),
                    navigateToSettings = {
                        navController.navigate(AppScreens.Settings.destinationRoute) {
                            popUpTo(AppScreens.Result.destinationRoute)
                            launchSingleTop = true
                        }
                    },
                )
            }
        }


        composable(
            route = AppScreens.Settings.destinationRoute,
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