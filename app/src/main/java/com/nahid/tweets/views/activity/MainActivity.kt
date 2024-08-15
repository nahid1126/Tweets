package com.nahid.tweets.views.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nahid.tweets.ui.theme.TweetsTheme
import com.nahid.tweets.views.screens.CategoryScreen
import com.nahid.tweets.views.screens.DetailsScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"
private var keepSplash = false
@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().setKeepOnScreenCondition {
            keepSplash
        }
        lifecycleScope.launch {
            delay(2000)
            keepSplash = false
        }
        setContent {
            TweetsTheme {
                Scaffold(topBar = {
                    TopAppBar(title = {
                        Text(text = "Tweets")
                    }, modifier = Modifier.background(color = Color.Black))
                }) {
                    Box(modifier = Modifier.padding(it)) {
                        App()
                    }
                }
            }
        }

    }

    @Composable
    private fun App() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "category") {
            composable(route = "category") {
                CategoryScreen {
                    navController.navigate("detail/${it}")
                }
            }
            composable(route = "detail/{category}", arguments = listOf(navArgument("category") {
                type = NavType.StringType
            })) {
                val cat = it.arguments?.getString("category")
                DetailsScreen(cat)
            }
        }
    }
}
