package dev.pan.pexelsonline

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import dev.pan.pexelsonline.presenter.detailsScreen.DetailsScreen
import dev.pan.pexelsonline.presenter.homeScreen.HomeScreen
import dev.pan.pexelsonline.presenter.homeScreen.HomeScreenViewModel
import dev.pan.pexelsonline.ui.theme.PexelsOnlineTheme
import dev.pan.pexelsonline.util.Constants.ALT_ARG
import dev.pan.pexelsonline.util.Constants.LANDSCAPE
import dev.pan.pexelsonline.util.Constants.PHOTOGRAPHER_ARG
import dev.pan.pexelsonline.util.Constants.PHOTOGRAPHER_URL_ARG

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PexelsOnlineTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<HomeScreenViewModel>()

    Box(){
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route
        ) {
            composable(Screen.Home.route) { HomeScreen(navController = navController) }

            composable(
                route = Screen.Details.route,
                arguments = listOf(
                    navArgument(LANDSCAPE) { type = NavType.StringType },
                    navArgument(PHOTOGRAPHER_ARG) { type = NavType.StringType },
                    navArgument(PHOTOGRAPHER_URL_ARG) { type = NavType.StringType },
                    navArgument(ALT_ARG) { type = NavType.StringType }
                )
                // uri needed because alt description adds additional path somehow
            ) { backStackEntry ->
                val landscape = Uri.decode(backStackEntry.arguments?.getString(LANDSCAPE) ?: "")
                val photographer = Uri.decode(backStackEntry.arguments?.getString(PHOTOGRAPHER_ARG) ?: "")
                val photographerUrl = Uri.decode(backStackEntry.arguments?.getString(PHOTOGRAPHER_URL_ARG) ?: "")
                val alt = Uri.decode(backStackEntry.arguments?.getString(ALT_ARG) ?: "")
                DetailsScreen(landscape, photographer, photographerUrl, alt)
            }
        }
    }
}

sealed class Screen(val route: String){

    object Home : Screen("home")


    /* 1. Create routes for screen
    * 2. Create constants for arguments used in route
    * 3. Pass them in composable in NavHost
    * 4. On element add click listener to navigate
    * 5. Create MutableSharedFlow that will emit route with arguments when event occurs
    * 6. Create function for that event
    * 7. Receive shared flow in composable through launched effect
    *  */
    object Details : Screen("details/{$LANDSCAPE}/{$PHOTOGRAPHER_ARG}/{$PHOTOGRAPHER_URL_ARG}/{$ALT_ARG}") {
        fun createRoute(landscape: String, photographer: String, photographerUrl: String, alt: String): String {
            return "details/${Uri.encode(landscape)}/${Uri.encode(photographer)}/${Uri.encode(photographerUrl)}/${Uri.encode(alt)}"
        }
    }
}