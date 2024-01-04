package com.udemy.jettrivia

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.udemy.jettrivia.Screen.QuestionsViewModel
import com.udemy.jettrivia.Screen.TriviaHome
import com.udemy.jettrivia.ui.theme.JetTriviaTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * This application topics include parsing JSON data from APIs and using
 * Retrofit to do API Calls (Getting JSON information from APIs)
 *
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TriviaHome(viewModel = hiltViewModel())
        }
    }
}





@Preview(showBackground = true)
@Composable
fun GenericPreview() {
    JetTriviaTheme {
        TriviaHome(viewModel = hiltViewModel())
    }
}