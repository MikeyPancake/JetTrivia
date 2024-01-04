package com.udemy.jettrivia.Repository

import android.util.Log
import com.udemy.jettrivia.Data.DataOrException
import com.udemy.jettrivia.Data.QuestionItem
import com.udemy.jettrivia.Network.QuestionAPI
import javax.inject.Inject

class QuestionRepository @Inject constructor(
    private val api : QuestionAPI) {

    /**
     * this function wraps the list of questions using the dataOrException class
     * which allows us to pass information (meta data) along with the results...
     * such as is loading? or loading failed because.....
     *
     * This is useful when ever you are retrieving data from a server or API
     */
    private val dataOrException =
        DataOrException<ArrayList<QuestionItem>,
            Boolean,
            Exception>()

    /**
     * Function is called which returns the arrayList of questions with extra data
     */

    suspend fun getAllQuestions() : DataOrException<ArrayList<QuestionItem>, Boolean, java.lang.Exception>{

        try{

            // when the getAllQuestions is called, this checks if the loading
            // state is true.
            dataOrException.loadingState = true
            dataOrException.data = api.getAllQuestions()

            // if data has data set the loading state to false
            if (dataOrException.data.toString().isNotEmpty()){
                dataOrException.loadingState = false
            }


        }catch(exception: Exception){
            // if there is an exception, pass it to the dataOrException class
            dataOrException.e = exception
            Log.e(
                "Question Repository",
                "getAllQuestions Exception: ${dataOrException.e!!.localizedMessage}")

        }
        return dataOrException
    }

}