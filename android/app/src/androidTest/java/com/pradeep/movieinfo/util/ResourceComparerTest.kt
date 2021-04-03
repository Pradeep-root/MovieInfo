package com.pradeep.movieinfo.util

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pradeep.movieinfo.R
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ResourceComparerTest{


    private lateinit var resourceComparer: ResourceComparer

    @Before
    fun setup(){
        resourceComparer = ResourceComparer()
    }

    @Test
    fun stringResourceSameInternetCheckMsg_returnTrue(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context, R.string.no_internet_connection, "No internet connection, Please check.")

        assertThat(result).isTrue()
    }

    @Test
    fun stringResourceSameAsHeaderTextPlaying_returnTrue(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context, R.string.heading_text_playing, "Playing")

        assertThat(result).isTrue()
    }

    @Test
    fun stringResourceSameAsHeaderTextMostPopular_returnTrue(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context, R.string.heading_text_popular, "Most popular")

        assertThat(result).isTrue()
    }

    @Test
    fun stringResourceSameAsOverview_returnTrue(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context, R.string.overview_lbl, "Overview")

        assertThat(result).isTrue()
    }
}