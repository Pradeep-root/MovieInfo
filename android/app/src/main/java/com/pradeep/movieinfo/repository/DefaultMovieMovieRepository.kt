package com.pradeep.movieinfo.repository

import com.pradeep.movieinfo.data.models.Movie
import com.pradeep.movieinfo.data.models.MovieResponse
import com.pradeep.movieinfo.data.network.MovieApi
import com.pradeep.movieinfo.util.Resource
import java.io.IOException
import javax.inject.Inject

class DefaultMovieMovieRepository @Inject constructor(private val movieApi: MovieApi) : MovieRepository {


    override suspend fun getNowPlaying(): Resource<MovieResponse> {
        return  try {
           val response = movieApi.getNowPlaying()
           val result = response.body()

           if(response.isSuccessful && result != null){
                Resource.success(result)
           }else{
               Resource.error(response.message(), null)
           }

       }catch (e : IOException){
          Resource.error( e.message.toString(), null)
       }
    }

    override suspend fun getPopularMovies(pageNumber : Int) = movieApi.getPopular(page = pageNumber)


    override suspend fun getMovieDetail(movieId: Int): Resource<Movie> {
        return  try {
            val response = movieApi.getMovieDetail(movie_id = movieId)
            val result = response.body()

            if(response.isSuccessful && result != null){
                Resource.success(result)
            }else{
                Resource.error(response.message(), null)
            }

        }catch (e : IOException){
            Resource.error( e.message.toString(), null)
        }
    }
}