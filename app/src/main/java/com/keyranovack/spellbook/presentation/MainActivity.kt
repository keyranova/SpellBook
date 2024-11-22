/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter to find the
 * most up to date changes to the libraries and their usages.
 */

package com.keyranovack.spellbook.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.android.horologist.compose.layout.AppScaffold
import com.keyranovack.spellbook.data.AppDataStore
import com.keyranovack.spellbook.data.dataStore
import com.keyranovack.spellbook.presentation.theme.SpellBookTheme

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(
                AppDataStore(dataStore, this)
            )
        )[MainViewModel::class.java]

        setContent {
            WearApp(viewModel = viewModel)
        }
    }
}

@Composable
fun WearApp(viewModel: MainViewModel) {
    val navController = rememberSwipeDismissableNavController()

    SpellBookTheme {
        AppScaffold {
            SwipeDismissableNavHost(navController = navController, startDestination = "spells") {
                composable("spells") {
                    SpellsScreen(
                        viewModel = viewModel,
                        navController = navController
                    )
                }

                composable("spells/{index}") { navBackStackEntry ->
                    val index = navBackStackEntry.arguments?.getString("index")
                    index?.let { id ->
                        SpellScreen(
                            viewModel = viewModel,
                            index = id
                        )
                    }
                }
            }
        }
    }
}
