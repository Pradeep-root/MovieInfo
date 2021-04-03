package com.pradeep.movieinfo.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pradeep.movieinfo.data.models.Movie
import com.pradeep.movieinfo.data.models.MovieResponse
import com.pradeep.movieinfo.repository.MovieRepository
import com.pradeep.movieinfo.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import retrofit2.Response

@ExperimentalCoroutinesApi
class MovieViewModelTest{


    private lateinit var viewModel: MovieViewModel
    private lateinit var repository: MovieRepository
    private val movieList = mutableListOf<Movie>()

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        repository = mockk()
        viewModel = MovieViewModel(repository)
        for (i in 1..20){
            movieList.add(Movie(464052,
                "/8UlWHLMpgZm9bx6QYh0NFoq67TZ.jpg",
                "2020-12-16",
                "Wonder Woman 1984",
                "A botched store robbery places Wonder Woman in a global battle" +
                        " against a powerful and mysterious ancient force that puts her powers in jeopardy.",
                6.8f,
                null))
        }
    }

    @Test
    fun `test list of url path is coming for posters`() = runBlockingTest {
        //Given
        coEvery { repository.getNowPlaying() } coAnswers   {
            Resource.success(MovieResponse(0, movieList, 0))
        }

        //When
        viewModel.getNowPlayingMovies()
        val status = viewModel.nowPlayingMovies.value

        //Then
        assertEquals(20, status?.data?.results?.size)

    }

    @Test
    fun `test now playing error case`() = runBlockingTest {
        //Given
        coEvery { repository.getNowPlaying() } coAnswers   {
            Resource.error("Error case", null)
        }

        //When
        viewModel.getNowPlayingMovies()
        val status = viewModel.nowPlayingMovies.value

        //Then
        assertEquals(Resource.error("Error case", null), status)

    }

    @Test
    fun `test list of movie api data is coming for popular movie list`() = runBlockingTest {
        //Given
        coEvery { repository.getPopularMovies(1) } coAnswers   {
            Response.success(MovieResponse(1, movieList, 500))
        }

        //When
        val status = repository.getPopularMovies(1)

        //Then
        assertEquals(Response.success(MovieResponse(1, movieList, 500))
            .body()?.results?.size, status.body()?.results?.size)

    }
}