package com.udemy.jettrivia.DI

import com.google.gson.Gson
import com.udemy.jettrivia.Network.QuestionAPI
import com.udemy.jettrivia.Repository.QuestionRepository
import com.udemy.jettrivia.Utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.DefineComponent
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * This is where we register things and create the providers for usability throughout the app. These
 * calls are done in the background by Hilt and Dagger.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideQuestionRepository(api: QuestionAPI) = QuestionRepository(api)

    // Instantiate the retrofit
    @Singleton //creates single instance
    @Provides // provides the function to anyone who needs this dependency
    fun provideQuestionApi(): QuestionAPI{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuestionAPI::class.java)
    }

}