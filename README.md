```markdown
# PyLearn

PyLearn is an Android educational application designed to help beginner university students learn and practise introductory Python programming through interactive quizzes, flashcards, progress tracking, and real code execution.

The app was developed using **Kotlin**, **Jetpack Compose**, **Material Design 3**, **MVVM**, **Room**, **Preferences DataStore**, **Retrofit**, **Kotlin Coroutines**, **SoundPool**, and **Judge0**.

---

## Features

PyLearn currently includes six beginner Python topics:

- Variables and Data Types
- Operators
- Conditions
- Loops
- Functions
- Lists and Dictionaries

Each topic provides two learning activities:

### Quiz Activity

Quiz activities include:

- Multiple-choice questions
- Predict-the-output questions
- Debugging questions
- Immediate feedback
- Explanations
- Progress indicators
- Final scores
- Retry support

Quiz results are stored using Room, including the learner's best score and number of attempts.

### Flashcard Review

Flashcards provide a self-paced way to review Python concepts.

Users can:

- Reveal answers
- Review explanations
- Mark a card as **I Remembered**
- Mark a card as **Review Again**
- View session progress
- Restart a completed session

Flashcard results are kept separate from quiz scores because they are based on self-reported recall.

---

## Application Screens

PyLearn contains the following main screens:

- **Landing Screen** – displays topics and saved progress
- **Topic Options Screen** – lets users choose Quiz or Flashcards
- **Activity Screen** – displays quiz questions and feedback
- **Flashcard Screen** – provides self-paced concept review
- **Statistics Screen** – displays progress, attempts, and best scores
- **Settings Screen** – manages appearance, sound, and progress reset
- **Python Code Runner** – executes Python code online

---

## Architecture

PyLearn follows the **MVVM architecture**.

    Jetpack Compose UI
            ↓
        ViewModel
            ↓
        Repository
            ↓
    Room / DataStore / Retrofit

The application uses:

- `StateFlow` for reactive UI state
- `SharedFlow` for one-time events such as sound feedback
- Repository interfaces for data access
- ViewModel factories for dependency injection
- `AppContainer` for application-level dependencies

This keeps the code organised, reusable, and testable.

---

## Data Persistence

### Room

Room stores quiz progress such as:

- Topic ID
- Best score
- Total questions
- Attempt count
- Completion status
- Last attempt time

### Preferences DataStore

DataStore stores user preferences including:

- Dark mode
- Larger text
- Reset confirmation
- Sound effects

These settings remain active after the application is restarted.

---

## Python Code Runner

PyLearn includes a standalone Python Code Runner powered by **Retrofit** and **Judge0**.

Users can:

- Enter Python code
- Provide optional standard input
- Execute the program
- View output
- View syntax and runtime errors
- Reset the editor

An internet connection is required for code execution.

Users are advised not to enter passwords, personal information, private keys, or confidential code.

---

## Sound Feedback

PyLearn uses Android `SoundPool` to provide optional sound feedback for:

- Correct answers
- Incorrect answers
- Quiz completion
- Flashcard flipping
- Flashcard completion

Sound effects are short and supportive rather than harsh or distracting.

Users can disable sound effects from the Settings screen.

---

## Accessibility and Ethical Design

PyLearn includes:

- Dark mode
- Larger text
- Scrollable layouts
- Clear controls
- Monospace formatting for Python code
- Visual feedback alongside sound
- User-controlled sound preferences

The application avoids:

- Public rankings
- Forced streaks
- Punishment for incorrect answers
- Harsh failure messages
- Forced time pressure
- Unnecessary personal-data collection

Quiz progress and settings are stored locally on the device.

---

## Testing

PyLearn includes both local unit tests and instrumented Compose UI tests.

Main local test classes include:

- `ActivityViewModelTest`
- `CodeExecutionRepositoryTest`
- `CodeRunnerViewModelTest`
- `FlashcardViewModelTest`
- `SettingsViewModelTest`
- `StatisticsViewModelTest`

Main GUI test classes include:

- `LandingScreenTest`
- `ActivityScreenTest`
- `CodeRunnerScreenTest`
- `FlashcardScreenTest`
- `SettingsScreenTest`

Current flashcard testing:

    FlashcardViewModelTest: 11/11 passed
    FlashcardScreenTest: 9/9 passed

---

## Build and Run

Open the project in Android Studio, allow Gradle to synchronise, start an emulator or connect an Android device, and run the `app` configuration.

To build the application:

    Build → Assemble Project

On Windows:

    gradlew.bat assembleDebug

To run local tests:

    gradlew.bat test

To run instrumented tests:

    gradlew.bat connectedAndroidTest

---

## Known Limitations

- Learning content currently focuses on six beginner topics
- Each topic contains five quiz questions
- Flashcard progress is not stored between sessions
- Progress is stored locally on one device
- Python code execution requires internet access
- The application currently supports Python only

---

## Future Improvements

Possible future improvements include:

- More Python topics and questions
- Larger flashcard sets
- Saved flashcard progress
- Spaced repetition
- Offline learning content
- Additional accessibility options
- More detailed learning statistics
- Cloud progress synchronization
- Support for additional programming languages

---

