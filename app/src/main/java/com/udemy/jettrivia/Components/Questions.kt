package com.udemy.jettrivia.Components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.udemy.jettrivia.Data.QuestionItem
import com.udemy.jettrivia.Screen.QuestionsViewModel
import java.lang.Exception

@Composable
fun Questions(viewModel: QuestionsViewModel) {

    // Gets the questions and converts the array list of questions to a mutable list
    val questions = viewModel.data.value.data?.toMutableList()

    // Increment question index state
    val questionIndex = remember {
        mutableStateOf(0)
    }

    // If loading state is true, display the progress circle
    if (viewModel.data.value.loadingState == true){
        Log.d("Loading", " Questions: .....Loading......")
        ProgressCircle()
    }
    else{
        Log.d("Loading", " Questions: .....Loading Stopped....")
        Log.d("Results", "Questions List Size: ${questions?.size} ")

        // This tries to get the index of the question
        val question = try {
            questions?.get(questionIndex.value)
        }
        catch (ex: Exception){
            null
        }

        // If the questions contain questions
        if (questions != null){
            QuestionDisplay(
                question = question!!,
                questionIndex = questionIndex,
                viewModel = viewModel){
                // this receives the question index from the state holder, increments by one and then
                // sends it to the question display composable so we can go to the next question
                questionIndex.value = questionIndex.value +1

            }
        }
    }
}

//@Preview
@Composable
fun QuestionDisplay(
    question: QuestionItem,
    questionIndex: MutableState<Int>,
    viewModel : QuestionsViewModel,
    onNextClicked : (Int) -> Unit){

    // State holder for dynamically adding the boxes based on the data
    val choicesState = remember(question) {
        question.choices.toMutableList()
    }

    // Holds the state of the answer selected by the user
    val answerState = remember (question) {
        mutableStateOf<Int?>(null)
    }

    // Holds the state of the correct answer
    val correctAnswerState = remember (question) {
        mutableStateOf<Boolean?>(null)
    }

    // Here we check if the user selected answer is the same as the actual answer
    val updateAnswer : (Int) -> Unit = remember(question){ {
        answerState.value = it
        // Checks if user selected choice is equal to the correct answer
        correctAnswerState.value = choicesState[it] == question.answer
    } }

    // Gets the questions and converts the array list of questions to a mutable list which is
    // used to get the total number of questions for the question tracker
    val questions = viewModel.data.value.data?.toMutableList()

    // Defines the dash line effect
    val pathEffect = PathEffect.dashPathEffect(
        floatArrayOf(10f,10f), 0f)

    // This allows you to use m3 theme in the custom divider which is outside the composable
    val lineColor = MaterialTheme.colorScheme.onBackground

    // Defines the answer box outline color
    val answerBoxColor = MaterialTheme.colorScheme.outline

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(5.dp),
         //color = AppColors.mBlack
        color = MaterialTheme.colorScheme.background

    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            // Sets Question tracker in UI with the index and total questions passed
            QuestionTracker(counter = questionIndex.value, totalQuestions = questions!!.size )

            // Sets the progess bar in the UI once a certain point is reached
            if (questionIndex.value >= 0)
                ProgressTracker(score = questionIndex.value)

            // Sets Dotted Line in UI
            CustomDottedDivider(pathEffect, lineColor)

            // Column that contains question and choices
            Column {
                // Sets Question Box in UI
                QuestionBox(question)

                // Sets Answer Selection Choices in UI
                AnswerSelection(choicesState, answerBoxColor, answerState, updateAnswer,
                    correctAnswerState
                )

                // If the correct answer is chosen, sets the next button in the UI
                if (correctAnswerState.value == true){
                    // Calls the button composable with custom designing
                    CustomButton(
                        modifier = Modifier
                            .height(50.dp)
                            .padding(2.dp)
                            //.padding(top = 25.dp, start = 1.dp, end = 1.dp)
                            .align(alignment = Alignment.CenterHorizontally),
                        shape = MaterialTheme.shapes.extraLarge,
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        text = "Next",
                        // on click we pass the question index (Increment by one in the question composable
                        onClick = { onNextClicked(questionIndex.value) })

                }
            }
        }
    }
}

@Preview
@Composable
fun ProgressTracker(score: Int = 1){

    // Defines the outline of the progress bar
    val progressBarOutline = MaterialTheme.colorScheme.outline

    // Defines the gradient of the progress bar
    val progressGradient = Brush.linearGradient(listOf(Color(0xFFFA0000), Color(0xFF03911A)))

    // Uses material theme to set the color of the button to transparent
    val buttonColor = ButtonDefaults.buttonColors(
        containerColor = Color.Transparent,
        contentColor = contentColorFor(Color.Transparent)
    )

    // Controls the state of the progress factor which is called in the fillMaxWidth of the button
    val progressFactorState by remember (score) {
        // Creates a float that is a multiple of a factor
        mutableStateOf(score * 0.00021f)

    }

    Row (
        modifier = Modifier
            .padding(3.dp)
            .background(Color.Transparent)
            .fillMaxWidth()
            .height(15.dp)
            .border(
                width = 2.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        progressBarOutline,
                        progressBarOutline
                    )
                ),
                shape = MaterialTheme.shapes.extraLarge
            )
            // This clips the row to only show the gradient in the shap we created
            .clip(
                RoundedCornerShape(
                    topStartPercent = 50,
                    topEndPercent = 50,
                    bottomEndPercent = 50,
                    bottomStartPercent = 50
                )
            ),

        verticalAlignment = Alignment.CenterVertically

    ){
        // Using a button allows us to increment the progress bar in the "fillMaxWidth"
        Button(
            contentPadding = PaddingValues(1.dp),
            onClick = {},
            enabled = false,
            elevation = null,
            colors = buttonColor,
            modifier = Modifier
                .background(brush = progressGradient)
                .fillMaxWidth(progressFactorState)

        ){

        }
    }
}

//@Preview
@Composable
fun QuestionTracker(counter: Int, totalQuestions: Int){

    // Sets Movie Year
    Text(buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        ){
            // Add 1 to the counter as we receive the counter with index of 0 initially so the
            // first question (position 0) will display question 1 and so forth
            append("Question ${counter + 1} / ")
        }
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp
            )
        ){
            append(" $totalQuestions")
        }
    }, modifier = Modifier.padding(10.dp))

}

@Composable
fun CustomDottedDivider(pathEffect : PathEffect, lineColor : Color){

    Canvas(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(1.dp),
    ){
        //FUnction to draw a line
        drawLine(
            color = lineColor,
            start = Offset(0f,0f),
            end = Offset(size.width, y = 0f),
            pathEffect = pathEffect
        )
    }
}

@Composable
private fun QuestionBox(question: QuestionItem) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxHeight(0.6f),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = question.question,
            modifier = Modifier
                .padding(6.dp)
                .fillMaxSize(),
            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
            fontWeight = MaterialTheme.typography.headlineLarge.fontWeight,
            lineHeight = MaterialTheme.typography.headlineLarge.lineHeight,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun AnswerSelection(
    choicesState: MutableList<String>,
    answerBoxColor: Color,
    answerState: MutableState<Int?>,
    updateAnswer: (Int) -> Unit,
    correctAnswerState: MutableState<Boolean?>
) {
    choicesState.forEachIndexed { index, answerText ->
        // Creates a row for each answer possibility
        Row(
            modifier = Modifier
                .padding(3.dp)
                .fillMaxWidth()
                .height(45.dp)
                .border(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            answerBoxColor,
                            answerBoxColor
                        )
                    ),
                    shape = MaterialTheme.shapes.extraLarge
                )
                .background(Color.Transparent),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = answerState.value == index,
                onClick = {
                    updateAnswer(index)
                },
                modifier = Modifier
                    .padding(start = 15.dp),
                colors = RadioButtonDefaults.colors(
                    selectedColor =
                    if (correctAnswerState.value == true &&
                        index == answerState.value
                    ) {
                        // If correct answer is selected make the rb green
                        Color.Green
                    } else {
                        // If wrong answer is selected make the rb red
                        Color.Red
                    }
                )
            )
            // Sets Text String to row
            Text(
                text = answerText,
                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                color =
                // Changes the color of the text based on user selection
                if (answerState.value == index) {
                    if (correctAnswerState.value == true) Color.Green else Color.Red
                }
                // Keep text as secondary color
                else MaterialTheme.colorScheme.secondary
                )
        }// End of Row
    }
}

@Composable
fun CustomButton(
    onClick : () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = ButtonDefaults.elevatedShape,
    containerColor: Color,
    contentColor : Color,
    text: String = ""
){
    val buttonColor = ButtonDefaults.buttonColors(
        containerColor = containerColor,
        contentColor = contentColorFor(contentColor)
    )
    Button(
        modifier = modifier,
        shape = shape,
        colors = buttonColor,
        onClick = onClick
    ) {
        Text(text)
    }
}

/**
 * Composable that sets the loading progress bar to center of screen
 */
@Composable
fun ProgressCircle(){
    Box (
        modifier = Modifier
            .size(75.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier, color = Color.White)
    }
}

