package com.pradeep.movieinfo.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pradeep.movieinfo.data.models.Movie
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

@ExperimentalCoroutinesApi
class DetailViewModelTest{

    private lateinit var viewModel: DetailViewModel
    private lateinit var repository: MovieRepository

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
         repository = mockk<MovieRepository>()
         viewModel = DetailViewModel(repository)

    }

    @Test
    fun `test movie detail invalid id error response`() = runBlockingTest {

        //Given
        coEvery { repository.getMovieDetail(-1) } answers  {
           Resource.error("Error occur", null)
        }

        //When
        viewModel.getMovieDetails(-1)
        val status = viewModel.movieDetails.value

        //Then
        assertEquals(Resource.error("Error occur", null), status)

    }

    @Test
    fun `test movie detail valid id success response`(){
        //Given
        coEvery { repository.getMovieDetail(464052) } answers   {
            Resource.success(Movie(464052,
                "/8UlWHLMpgZm9bx6QYh0NFoq67TZ.jpg",
                "2020-12-16",
                "Wonder Woman 1984",
                "A botched store robbery places Wonder Woman in a global battle" +
                        " against a powerful and mysterious ancient force that puts her powers in jeopardy.",
                6.8f,
                null))
        }

        //When
        viewModel.getMovieDetails(464052)
        val status = viewModel.movieDetails.value

        //Then
        assertEquals(464052 ,status?.data?.id)
        assertEquals("/8UlWHLMpgZm9bx6QYh0NFoq67TZ.jpg" ,status?.data?.posterPath)
        assertEquals("2020-12-16" ,status?.data?.releaseDate)
        assertEquals("Wonder Woman 1984" ,status?.data?.title)
        assertEquals("A botched store robbery places Wonder Woman in a global battle " +
                "against a powerful and mysterious ancient force that puts her powers in jeopardy."
            ,status?.data?.overview)
        assertEquals(6.8f ,status?.data?.rating)

    }

}