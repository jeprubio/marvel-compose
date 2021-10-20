# Marvel Compose App
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
