package com.udemy.jettrivia

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * This class governs the entire app and is responsible for binding all the dependencies
 * for the entire app. This must be registered in the manifest file.
 *
 */
@HiltAndroidApp
class TriviaApplication : Application() {
}