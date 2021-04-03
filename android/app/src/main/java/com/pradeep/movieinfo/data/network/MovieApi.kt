package com.pradeep.movieinfo.data.network

import com.pradeep.movieinfo.data.models.Movie
import com.pradeep.movieinfo.data.models.MovieResponse
import com.pradeep.movieinfo.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("/3/movie/now_playing")
    suspend fun getNowPlaying(
        @Query("language")
        language : String = "en-US",
        @Query("page")
        page : String = "undefined",
        @Query("api_key")
        apiKey: String = API_KEY) : Response<MovieResponse>

    @GET("/3/movie/popular")
    suspend fun getPopular(
        @Query("language")
        language : String = "en-US",
        @Query("page")
        page : Int,
        @Query("api_key")
        apiKey: String = API_KEY) : Response<MovieResponse>

    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id")
        movie_id : Int,
        @Query("language")
        language : String = "en-US",
        @Query("api_key")
        apiKey: String = API_KEY) : Response<Movie>
}