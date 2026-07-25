package com.example.pylearn.navigation

/**
 * Defines the navigation destinations currently available in PyLearn.
 */
sealed class AppDestination(val route: String) {

    data object Landing : AppDestination("landing")

    data object Activity : AppDestination("activity")

    data object Statistics : AppDestination("statistics")

    data object Settings : AppDestination("settings")
}