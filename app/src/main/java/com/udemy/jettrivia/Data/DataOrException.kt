package com.udemy.jettrivia.Data

/**
 * Wrapper class that allows us to extract information from the retrieval process
 * which will then allow us to pass status updates to the UI
 *
 * T allows us to use any data type
 */
data class DataOrException <T, Boolean, E : Exception> (

    var data : T? = null,
    var loadingState: Boolean? = null,
    var e : E? = null
)