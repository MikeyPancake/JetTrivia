package com.udemy.jettrivia.Screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udemy.jettrivia.Data.DataOrException
import com.udemy.jettrivia.Data.QuestionItem
import com.udemy.jettrivia.Repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(
    private val repository: QuestionRepository

) : ViewModel() {

    val data : MutableState<DataOrException
    <ArrayList<QuestionItem>,
            Boolean,
            Exception>>
    = mutableStateOf(
        DataOrException(null, true, Exception(""))
    )

    // on initializing, get all questions
    init {
        getAllQuestions()
    }

    // Coroutine that fetches the question data from the repository
    private fun getAllQuestions() {

        viewModelScope.launch {
            // Sets loadingState to true
            data.value.loadingState = true
            // sets data using the repository function
            data.value = repository.getAllQuestions()

            if (data.value.data.toString().isNotEmpty()){
                data.value.loadingState = false

            }
        }
    }
}