package com.pradeep.movieinfo.di

import android.content.Context
import com.pradeep.movieinfo.data.network.MovieApi
import com.pradeep.movieinfo.repository.DefaultMovieMovieRepository
import com.pradeep.movieinfo.repository.MovieRepository
import com.pradeep.movieinfo.util.ConnectivityInterceptor
import com.pradeep.movieinfo.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Singleton
    @Provides
    fun providesOkHttp(@ApplicationContext context : Context) = OkHttpClient.Builder()
        .addInterceptor(ConnectivityInterceptor(context))
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun providesMovieApi(retrofit: Retrofit) = retrofit.create(MovieApi::class.java)

    @Singleton
    @Provides
    fun providesMovieRepository(movieApi: MovieApi) = DefaultMovieMovieRepository(movieApi) as MovieRepository
}