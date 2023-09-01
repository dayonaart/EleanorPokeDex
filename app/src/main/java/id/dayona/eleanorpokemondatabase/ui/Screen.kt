package id.dayona.eleanorpokemondatabase.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import id.dayona.eleanorpokemondatabase.ui.routes.ErrorDialog
import id.dayona.eleanorpokemondatabase.ui.routes.FeedScreen
import id.dayona.eleanorpokemondatabase.ui.routes.HomeScreen
import id.dayona.eleanorpokemondatabase.ui.routes.ProfileScreen
import id.dayona.eleanorpokemondatabase.ui.routes.SearchScreen
import id.dayona.eleanorpokemondatabase.viewmodel.PokemonViewModel
import kotlinx.coroutines.flow.collectLatest

sealed class ScreenRoute(val route: String) {
    object HomeScreen : ScreenRoute("home-screen")
    object SearchScreen : ScreenRoute("search-screen")
    object FeedScreen : ScreenRoute("feed-screen")
    object ProfileScreen : ScreenRoute("profile-screen")
    object DetailPokemon : ScreenRoute("detail-pokemon-screen")
    object ErrorDialog : ScreenRoute("error-dialog")
    object LoadingDialog : ScreenRoute("loading-dialog")
}

class Screen : BottomNav, HomeScreen, SearchScreen, FeedScreen, ProfileScreen {
    override lateinit var navController: NavHostController
    override lateinit var pokemonViewModel: PokemonViewModel

    @Composable
    fun Navigation(paddingValues: PaddingValues) {
        val errorDialog by pokemonViewModel.errorDialog.collectAsState()
        NavHost(
            navController = navController,
            startDestination = ScreenRoute.HomeScreen.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(ScreenRoute.HomeScreen.route) {
                Home()
            }
            composable(ScreenRoute.SearchScreen.route) {
                Search()
            }
            composable(ScreenRoute.FeedScreen.route) {
                Feed()
            }
            composable(ScreenRoute.ProfileScreen.route) {
                Profile()
            }
            composable(
                ScreenRoute.DetailPokemon.route + "/{index}",
                arguments = listOf(navArgument("index") { type = NavType.IntType })
            ) {
                val args = it.arguments?.getInt("index")
                DetailPokemon(args!!)
            }
            dialog(
                ScreenRoute.LoadingDialog.route,
                dialogProperties = DialogProperties(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false
                )
            ) {
                (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0f)
                RippleLoading()
            }
            dialog(
                ScreenRoute.ErrorDialog.route,
                dialogProperties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                )
            ) {
                (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0f)
                ErrorDialog(
                    dialogText = errorDialog.errorText
                )
            }
        }
    }

    @Composable
    fun MainScreen() {
        LaunchedEffect(key1 = pokemonViewModel.loading) {
            pokemonViewModel.loading.collectLatest {
                if (it) {
                    navController.navigate(ScreenRoute.LoadingDialog.route)
                } else {
                    navController.popBackStack(ScreenRoute.LoadingDialog.route, inclusive = true)
                }
            }
        }
        LaunchedEffect(key1 = pokemonViewModel.errorDialog) {
            pokemonViewModel.errorDialog.collectLatest {
                if (it.showError) {
                    navController.navigate(ScreenRoute.ErrorDialog.route)
                }
            }
        }
        Scaffold(
            content = { Navigation(it) },
            bottomBar = { BottomNavigator() },
        )
    }
}