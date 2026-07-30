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
            id = "variables_4",
            topicId = "variables",
            questionText = "What change fixes this code?",
            codeSnippet = """
                student age = 20
                print(student age)
            """.trimIndent(),
            options = listOf(
                "Replace the spaces with underscores",
                "Put the variable name in quotation marks",
                "Replace 20 with \"20\"",
                "Remove the print statement"
            ),
            correctAnswerIndex = 0,
            explanation = "Python variable names cannot contain spaces. A valid name would be student_age.",
            questionType = QuestionType.DEBUG_CODE,
            difficulty = DifficultyLevel.BEGINNER
        ),

        LearningQuestion(
            id = "variables_5",
            topicId = "variables",
            questionText = "What is the data type of this value?",
            codeSnippet = "price = 12.50",
            options = listOf(
                "String",
                "Integer",
                "Float",
                "Boolean"
            ),
            correctAnswerIndex = 2,
            explanation = "A number containing a decimal point is stored as a float in Python.",
            questionType = QuestionType.MULTIPLE_CHOICE,
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
            id = "operators_3",
            topicId = "operators",
            questionText = "Why does this condition not correctly compare the two values?",
            codeSnippet = """
                score = 10
        
                if score = 10:
                    print("Full score")
            """.trimIndent(),
            options = listOf(
                "The comparison should use ==",
                "The score must be stored as text",
                "The if keyword should be removed",
                "The print statement needs two equals signs"
            ),
            correctAnswerIndex = 0,
            explanation = "A single equals sign assigns a value. Python uses == to compare whether two values are equal.",
            questionType = QuestionType.DEBUG_CODE,
            difficulty = DifficultyLevel.BEGINNER
        ),

        LearningQuestion(
            id = "operators_4",
            topicId = "operators",
            questionText = "What is the output?",
            codeSnippet = "print(10 // 3)",
            options = listOf(
                "3",
                "3.33",
                "1",
                "4"
            ),
            correctAnswerIndex = 0,
            explanation = "The // operator performs floor division and returns the whole-number result.",
            questionType = QuestionType.PREDICT_OUTPUT,
            difficulty = DifficultyLevel.BEGINNER
        ),

        LearningQuestion(
            id = "operators_5",
            topicId = "operators",
            questionText = "Which operator returns the remainder after division?",
            options = listOf(
                "/",
                "//",
                "%",
                "**"
            ),
            correctAnswerIndex = 2,
            explanation = "The modulo operator % returns the remainder after division.",
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
            id = "conditions_3",
            topicId = "conditions",
            questionText = "What is missing from this conditional statement?",
            codeSnippet = """
                age = 18
        
                if age >= 18
                    print("Adult")
            """.trimIndent(),
            options = listOf(
                "A colon after the condition",
                "A semicolon after age",
                "Quotation marks around 18",
                "The else keyword before print"
            ),
            correctAnswerIndex = 0,
            explanation = "Python requires a colon at the end of an if condition.",
            questionType = QuestionType.DEBUG_CODE,
            difficulty = DifficultyLevel.BEGINNER
        ),

        LearningQuestion(
            id = "conditions_4",
            topicId = "conditions",
            questionText = "What will this code display?",
            codeSnippet = """
        score = 65

        if score >= 80:
            print("High")
        elif score >= 50:
            print("Pass")
        else:
            print("Try again")
    """.trimIndent(),
            options = listOf(
                "High",
                "Pass",
                "Try again",
                "Nothing"
            ),
            correctAnswerIndex = 1,
            explanation = "The first condition is false, but score >= 50 is true, so Pass is displayed.",
            questionType = QuestionType.PREDICT_OUTPUT,
            difficulty = DifficultyLevel.BEGINNER
        ),

        LearningQuestion(
            id = "conditions_5",
            topicId = "conditions",
            questionText = "Which value is treated as a Boolean value?",
            options = listOf(
                "\"True\"",
                "True",
                "1.5",
                "\"False value\""
            ),
            correctAnswerIndex = 1,
            explanation = "True without quotation marks is a Boolean value. Text inside quotation marks is a string.",
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
            id = "loops_3",
            topicId = "loops",
            questionText = "Why does this loop cause an indentation error?",
            codeSnippet = """
                for number in range(3):
                print(number)
            """.trimIndent(),
            options = listOf(
                "The print statement must be indented",
                "range must be written in uppercase",
                "The loop must start from 1",
                "The colon should be removed"
            ),
            correctAnswerIndex = 0,
            explanation = "Statements inside a Python loop must be indented beneath the loop declaration.",
            questionType = QuestionType.DEBUG_CODE,
            difficulty = DifficultyLevel.BEGINNER
        ),

        LearningQuestion(
            id = "loops_4",
            topicId = "loops",
            questionText = "What is the output?",
            codeSnippet = """
        total = 0

        for number in range(1, 4):
            total = total + number

        print(total)
    """.trimIndent(),
            options = listOf(
                "3",
                "6",
                "10",
                "123"
            ),
            correctAnswerIndex = 1,
            explanation = "range(1, 4) produces 1, 2 and 3. Their sum is 6.",
            questionType = QuestionType.PREDICT_OUTPUT,
            difficulty = DifficultyLevel.BEGINNER
        ),

        LearningQuestion(
            id = "loops_5",
            topicId = "loops",
            questionText = "Which keyword immediately stops a loop?",
            options = listOf(
                "stop",
                "exit",
                "break",
                "finish"
            ),
            correctAnswerIndex = 2,
            explanation = "The break keyword ends the nearest active loop immediately.",
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
            id = "functions_3",
            topicId = "functions",
            questionText = "What change makes this function return the result?",
            codeSnippet = """
                def multiply(a, b):
                    a * b
        
                print(multiply(3, 4))
            """.trimIndent(),
            options = listOf(
                "Add return before a * b",
                "Replace def with function",
                "Remove the parameters",
                "Move print inside the function"
            ),
            correctAnswerIndex = 0,
            explanation = "The function calculates a value but does not return it. It should use return a * b.",
            questionType = QuestionType.DEBUG_CODE,
            difficulty = DifficultyLevel.INTERMEDIATE
        ),

        LearningQuestion(
            id = "functions_4",
            topicId = "functions",
            questionText = "What value is returned?",
            codeSnippet = """
        def square(number):
            return number * number

        result = square(5)
        print(result)
    """.trimIndent(),
            options = listOf(
                "5",
                "10",
                "25",
                "55"
            ),
            correctAnswerIndex = 2,
            explanation = "The function multiplies 5 by itself, so it returns 25.",
            questionType = QuestionType.PREDICT_OUTPUT,
            difficulty = DifficultyLevel.INTERMEDIATE
        ),

        LearningQuestion(
            id = "functions_5",
            topicId = "functions",
            questionText = "What is the purpose of a function parameter?",
            options = listOf(
                "To provide input to the function",
                "To permanently store every result",
                "To stop the program",
                "To create a loop automatically"
            ),
            correctAnswerIndex = 0,
            explanation = "Parameters allow values to be passed into a function when it is called.",
            questionType = QuestionType.MULTIPLE_CHOICE,
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
        ),

        LearningQuestion(
            id = "collections_3",
            topicId = "collections",
            questionText = "What is wrong with this list access?",
            codeSnippet = """
                languages = ["Python", "Java", "Kotlin"]
                print(languages[3])
            """.trimIndent(),
            options = listOf(
                "Index 3 is outside the list",
                "Lists cannot contain strings",
                "The print function cannot display list values",
                "The list needs round brackets"
            ),
            correctAnswerIndex = 0,
            explanation = "This list has indexes 0, 1 and 2. Accessing index 3 causes an IndexError.",
            questionType = QuestionType.DEBUG_CODE,
            difficulty = DifficultyLevel.INTERMEDIATE
        ),

        LearningQuestion(
            id = "collections_4",
            topicId = "collections",
            questionText = "What is the output?",
            codeSnippet = """
                numbers = [2, 4, 6]
                numbers.append(8)
                print(numbers)
            """.trimIndent(),
            options = listOf(
                "[2, 4, 6]",
                "[8, 2, 4, 6]",
                "[2, 4, 6, 8]",
                "8"
            ),
            correctAnswerIndex = 2,
            explanation = "append adds the new item to the end of the list.",
            questionType = QuestionType.PREDICT_OUTPUT,
            difficulty = DifficultyLevel.INTERMEDIATE
        ),

        LearningQuestion(
            id = "collections_5",
            topicId = "collections",
            questionText = "How is the value Singapore accessed?",
            codeSnippet = """
                student = {
                    "name": "Alex",
                    "country": "Singapore"
                }
            """.trimIndent(),
            options = listOf(
                "student[\"country\"]",
                "student[1]",
                "student.country()",
                "student(\"Singapore\")"
            ),
            correctAnswerIndex = 0,
            explanation = "Dictionary values are accessed using their keys inside square brackets.",
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