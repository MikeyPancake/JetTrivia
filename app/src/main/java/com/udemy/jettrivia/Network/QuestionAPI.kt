package com.udemy.jettrivia.Network

import com.udemy.jettrivia.Data.Question
import retrofit2.http.GET
import javax.inject.Singleton

/**
 * This file can also be called a DAO (Data Access Object) as it directly interacts with
 * the repository class which then does the fetching of data from the JSON API
 */
@Singleton
interface QuestionAPI {

    // Gets the json file from the API and uses a suspend function to return the Questions to the
    // Data.Question file
    @GET("world.json") // appends this path to the Base URL in the constance
    suspend fun getAllQuestions() : Question
}