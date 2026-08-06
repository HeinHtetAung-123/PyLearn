PyLearn

PyLearn is an Android educational application designed to help beginner university students learn and practise introductory Python programming concepts.

The application combines interactive quizzes, debugging questions, output-prediction activities, self-paced flashcards, progress tracking, accessibility preferences, sound feedback, and an online Python code runner.

PyLearn was developed using Kotlin, Jetpack Compose, Material Design 3, MVVM architecture, Room, Preferences DataStore, Retrofit, Kotlin Coroutines, and the Judge0 code-execution service.

Table of Contents

Project Overview

Problem Statement

Project Aim

Target Users

Learning Topics

Main Features

Application Screens

User Flow

Architecture

Technology Stack

Project Structure

Data Persistence

Python Code Execution

Sound Architecture

Flashcard Design

Accessibility

Privacy and Ethical Considerations

Testing

Project Setup

Running the Application

Building the Project

Screenshots

Known Limitations

Future Improvements

Development Principles

Version Control

Author

Acknowledgements

Project Status

Project Overview

PyLearn is a mobile learning application that introduces Python programming concepts through short and interactive activities.

Instead of relying only on long lessons or passive reading, PyLearn allows learners to:

answer Python quiz questions;

predict program output;

identify and debug errors;

review concepts using flashcards;

execute Python code;

receive immediate explanations;

monitor quiz performance;

customise accessibility and sound preferences.

The application is intended to provide a supportive learning environment for beginner programmers.

Problem Statement

Beginner university students may find introductory programming difficult because they must understand unfamiliar syntax, logical thinking, problem-solving techniques, and error messages at the same time.

Traditional learning materials can sometimes be too long, passive, or disconnected from practical experimentation.

Students may benefit from a mobile application that:

divides Python concepts into manageable topics;

provides immediate feedback;

supports repeated practice;

allows self-paced revision;

records learning progress;

encourages experimentation with real Python code.

PyLearn addresses this problem by combining structured quizzes, flashcards, explanations, progress tracking, and code execution in one application.

Project Aim

The aim of PyLearn is to provide beginner university students with an accessible, interactive, and responsible mobile learning environment for practising introductory Python programming.

The application aims to:

improve understanding of basic Python concepts;

support recall and pattern recognition;

provide immediate and respectful feedback;

encourage independent practice;

allow learners to experiment with Python code;

store learning progress locally;

support different visual and audio preferences;

avoid manipulative or discouraging learning practices.

Target Users

PyLearn is primarily designed for:

beginner university programming students;

students learning Python for the first time;

learners who prefer short interactive exercises;

users who want immediate feedback;

students who need repeated practice;

learners who benefit from visual progress indicators;

users who prefer self-paced learning without time pressure.

The application does not require prior advanced programming knowledge.

Learning Topics

PyLearn currently includes six introductory Python topics:

Variables and data types

Operators

Conditional statements

Loops

Functions

Lists and dictionaries

Each topic contains five quiz questions.

The quiz dataset therefore contains a total of:

6 topics × 5 questions = 30 quiz questions

Each topic also contains flashcards for concept review.

Main Features

1. Topic-Based Learning

The Landing screen displays all available Python topics.

Each topic card includes:

topic title;

short description;

difficulty level;

completion indicator;

best score;

attempt count;

score percentage;

visual progress bar;

activity button.

Completed topics use a compact visual layout to avoid overcrowding the screen.

2. Activity Selection

Selecting a topic opens an activity-choice screen.

Users can choose between:

Quiz Activity

Flashcard Review

This approach keeps the Landing screen clean and prevents topic cards from containing too many buttons.

3. Interactive Quiz Activities

Each topic contains five quiz questions.

Supported question types include:

multiple choice;

predict the output;

debug the code.

The Activity screen displays:

topic title;

question type;

current question number;

progress indicator;

question text;

optional code snippet;

answer options;

submission button;

immediate feedback;

explanation;

next-question button;

final score.

Users cannot submit an answer until an option is selected.

After an answer is submitted, the learner receives feedback explaining the result.

4. Flashcard Review

Flashcards provide a separate self-paced review activity.

Each flashcard contains:

a prompt;

an answer;

optional Python code;

an explanation.

Users can:

tap to reveal the answer;

return to the prompt;

select I Remembered;

select Review Again;

monitor remembered and review-again counts;

restart the session;

complete the session without time pressure.

Flashcard results do not change quiz scores because flashcard recall is self-reported rather than objectively marked.

5. Quiz Progress Tracking

PyLearn saves quiz results using Room.

For each topic, the application records:

topic ID;

best score;

total questions;

number of attempts;

completion status;

latest attempt timestamp.

When a topic is repeated:

the attempt count increases;

the best score is preserved;

a lower new score does not replace a higher previous score.

Progress remains available after the application is closed and reopened.

6. Statistics

The Statistics screen summarises learner performance.

It displays:

number of completed topics;

overall progress;

total attempts;

average best score;

result information for each topic.

Statistics are calculated from Room database records and displayed reactively using StateFlow.

7. Python Code Runner

PyLearn includes a standalone Python Code Runner.

Users can:

type Python source code;

provide optional standard input;

execute code online;

view standard output;

view syntax errors;

view runtime errors;

review execution status;

reset the editor.

Example:

name = input()
print("Hello", name)

Example standard input:

Alex

Expected output:

Hello Alex

The Code Runner is kept separate from quiz activities to maintain a clean and focused quiz interface.

8. Persistent Settings

PyLearn stores user preferences using Preferences DataStore.

Available settings include:

dark mode;

larger text;

confirmation before progress reset;

sound effects.

These preferences remain active after the application is restarted.

9. Sound Feedback

PyLearn provides optional sound effects for important learning events.

Sound effects include:

correct answer;

incorrect answer;

quiz completion;

flashcard flip;

flashcard session completion.

Sounds are intentionally short and subtle.

The incorrect-answer sound is designed as a neutral cue rather than a harsh failure buzzer.

Users can disable all sound effects in Settings.

10. Progress Reset

Users can delete all saved quiz progress from the Settings screen.

When reset confirmation is enabled:

The user taps Reset Progress.

A confirmation dialog appears.

The user may confirm or cancel.

When confirmation is disabled, the reset occurs immediately.

Resetting progress removes quiz records but does not remove the application’s learning content.

Application Screens

Landing Screen

The Landing screen is the main dashboard.

It displays:

application introduction;

Python topic cards;

saved topic progress;

Statistics navigation;

Settings navigation;

Python Code Runner navigation.

Topic Options Screen

The Topic Options screen appears after selecting a Python topic.

It allows the learner to choose:

Quiz Activity;

Flashcard Review.

It also explains that flashcard results do not affect quiz scores.

Activity Screen

The Activity screen presents quiz questions and feedback.

It supports:

answer selection;

answer submission;

immediate explanations;

score calculation;

question progression;

quiz completion;

quiz restart.

Flashcard Screen

The Flashcard screen provides self-paced concept review.

It displays:

current flashcard number;

progress indicator;

front and back content;

optional code snippet;

remembered count;

review-again count;

completion summary.

Statistics Screen

The Statistics screen displays stored quiz performance.

It presents overall and topic-level results using data retrieved from Room.

Settings Screen

The Settings screen allows users to control:

dark mode;

larger text;

reset confirmation;

sound effects;

saved-progress deletion.

Python Code Runner Screen

The Code Runner screen contains:

Python code editor;

standard input field;

Run Code button;

Reset button;

execution result card;

privacy notice.

User Flow

The primary application flow is:

Landing Screen
    |
    +-- Statistics
    |
    +-- Settings
    |
    +-- Python Code Runner
    |
    +-- Select Topic
            |
            +-- Topic Options
                    |
                    +-- Quiz Activity
                    |
                    +-- Flashcard Review

Quiz flow:

Select answer
→ Submit answer
→ Receive feedback and explanation
→ Continue to next question
→ Complete activity
→ Save result in Room
→ View score

Flashcard flow:

View prompt
→ Reveal answer
→ Review explanation
→ Mark remembered or review again
→ Continue through cards
→ View session summary

Code Runner flow:

Enter Python code
→ Add optional input
→ Submit code through Retrofit
→ Receive Judge0 token
→ Poll execution result
→ Display output or error

Architecture

PyLearn follows the Model-View-ViewModel architectural pattern.

Compose UI
    |
    v
ViewModel
    |
    v
Repository
    |
    +-- Room Database
    +-- Preferences DataStore
    +-- Retrofit / Judge0

UI Layer

The UI layer contains Jetpack Compose screens and reusable components.

Responsibilities include:

displaying state;

handling user interaction;

collecting ViewModel StateFlow;

collecting one-time SharedFlow events;

navigating between screens;

playing sound effects through the sound abstraction.

The UI does not directly access Room or Retrofit.

ViewModel Layer

ViewModels manage screen state and presentation logic.

Examples include:

LandingViewModel

ActivityViewModel

FlashcardViewModel

StatisticsViewModel

SettingsViewModel

CodeRunnerViewModel

MainViewModel

ViewModels:

expose immutable StateFlow;

process user actions;

update UI state;

call repositories;

emit one-time events through SharedFlow;

avoid direct dependency on Compose UI elements.

Repository Layer

Repositories separate data access from UI logic.

Main repositories include:

QuizProgressRepository

RoomQuizProgressRepository

SettingsRepository

DataStoreSettingsRepository

CodeExecutionRepository

Judge0CodeExecutionRepository

This structure improves:

maintainability;

testability;

separation of concerns;

dependency replacement;

code reuse.

Dependency Injection

PyLearn uses manual dependency injection through AppContainer.

AppContainer creates and provides:

Room database;

quiz-progress repository;

settings repository;

code-execution repository;

sound player.

PyLearnApplication creates one application-level AppContainer.

ViewModel factories receive the repositories required by each ViewModel.

Reactive State

PyLearn uses:

StateFlow for persistent screen state;

SharedFlow for one-time events.

StateFlow is used for values such as:

selected answer;

score;

current question;

flashcard position;

user settings;

statistics;

code-execution status.

SharedFlow is used for events such as:

correct-answer sound;

incorrect-answer sound;

completion sound;

flashcard flip sound.

This prevents one-time events from replaying unnecessarily during Compose recomposition.

Technology Stack

Technology

Purpose

Kotlin

Main programming language

Android SDK

Android application platform

Jetpack Compose

Declarative user-interface development

Material Design 3

UI components, typography, and theming

Navigation Compose

Screen navigation and route arguments

ViewModel

Lifecycle-aware presentation logic

StateFlow

Reactive screen state

SharedFlow

One-time UI and sound events

Kotlin Coroutines

Asynchronous operations

Room

Local quiz-progress persistence

Preferences DataStore

Persistent settings

Retrofit

HTTP requests

Gson Converter

JSON conversion

Judge0

External Python code execution

SoundPool

Short local sound playback

KSP

Room code generation

JUnit

Local unit testing

Compose UI Test

Instrumented GUI testing

Gradle Kotlin DSL

Build configuration

Git

Version control

GitHub

Remote repository hosting

Project Structure

PyLearn/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/pylearn/
│   │   │   │   ├── audio/
│   │   │   │   ├── data/
│   │   │   │   │   ├── local/
│   │   │   │   │   └── remote/
│   │   │   │   ├── di/
│   │   │   │   ├── domain/model/
│   │   │   │   ├── navigation/
│   │   │   │   ├── ui/
│   │   │   │   │   ├── activity/
│   │   │   │   │   ├── coderunner/
│   │   │   │   │   ├── flashcards/
│   │   │   │   │   ├── landing/
│   │   │   │   │   ├── settings/
│   │   │   │   │   ├── statistics/
│   │   │   │   │   ├── theme/
│   │   │   │   │   └── topicoptions/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── MainViewModel.kt
│   │   │   │   └── PyLearnApplication.kt
│   │   │   └── res/
│   │   │       └── raw/
│   │   │           ├── activity_complete.wav
│   │   │           ├── correct_answer.wav
│   │   │           ├── flashcard_flip.wav
│   │   │           └── incorrect_answer.wav
│   │   ├── test/
│   │   └── androidTest/
│   └── build.gradle.kts
├── docs/
│   └── images/
├── build.gradle.kts
├── settings.gradle.kts
├── gradlew
├── gradlew.bat
└── README.md

Data Persistence

Room Database

Room stores quiz progress locally.

The database includes:

QuizProgressEntity

QuizProgressDao

PyLearnDatabase

RoomQuizProgressRepository

A quiz result stores information such as:

topicId
bestScore
totalQuestions
attemptCount
isCompleted
lastAttemptTimestamp

Repository logic ensures that:

attempts increase after every completed quiz;

the highest score is retained;

duplicate completion actions do not save the same result repeatedly.

Preferences DataStore

Preferences DataStore stores:

darkModeEnabled
largeTextEnabled
confirmBeforeReset
soundEffectsEnabled

The settings repository exposes preferences as a Flow.

The UI updates automatically when preferences change.

Python Code Execution

PyLearn uses Retrofit to communicate with a Judge0-compatible code-execution service.

The execution process is:

The learner enters Python code.

The learner may provide standard input.

CodeRunnerViewModel validates the request.

CodeExecutionRepository submits the code.

Judge0 returns a submission token.

The repository checks the execution status.

Processing continues until execution is complete.

Output or error details are converted into an application model.

The result is displayed in the Compose UI.

Possible results include:

successful output;

no output;

compilation or syntax error;

runtime error;

network error;

service error.

The application handles errors without crashing.

An internet connection is required for code execution.

Sound Architecture

Sound playback is implemented through an abstraction rather than directly inside Compose screens.

SoundEffect

SoundEffect defines supported sound types:

CORRECT_ANSWER
INCORRECT_ANSWER
ACTIVITY_COMPLETE
FLASHCARD_FLIP

SoundPlayer

SoundPlayer defines:

fun play(
    effect: SoundEffect,
    enabled: Boolean
)

fun release()

AndroidSoundPlayer

AndroidSoundPlayer uses Android SoundPool.

It:

loads local WAV resources;

uses the application context;

supports multiple short sound streams;

respects the saved sound preference;

releases audio resources;

avoids loading sound files for every answer.

Event Flow

User submits answer
→ ActivityViewModel evaluates answer
→ ViewModel emits ActivityUiEvent
→ Navigation layer collects event
→ Event maps to SoundEffect
→ SoundPlayer checks preference
→ Sound plays once

This design avoids placing Android sound APIs inside ViewModels and prevents sounds from replaying during ordinary recomposition.

Flashcard Design

Flashcards are designed to support:

recall;

pattern recognition;

repeated exposure;

patience;

self-paced learning.

The flashcard state records:

currentCardIndex
isCardFlipped
rememberedCount
reviewAgainCount
isSessionComplete

The user must reveal the answer before marking a card.

The application uses supportive options:

I Remembered
Review Again

It avoids negative wording such as:

Failed
Wrong again
Poor performance

Flashcard sessions do not include:

countdown timers;

forced streaks;

public rankings;

punishment for forgetting;

pressure-based notifications.

Accessibility

PyLearn includes several accessibility-conscious design decisions.

Larger Text

Users can enable larger typography throughout the application.

The setting affects the application theme rather than only one screen.

Dark Mode

Dark mode can be enabled in Settings.

The application uses Material colour roles to preserve readability.

Scrollable Layouts

Major screens use scrollable layouts to support:

small devices;

larger text;

longer explanations;

portrait orientation.

Code Readability

Python code uses a monospace font to preserve indentation and improve readability.

Clear Controls

Buttons use clear labels such as:

Submit Answer
Next Question
Reveal Answer
I Remembered
Review Again
Reset Progress
Run Code

Alternative Feedback

Important learning feedback is displayed visually as text.

Sound is supplementary and is not the only way information is communicated.

Users can disable sounds without losing functionality.

Privacy and Ethical Considerations

PyLearn was designed to minimise unnecessary data collection.

No Account Requirement

Users do not need to:

create an account;

provide a name;

provide an email address;

provide a phone number;

provide location information.

Local Storage

Quiz progress and settings are stored locally on the device.

The application does not require cloud-based user profiles.

Code Runner Privacy

Python source code and optional standard input are sent to an external execution service.

The application displays a notice advising users not to enter:

passwords;

personal information;

confidential information;

private keys;

sensitive source code.

Respectful Learning Design

The application avoids:

public performance rankings;

manipulative streak systems;

harsh failure sounds;

humiliating messages;

forced time pressure;

deceptive design;

unnecessary notifications.

Incorrect answers receive explanations rather than punishment.

Flashcard forgetting is treated as a normal part of learning.

User Control

Users can:

disable sound effects;

change visual preferences;

reset stored progress;

cancel a reset action;

practise at their own pace;

leave an activity at any time.

Testing

PyLearn contains local unit tests and instrumented Compose UI tests.

Local Unit Tests

Local unit tests are stored under:

app/src/test/

Main test classes include:

ActivityViewModelTest
CodeExecutionRepositoryTest
CodeRunnerViewModelTest
FlashcardViewModelTest
SettingsViewModelTest
StatisticsViewModelTest

Activity ViewModel Tests

Tests cover:

valid topic loading;

invalid topic handling;

answer selection;

correct scoring;

incorrect scoring;

submission locking;

question progression;

completion;

restart behaviour;

duplicate result protection;

correct-answer event;

incorrect-answer event;

activity-completion event.

Flashcard ViewModel Tests

The flashcard test suite contains 11 tests.

It covers:

valid topic loading;

invalid topic handling;

card flipping;

returning to the prompt;

preventing early recall marking;

remembered-count updates;

review-again updates;

session completion;

session restart;

flip sound event;

completion event.

Current result:

11 tests
11 passed

Instrumented GUI Tests

Instrumented tests are stored under:

app/src/androidTest/

Main test classes include:

LandingScreenTest
ActivityScreenTest
CodeRunnerScreenTest
FlashcardScreenTest
SettingsScreenTest

Flashcard Screen Tests

The flashcard GUI test suite contains nine tests.

It covers:

prompt and progress display;

hidden recall buttons before reveal;

answer display after reveal;

reveal callback;

remembered callback;

review callback;

completion summary;

restart callback;

error state.

Current result:

9 tests
9 passed

Running Local Tests

Windows:

gradlew.bat test

macOS or Linux:

./gradlew test

Running Instrumented Tests

Windows:

gradlew.bat connectedAndroidTest

macOS or Linux:

./gradlew connectedAndroidTest

Project Setup

Requirements

Android Studio

Android SDK

Java Development Kit supported by the project

Android emulator or physical Android device

Git

Internet connection for Python code execution

Project Configuration

Package: com.example.pylearn
Minimum SDK: 26
Build scripts: Kotlin DSL
UI toolkit: Jetpack Compose

The exact compile SDK, target SDK, dependency versions, and plugin versions are defined in the Gradle configuration files.

Running the Application

Clone or download the project.

Open Android Studio.

Select Open.

Choose the PyLearn project folder.

Wait for Gradle synchronisation to finish.

Start an emulator or connect a device.

Select the app run configuration.

Click Run.

The Python Code Runner requires an internet connection.

Building the Project

Android Studio

Build → Assemble Project

For a clean rebuild:

Build → Clean Project
Build → Rebuild Project

Command Line

Windows:

gradlew.bat assembleDebug

macOS or Linux:

./gradlew assembleDebug

The debug APK is normally generated under:

app/build/outputs/apk/debug/

Screenshots

Create the following folder:

docs/images/

Recommended screenshots:

landing-screen.png
topic-options-screen.png
quiz-screen.png
answer-feedback-screen.png
quiz-complete-screen.png
flashcard-prompt-screen.png
flashcard-answer-screen.png
flashcard-complete-screen.png
statistics-screen.png
settings-screen.png
code-runner-screen.png

Landing Screen



Topic Options



Quiz Activity



Answer Feedback



Quiz Completion



Flashcard Prompt



Flashcard Answer



Flashcard Completion



Statistics



Settings



Python Code Runner



Known Limitations

Learning content is limited to six beginner topics.

Each topic currently contains five quiz questions.

Flashcard content is stored locally.

Flashcard results are not saved between sessions.

Quiz progress is stored only on one device.

No cloud synchronisation is available.

No user account system is included.

Python execution requires internet access.

External code execution depends on service availability.

The application currently supports Python only.

Code execution is not performed offline.

No tablet-specific layout has been implemented.

Learning content cannot currently be edited from the user interface.

Future Improvements

Possible improvements include:

additional Python topics;

beginner, intermediate, and advanced learning paths;

more quiz questions;

larger flashcard sets;

saved flashcard progress;

spaced-repetition scheduling;

optional flashcard shuffling;

bookmarked questions;

favourite flashcards;

offline lesson notes;

offline Python execution;

cloud progress backup;

account-based synchronisation;

tablet layouts;

landscape optimisation;

additional sound-volume controls;

haptic feedback preference;

more accessibility settings;

screen-reader testing;

detailed question-history reports;

personalised recommendations;

downloadable learning modules;

teacher-created question sets;

additional programming languages.

Any spaced-repetition system should remain transparent and user-controlled rather than manipulative.

Development Principles

Single Responsibility Principle

Classes are separated according to their responsibilities.

Examples:

screens display UI;

ViewModels manage UI state;

repositories manage data;

Room manages local progress;

DataStore manages preferences;

Retrofit manages network communication;

SoundPlayer manages sound effects.

Separation of Concerns

The application separates:

presentation;

business logic;

local persistence;

preferences;

networking;

sound playback;

navigation.

Dependency Inversion

ViewModels depend on repository interfaces instead of concrete database or network implementations where appropriate.

Reusability

Reusable components and abstractions include:

topic cards;

setting switch cards;

repository interfaces;

ViewModel factories;

sound effects;

activity option cards;

shared theme components.

Testability

Business logic is placed in ViewModels and repositories so it can be tested without depending on the full Android UI.

Version Control

Git and GitHub are used for version control.

Development was organised using meaningful milestone commits, including:

Initial project setup
Add navigation and main screens
Add Python learning topics
Add quiz activities
Add Room progress persistence
Add Statistics screen
Add DataStore settings
Add Python Code Runner
Add unit tests
Add Compose UI tests
Redesign landing topic cards
Add sound effects and persistent sound settings
Add flashcard learning activities
Add tests for flashcard activities

Git Commands

Check changes:

git status

Stage changes:

git add .

Commit changes:

git commit -m "Update project documentation"

Push changes:

git push

PyLearn was developed as an Android educational application project using Kotlin and Jetpack Compose.

Acknowledgements

The project uses Android and open-source technologies including:

Kotlin;

Jetpack Compose;

Material Design 3;

Room;

Preferences DataStore;

Retrofit;

Kotlin Coroutines;

Judge0;

JUnit;

Compose UI Test.

Generative artificial intelligence was used to support planning, debugging, code explanation, test development, documentation, and learning during development.

All generated suggestions were reviewed, tested, adapted, and integrated by the developer.

Project Status

The current implementation includes:

six Python topics;

30 quiz questions;

three quiz activity types;

flashcard review activities;

Room progress persistence;

statistics;

persistent settings;

dark mode;

larger text;

sound preferences;

correct, incorrect, completion, and flashcard sounds;

Python code execution;

local unit tests;

instrumented Compose UI tests.

The main application functionality is implemented and operational.
