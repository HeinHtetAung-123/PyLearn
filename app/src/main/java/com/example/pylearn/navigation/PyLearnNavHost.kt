package com.example.pylearn.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pylearn.ui.activity.ActivityScreen
import com.example.pylearn.ui.landing.LandingScreen
import com.example.pylearn.ui.settings.SettingsScreen
import com.example.pylearn.ui.statistics.StatisticsScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import com.example.pylearn.ui.landing.LandingViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.pylearn.ui.activity.ActivityViewModel
import androidx.compose.ui.platform.LocalContext
import com.example.pylearn.PyLearnApplication
import com.example.pylearn.ui.activity.ActivityViewModelFactory
/**
 * Contains the main navigation graph for PyLearn.
 */
@Composable
fun PyLearnNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppDestination.Landing.route,
        modifier = modifier
    ) {
        composable(route = AppDestination.Landing.route) {
            val landingViewModel: LandingViewModel = viewModel()
            val uiState by landingViewModel.uiState.collectAsStateWithLifecycle()

            LandingScreen(
                uiState = uiState,
                onTopicClick = { topic ->
                    navController.navigate(
                        AppDestination.Activity.createRoute(topic.id)
                    )
                },
                onStatisticsClick = {
                    navController.navigate(AppDestination.Statistics.route)
                },
                onSettingsClick = {
                    navController.navigate(AppDestination.Settings.route)
                }
            )
        }

        composable(
            route = AppDestination.Activity.route,
            arguments = listOf(
                navArgument(AppDestination.Activity.TOPIC_ID_ARGUMENT) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val topicId = backStackEntry.arguments
                ?.getString(AppDestination.Activity.TOPIC_ID_ARGUMENT)
                .orEmpty()

            val application =
                LocalContext.current.applicationContext as PyLearnApplication

            val activityViewModel: ActivityViewModel = viewModel(
                factory = ActivityViewModelFactory(
                    quizProgressRepository =
                        application.appContainer.quizProgressRepository
                )
            )
            val uiState by activityViewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(topicId) {
                activityViewModel.loadTopic(topicId)
            }

            ActivityScreen(
                uiState = uiState,
                onAnswerSelected = activityViewModel::selectAnswer,
                onSubmitAnswer = activityViewModel::submitAnswer,
                onNextQuestion = activityViewModel::moveToNextQuestion,
                onRestartQuiz = activityViewModel::restartQuiz,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = AppDestination.Statistics.route) {
            StatisticsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = AppDestination.Settings.route) {
            SettingsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}