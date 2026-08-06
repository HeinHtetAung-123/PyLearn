package com.example.pylearn.data

import com.example.pylearn.domain.model.Flashcard

object SampleFlashcardData {

    val flashcards = listOf(
        // Variables and data types
        Flashcard(
            id = "variables_1",
            topicId = "variables",
            prompt = "Which Python data type stores whole numbers?",
            answer = "Int",
            explanation =
                "The int type stores whole numbers such as 4, 20, and -3."
        ),
        Flashcard(
            id = "variables_2",
            topicId = "variables",
            prompt = "What value is stored in name?",
            codeSnippet = """
                name = "Alex"
            """.trimIndent(),
            answer = "The string \"Alex\"",
            explanation =
                "Text surrounded by quotation marks is stored as a string."
        ),
        Flashcard(
            id = "variables_3",
            topicId = "variables",
            prompt = "What data type is this value?",
            codeSnippet = """
                is_active = True
            """.trimIndent(),
            answer = "Boolean",
            explanation =
                "True and False are Boolean values in Python."
        ),

        // Operators
        Flashcard(
            id = "operators_1",
            topicId = "operators",
            prompt = "What does the % operator return?",
            answer = "The remainder after division",
            explanation =
                "For example, 7 % 3 returns 1."
        ),
        Flashcard(
            id = "operators_2",
            topicId = "operators",
            prompt = "What is the result?",
            codeSnippet = """
                result = 2 ** 3
            """.trimIndent(),
            answer = "8",
            explanation =
                "The ** operator raises a number to a power."
        ),
        Flashcard(
            id = "operators_3",
            topicId = "operators",
            prompt = "Which operator checks whether two values are equal?",
            answer = "==",
            explanation =
                "A double equals sign compares values. A single equals sign assigns a value."
        ),

        // Conditions
        Flashcard(
            id = "conditions_1",
            topicId = "conditions",
            prompt = "Which keyword begins a condition in Python?",
            answer = "if",
            explanation =
                "An if statement runs code when its condition is true."
        ),
        Flashcard(
            id = "conditions_2",
            topicId = "conditions",
            prompt = "Which keyword handles another condition after if?",
            answer = "elif",
            explanation =
                "elif means else if and checks another condition."
        ),
        Flashcard(
            id = "conditions_3",
            topicId = "conditions",
            prompt = "What is printed?",
            codeSnippet = """
                age = 20

                if age >= 18:
                    print("Adult")
            """.trimIndent(),
            answer = "Adult",
            explanation =
                "The condition is true because 20 is greater than or equal to 18."
        ),

        // Loops
        Flashcard(
            id = "loops_1",
            topicId = "loops",
            prompt = "Which loop is commonly used to iterate through a list?",
            answer = "A for loop",
            explanation =
                "A for loop processes each item in a collection."
        ),
        Flashcard(
            id = "loops_2",
            topicId = "loops",
            prompt = "What values are produced by range(3)?",
            answer = "0, 1, and 2",
            explanation =
                "The ending value is excluded from a Python range."
        ),
        Flashcard(
            id = "loops_3",
            topicId = "loops",
            prompt = "Which keyword immediately exits a loop?",
            answer = "break",
            explanation =
                "break stops the nearest active loop."
        ),

        // Functions
        Flashcard(
            id = "functions_1",
            topicId = "functions",
            prompt = "Which keyword defines a Python function?",
            answer = "def",
            explanation =
                "Functions begin with the def keyword followed by their name."
        ),
        Flashcard(
            id = "functions_2",
            topicId = "functions",
            prompt = "What is a function parameter?",
            answer = "A named value received by a function",
            explanation =
                "Parameters allow information to be passed into a function."
        ),
        Flashcard(
            id = "functions_3",
            topicId = "functions",
            prompt = "Which keyword sends a value back from a function?",
            answer = "return",
            explanation =
                "return finishes the function and provides a result."
        ),

        // Lists and dictionaries
        Flashcard(
            id = "collections_1",
            topicId = "collections",
            prompt = "Which brackets are used to create a list?",
            answer = "Square brackets: [ ]",
            explanation =
                "For example, numbers = [1, 2, 3]."
        ),
        Flashcard(
            id = "collections_2",
            topicId = "collections",
            prompt = "What does append() do to a list?",
            answer = "Adds an item to the end",
            explanation =
                "append() changes the existing list by adding one new item."
        ),
        Flashcard(
            id = "collections_3",
            topicId = "collections",
            prompt = "How does a dictionary organise its values?",
            answer = "Using key-value pairs",
            explanation =
                "Each dictionary value is accessed through its associated key."
        )
    )

    fun getFlashcardsForTopic(
        topicId: String
    ): List<Flashcard> {
        return flashcards.filter { flashcard ->
            flashcard.topicId == topicId
        }
    }
}