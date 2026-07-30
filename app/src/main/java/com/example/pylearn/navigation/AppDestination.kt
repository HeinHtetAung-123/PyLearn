package com.example.pylearn.navigation

sealed class AppDestination(val route: String) {

    data object Landing : AppDestination("landing")

    data object Activity : AppDestination("activity/{topicId}") {
        const val TOPIC_ID_ARGUMENT = "topicId"

        fun createRoute(topicId: String): String {
            return "activity/$topicId"
        }
    }

    data object Statistics : AppDestination("statistics")

    data object Settings : AppDestination("settings")

    data object CodeRunner : AppDestination("code_runner")
}