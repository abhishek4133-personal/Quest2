package com.learn.quest2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.learn.quest2.helper.Screens
import com.learn.quest2.presentation.composable.screen.ECommerceScreen
import com.learn.quest2.presentation.composable.screen.StatisticsScreen
import com.learn.quest2.presentation.viewmodel.ECommerceViewModel
import com.learn.quest2.ui.theme.Quest2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Quest2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.onPrimary
                ) {
//                    HomeScreen()
                    Home()
                }
            }
        }
    }

    @Preview
    @Composable
    private fun Home() {
        val viewModel: ECommerceViewModel = hiltViewModel()
        val navComposable = rememberNavController()
        NavHost(navController = navComposable, startDestination = Screens.LISTING.name) {
            composable(Screens.LISTING.name) {
                ECommerceScreen(
                    viewModel = viewModel,
                    navController = navComposable
                )
            }
            composable(Screens.STATS.name) {
                StatisticsScreen(
                    viewModel = viewModel,
                    navController = navComposable
                )
            }
        }
    }
}



