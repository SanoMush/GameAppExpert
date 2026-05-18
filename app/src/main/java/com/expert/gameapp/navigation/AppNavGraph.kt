package com.expert.gameapp.navigation

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.expert.gameapp.presentation.detail.DetailScreen
import com.expert.gameapp.presentation.home.HomeScreen
import com.expert.gameapp.presentation.search.SearchScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                navigateToDetail = { gameId ->
                    navController.navigate("detail/$gameId")
                },
                navigateToSearch = {
                    navController.navigate("search")
                },
                navigateToFavorite = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("gameapp://favorite"))
                    context.startActivity(intent)
                }
            )
        }
        composable(
            route = "detail/{gameId}",
            deepLinks = listOf(androidx.navigation.navDeepLink { uriPattern = "gameapp://detail/{gameId}" })
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getString("gameId")?.toIntOrNull()
            if (gameId != null) {
                val isFromFavorite = (context as? android.app.Activity)?.intent?.getBooleanExtra("from_favorite", false) == true
                
                androidx.activity.compose.BackHandler(enabled = isFromFavorite) {
                    (context as? android.app.Activity)?.finish()
                }

                DetailScreen(
                    navigateBack = {
                        if (isFromFavorite) {
                            (context as? android.app.Activity)?.finish()
                        } else if (!navController.navigateUp()) {
                            (context as? android.app.Activity)?.finish()
                        }
                    }
                )
            }
        }
        composable("search") {
            SearchScreen(
                navigateToDetail = { gameId ->
                    navController.navigate("detail/$gameId")
                },
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}
