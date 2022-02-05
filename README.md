# Marvel Compose App

[![MARVEL-COMPOSE](https://circleci.com/gh/jeprubio/marvel-compose.svg?style=svg)](https://app.circleci.com/pipelines/github/jeprubio/marvel-compose?branch=main)

App created in compose that displays Marvel heroes using the [marvel api](https://developer.marvel.com/docs)

This app uses MVVM but trying to follow the [Recommended app architecture](https://developer.android.com/jetpack/guide#recommended-app-arch) 
at the same time.

## Getting Started

The project files can be found at https://github.com/jeprubio/marvel-compose/
	
Easiest and simple way to download code from Github is to download the whole code in a zip file by clicking the "Code" / "Download Zip" button on the right hand side of the page.
	
You can then save the zip file into a convenient location on your computer and start working on it.
	
Another way to get the code is using git:
	
git clone git@github.com:jeprubio/marvel-compose.git

## Prerequisites

Android studio should be installed in order to run the app.

Follow the instructions at https://developer.android.com/studio/install depending on which SO your computer is running.

## How to run the application

Open the code in android studio.

Edit the `local.properties` file adding a value for `MARVEL_KEY`. E.g.:
`MARVEL_KEY=1234567890abcdef1234567890abcdef12345678`

Wait until gradle finishes and press the play button.

To run the unit tests: ./gradlew lint testDebug --continue
To run the UI tests: ./gradlew connectedDebugAndroidTest

To record the screenshots test: ./gradlew executeScreenshotTests -Precord
To execute the screenshots test: ./gradlew executeScreenshotTests

## Screenshots

![Characters List](https://raw.github.com/jeprubio/marvel-compose/main/screenshots/readme/1-characters-list.jpg)
![Character Details](https://raw.github.com/jeprubio/marvel-compose/main/screenshots/readme/2-character-details.jpg)
![Comics List](https://raw.github.com/jeprubio/marvel-compose/main/screenshots/readme/3-comics-list.jpg)
![Comic Details](https://raw.github.com/jeprubio/marvel-compose/main/screenshots/readme/4-comic-details.jpg)

## Libraries Used

- [Timber](https://github.com/JakeWharton/timber) - Library to perform logging.

- [Gson](https://github.com/google/gson) - To parse from and to json (configured retrofit to use it).

- [Retrofit](https://github.com/square/retrofit) - To perform API calls and parse the response.

- [Dagger-Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - For dependency injection.

- [Coil](https://github.com/coil-kt/coil) - Loading and caching images.

- [MultiNavCompose](https://github.com/jeziellago/multinavcompose) - Multi-module navigation built on Jetpack Navigation Compose.

## Testing libraries
- [Junit5](https://junit.org/junit5/docs/current/user-guide/) - For writing tests.

- [Mockk](https://mockk.io/) - A mocking library similar to mockito that allows you to mock suspend functions.

- [kotlinx-coroutines-test](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/) - It has test utilities for working with coroutines.

- [Espresso](https://developer.android.com/training/testing/espresso) - To perform Android UI tests
