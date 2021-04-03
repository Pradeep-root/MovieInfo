package com.pradeep.movieinfo.ui.custom

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class RatingViewTest {


    private lateinit var ratingView : RatingView
    private lateinit var context : Context

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        context = ApplicationProvider.getApplicationContext()
        ratingView = RatingView(context)
    }

    @Test
    fun testLessThen50_returnString(){
        ratingView.setRatingProgress(50)
        assertThat(ratingView.getRating()).isEqualTo("50%")
    }

    @Test
    fun testLessThen60_returnString(){
        ratingView.setRatingProgress(60)
        assertThat(ratingView.getRating()).isEqualTo("60%")
    }

    @Test
    fun testLessThen100_returnString(){
        ratingView.setRatingProgress(100)
        assertThat(ratingView.getRating()).isEqualTo("100%")
    }

    @Test
    fun testLessThen45_returnString(){
        ratingView.setRatingProgress(45)
        assertThat(ratingView.getRating()).isEqualTo("45%")
    }


    @Test
    fun testLessThen20_returnString(){
        ratingView.setRatingProgress(20)
        assertThat(ratingView.getRating()).isEqualTo("20%")
    }

    @After
    fun tearDown(){
        ratingView == null
    }

}