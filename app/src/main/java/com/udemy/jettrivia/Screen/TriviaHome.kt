package com.udemy.jettrivia.Screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.udemy.jettrivia.Components.QuestionDisplay
import com.udemy.jettrivia.Components.Questions
import com.udemy.jettrivia.ui.theme.JetTriviaTheme

@Composable
fun TriviaHome(viewModel: QuestionsViewModel = hiltViewModel()){

    JetTriviaTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            Questions(viewModel)
            //QuestionDisplay()
        }
    }
}