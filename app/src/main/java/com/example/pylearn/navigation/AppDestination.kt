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

    data object TopicOptions : AppDestination(
        route = "topic_options/{topicId}"
    ) {
        const val TOPIC_ID_ARGUMENT = "topicId"

        fun createRoute(topicId: String): String {
            return "topic_options/$topicId"
        }
    }

    data object Flashcards : AppDestination(
        route = "flashcards/{topicId}"
    ) {
        const val TOPIC_ID_ARGUMENT = "topicId"

        fun createRoute(topicId: String): String {
            return "flashcards/$topicId"
        }
    }
}