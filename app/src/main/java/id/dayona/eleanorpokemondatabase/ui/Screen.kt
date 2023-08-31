package id.dayona.eleanorpokemondatabase.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
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

sealed class ScreenRoute(val route: String) {
    object HomeScreen : ScreenRoute("home-screen")
    object SearchScreen : ScreenRoute("search-screen")
    object FeedScreen : ScreenRoute("feed-screen")
    object ProfileScreen : ScreenRoute("profile-screen")
    object DetailPokemon : ScreenRoute("detail-pokemon-screen")
    object ErrorDialog : ScreenRoute("error-dialog")
    object LoadingDialog : ScreenRoute("loading-dialog")

    fun withArgsFormat(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}

class Screen : BottomNav, HomeScreen, SearchScreen, FeedScreen, ProfileScreen {
    override lateinit var navController: NavHostController
    override lateinit var pokemonViewModel: PokemonViewModel

    @Composable
    fun Navigation(paddingValues: PaddingValues) {
        NavHost(
            navController = navController,
            startDestination = ScreenRoute.HomeScreen.route,
            modifier = androidx.compose.ui.Modifier.padding(paddingValues)
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
            dialog(ScreenRoute.LoadingDialog.route) {
                Dialog(onDismissRequest = {
                    navController.popBackStack()
                }, content = {
                    RippleLoading()
                })
            }
            dialog(ScreenRoute.ErrorDialog.route) {
                ErrorDialog(
                    onDismissRequest = {
                        navController.popBackStack()
                    },
                    dialogTitle = "errorDialogModel.dialogTitle",
                    dialogText = "errorDialogModel.dialogText"
                )
            }
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun MainScreen() {
        Scaffold(
            content = { Navigation(it) },
            bottomBar = { BottomNavigator() },
        )
    }
}