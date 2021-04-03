package com.pradeep.movieinfo.data.models

import com.google.gson.annotations.SerializedName

class Movie(

    @SerializedName("id")
    val id : Int?,

    @SerializedName("poster_path")
    val posterPath : String?,

    @SerializedName("release_date")
    val releaseDate : String?,

    @SerializedName("title")
    val title : String?,

    @SerializedName("overview")
    val overview : String?,

    @SerializedName("vote_average")
    val rating : Float?,

    @SerializedName("genres")
    val genres : List<Genres>?

){

     inner class Genres(
        @SerializedName("name")
        val name : String?)
}