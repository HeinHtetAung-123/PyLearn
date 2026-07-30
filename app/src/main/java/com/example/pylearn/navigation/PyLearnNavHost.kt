package com.example.pylearn.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pylearn.PyLearnApplication
import com.example.pylearn.ui.activity.ActivityScreen
import com.example.pylearn.ui.activity.ActivityViewModel
import com.example.pylearn.ui.activity.ActivityViewModelFactory
import com.example.pylearn.ui.landing.LandingScreen
import com.example.pylearn.ui.landing.LandingViewModel
import com.example.pylearn.ui.settings.SettingsScreen
import com.example.pylearn.ui.statistics.StatisticsScreen
import com.example.pylearn.ui.statistics.StatisticsViewModel
import com.example.pylearn.ui.statistics.StatisticsViewModelFactory
import com.example.pylearn.ui.settings.SettingsViewModel
import com.example.pylearn.ui.settings.SettingsViewModelFactory
import com.example.pylearn.ui.coderunner.CodeRunnerScreen
import com.example.pylearn.ui.coderunner.CodeRunnerViewModel
import com.example.pylearn.ui.coderunner.CodeRunnerViewModelFactory

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
                    navController.navigate(
                        AppDestination.Statistics.route
                    )
                },
                onSettingsClick = {
                    navController.navigate(
                        AppDestination.Settings.route
                    )
                },
                onCodeRunnerClick = {
                    navController.navigate(
                        AppDestination.CodeRunner.route
                    )
                }
            )
        }

        composable(
            route = AppDestination.Activity.route,
            arguments = listOf(
                navArgument(
                    AppDestination.Activity.TOPIC_ID_ARGUMENT
                ) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val topicId = backStackEntry.arguments
                ?.getString(
                    AppDestination.Activity.TOPIC_ID_ARGUMENT
                )
                .orEmpty()

            val application =
                LocalContext.current.applicationContext as PyLearnApplication

            val activityViewModel: ActivityViewModel = viewModel(
                factory = ActivityViewModelFactory(
                    quizProgressRepository =
                        application.appContainer.quizProgressRepository
                )
            )

            val uiState by
            activityViewModel.uiState.collectAsStateWithLifecycle()

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
            val application =
                LocalContext.current.applicationContext as PyLearnApplication

            val statisticsViewModel: StatisticsViewModel = viewModel(
                factory = StatisticsViewModelFactory(
                    quizProgressRepository =
                        application.appContainer.quizProgressRepository
                )
            )

            val statisticsUiState by
            statisticsViewModel.uiState.collectAsStateWithLifecycle()

            StatisticsScreen(
                uiState = statisticsUiState,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = AppDestination.Settings.route) {
            val application =
                LocalContext.current.applicationContext as PyLearnApplication

            val settingsViewModel: SettingsViewModel = viewModel(
                factory = SettingsViewModelFactory(
                    settingsRepository =
                        application.appContainer.settingsRepository,
                    quizProgressRepository =
                        application.appContainer.quizProgressRepository
                )
            )

            val settingsUiState by
            settingsViewModel.uiState.collectAsStateWithLifecycle()

            SettingsScreen(
                uiState = settingsUiState,
                onDarkModeChanged =
                    settingsViewModel::setDarkModeEnabled,
                onLargeTextChanged =
                    settingsViewModel::setLargeTextEnabled,
                onConfirmBeforeResetChanged =
                    settingsViewModel::setConfirmBeforeReset,
                onResetProgressClick =
                    settingsViewModel::requestProgressReset,
                onConfirmReset =
                    settingsViewModel::confirmProgressReset,
                onDismissReset =
                    settingsViewModel::dismissResetConfirmation,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = AppDestination.CodeRunner.route) {
            val application =
                LocalContext.current.applicationContext as PyLearnApplication

            val codeRunnerViewModel: CodeRunnerViewModel = viewModel(
                factory = CodeRunnerViewModelFactory(
                    codeExecutionRepository =
                        application.appContainer.codeExecutionRepository
                )
            )

            val codeRunnerUiState by
            codeRunnerViewModel.uiState.collectAsStateWithLifecycle()

            CodeRunnerScreen(
                uiState = codeRunnerUiState,
                onSourceCodeChanged =
                    codeRunnerViewModel::updateSourceCode,
                onStandardInputChanged =
                    codeRunnerViewModel::updateStandardInput,
                onRunCode =
                    codeRunnerViewModel::runCode,
                onResetCode =
                    codeRunnerViewModel::resetCode,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}