package com.example.pylearn.data

import com.example.pylearn.domain.model.DifficultyLevel
import com.example.pylearn.domain.model.PythonTopic

object SampleLearningData {

    val topics = listOf(
        PythonTopic(
            id = "variables",
            title = "Variables and Data Types",
            description = "Learn how Python stores text, numbers and other values.",
            learningObjective = "Create variables and recognise common Python data types.",
            difficulty = DifficultyLevel.BEGINNER
        ),
        PythonTopic(
            id = "operators",
            title = "Operators",
            description = "Explore arithmetic, comparison and logical operators.",
            learningObjective = "Use operators to calculate values and compare expressions.",
            difficulty = DifficultyLevel.BEGINNER
        ),
        PythonTopic(
            id = "conditions",
            title = "Conditional Statements",
            description = "Make decisions using if, elif and else.",
            learningObjective = "Build simple decision-making programs.",
            difficulty = DifficultyLevel.BEGINNER
        ),
        PythonTopic(
            id = "loops",
            title = "Loops",
            description = "Repeat actions using for and while loops.",
            learningObjective = "Use loops to process repeated tasks.",
            difficulty = DifficultyLevel.BEGINNER
        ),
        PythonTopic(
            id = "functions",
            title = "Functions",
            description = "Organise reusable code into functions.",
            learningObjective = "Define functions, pass arguments and return values.",
            difficulty = DifficultyLevel.INTERMEDIATE
        ),
        PythonTopic(
            id = "collections",
            title = "Lists and Dictionaries",
            description = "Store and manage groups of related values.",
            learningObjective = "Create, access and update lists and dictionaries.",
            difficulty = DifficultyLevel.INTERMEDIATE
        )
    )
}