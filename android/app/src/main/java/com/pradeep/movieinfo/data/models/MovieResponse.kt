package com.pradeep.movieinfo.data.models

import com.google.gson.annotations.SerializedName

class MovieResponse(

    @SerializedName("page")
    val page : Int?,

    @SerializedName("results")
    val results : MutableList<Movie>?,

    @SerializedName("total_pages")
    val totalPages : Int?

)