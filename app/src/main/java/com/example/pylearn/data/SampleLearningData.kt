package com.example.pylearn.data

import com.example.pylearn.domain.model.DifficultyLevel
import com.example.pylearn.domain.model.LearningQuestion
import com.example.pylearn.domain.model.PythonTopic
import com.example.pylearn.domain.model.QuestionType

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

    val questions = listOf(
        LearningQuestion(
            id = "variables_1",
            topicId = "variables",
            questionText = "Which statement correctly creates an integer variable?",
            options = listOf(
                "age = 20",
                "int age = 20",
                "age := integer(20)",
                "variable age = 20"
            ),
            correctAnswerIndex = 0,
            explanation = "Python creates variables by assigning values with the equals sign. The value 20 is automatically recognised as an integer.",
            questionType = QuestionType.MULTIPLE_CHOICE,
            difficulty = DifficultyLevel.BEGINNER
        ),
        LearningQuestion(
            id = "variables_2",
            topicId = "variables",
            questionText = "What data type is stored in the variable?",
            codeSnippet = "student_name = \"Alex\"",
            options = listOf(
                "Integer",
                "String",
                "Boolean",
                "Float"
            ),
            correctAnswerIndex = 1,
            explanation = "Text surrounded by quotation marks is stored as a string.",
            questionType = QuestionType.MULTIPLE_CHOICE,
            difficulty = DifficultyLevel.BEGINNER
        ),
        LearningQuestion(
            id = "variables_3",
            topicId = "variables",
            questionText = "What is the output of this code?",
            codeSnippet = """
                score = 8
                score = score + 2
                print(score)
            """.trimIndent(),
            options = listOf(
                "8",
                "10",
                "82",
                "score + 2"
            ),
            correctAnswerIndex = 1,
            explanation = "The value of score starts at 8 and then increases by 2, producing 10.",
            questionType = QuestionType.PREDICT_OUTPUT,
            difficulty = DifficultyLevel.BEGINNER
        ),

        LearningQuestion(
            id = "operators_1",
            topicId = "operators",
            questionText = "What is the result of this expression?",
            codeSnippet = "5 + 3 * 2",
            options = listOf(
                "16",
                "11",
                "10",
                "13"
            ),
            correctAnswerIndex = 1,
            explanation = "Multiplication is performed before addition, so 3 × 2 is 6, and 5 + 6 is 11.",
            questionType = QuestionType.PREDICT_OUTPUT,
            difficulty = DifficultyLevel.BEGINNER
        ),
        LearningQuestion(
            id = "operators_2",
            topicId = "operators",
            questionText = "Which operator checks whether two values are equal?",
            options = listOf(
                "=",
                "==",
                "!=",
                ">="
            ),
            correctAnswerIndex = 1,
            explanation = "The double equals operator checks equality. A single equals sign assigns a value.",
            questionType = QuestionType.MULTIPLE_CHOICE,
            difficulty = DifficultyLevel.BEGINNER
        ),

        LearningQuestion(
            id = "conditions_1",
            topicId = "conditions",
            questionText = "What will this code display?",
            codeSnippet = """
                temperature = 30

                if temperature > 25:
                    print("Warm")
                else:
                    print("Cool")
            """.trimIndent(),
            options = listOf(
                "Warm",
                "Cool",
                "30",
                "Nothing"
            ),
            correctAnswerIndex = 0,
            explanation = "The condition 30 > 25 is true, so the first print statement runs.",
            questionType = QuestionType.PREDICT_OUTPUT,
            difficulty = DifficultyLevel.BEGINNER
        ),
        LearningQuestion(
            id = "conditions_2",
            topicId = "conditions",
            questionText = "Which keyword checks another condition after an if statement?",
            options = listOf(
                "then",
                "elseif",
                "elif",
                "otherwise"
            ),
            correctAnswerIndex = 2,
            explanation = "Python uses elif to test an additional condition after if.",
            questionType = QuestionType.MULTIPLE_CHOICE,
            difficulty = DifficultyLevel.BEGINNER
        ),

        LearningQuestion(
            id = "loops_1",
            topicId = "loops",
            questionText = "How many times will this loop run?",
            codeSnippet = """
                for number in range(3):
                    print(number)
            """.trimIndent(),
            options = listOf(
                "2 times",
                "3 times",
                "4 times",
                "It never stops"
            ),
            correctAnswerIndex = 1,
            explanation = "range(3) produces 0, 1 and 2, so the loop runs three times.",
            questionType = QuestionType.PREDICT_OUTPUT,
            difficulty = DifficultyLevel.BEGINNER
        ),
        LearningQuestion(
            id = "loops_2",
            topicId = "loops",
            questionText = "Which loop is normally used when the number of repetitions is known?",
            options = listOf(
                "for loop",
                "while loop",
                "if statement",
                "function"
            ),
            correctAnswerIndex = 0,
            explanation = "A for loop is commonly used when iterating through a known sequence or range.",
            questionType = QuestionType.MULTIPLE_CHOICE,
            difficulty = DifficultyLevel.BEGINNER
        ),

        LearningQuestion(
            id = "functions_1",
            topicId = "functions",
            questionText = "Which keyword is used to define a function in Python?",
            options = listOf(
                "function",
                "fun",
                "define",
                "def"
            ),
            correctAnswerIndex = 3,
            explanation = "Python uses the def keyword followed by the function name.",
            questionType = QuestionType.MULTIPLE_CHOICE,
            difficulty = DifficultyLevel.INTERMEDIATE
        ),
        LearningQuestion(
            id = "functions_2",
            topicId = "functions",
            questionText = "What is the output?",
            codeSnippet = """
                def add(a, b):
                    return a + b

                print(add(2, 4))
            """.trimIndent(),
            options = listOf(
                "2",
                "4",
                "6",
                "24"
            ),
            correctAnswerIndex = 2,
            explanation = "The function returns the sum of 2 and 4, which is 6.",
            questionType = QuestionType.PREDICT_OUTPUT,
            difficulty = DifficultyLevel.INTERMEDIATE
        ),

        LearningQuestion(
            id = "collections_1",
            topicId = "collections",
            questionText = "Which expression accesses the first item in this list?",
            codeSnippet = "languages = [\"Python\", \"Java\", \"Kotlin\"]",
            options = listOf(
                "languages[0]",
                "languages[1]",
                "languages.first",
                "languages(0)"
            ),
            correctAnswerIndex = 0,
            explanation = "Python list indexes begin at zero, so the first item is accessed with index 0.",
            questionType = QuestionType.MULTIPLE_CHOICE,
            difficulty = DifficultyLevel.INTERMEDIATE
        ),
        LearningQuestion(
            id = "collections_2",
            topicId = "collections",
            questionText = "Which structure stores information as key-value pairs?",
            options = listOf(
                "String",
                "Dictionary",
                "Integer",
                "Tuple index"
            ),
            correctAnswerIndex = 1,
            explanation = "A dictionary stores values using keys, such as student[\"name\"].",
            questionType = QuestionType.MULTIPLE_CHOICE,
            difficulty = DifficultyLevel.INTERMEDIATE
        )
    )

    fun getQuestionsForTopic(topicId: String): List<LearningQuestion> {
        return questions.filter { question ->
            question.topicId == topicId
        }
    }
}