package com.pradeep.movieinfo.ui.details

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.pradeep.movieinfo.R
import com.pradeep.movieinfo.data.models.Movie
import com.pradeep.movieinfo.util.Constants.Companion.IMG_BASE_URL
import com.pradeep.movieinfo.util.Constants.Companion.PUT_EXTRA_KEY_SELECTED_MOVIE_ID_PUT
import com.pradeep.movieinfo.util.Status
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.tag_layout.view.*


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel

    companion object {
        val TAG = DetailActivity::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        val movieId = intent.getIntExtra(PUT_EXTRA_KEY_SELECTED_MOVIE_ID_PUT, -1)
        if (movieId != -1) {
            viewModel.getMovieDetails(movieId)
        } else {
            Log.i(TAG, "invalid movie id")
        }
        setupMovieDetailObserver()
        backSoftButtonListner()
    }

    private fun backSoftButtonListner() {
        img_back_button.setOnClickListener {
            finish()
        }
    }

    private fun setupMovieDetailObserver() {
        viewModel.movieDetails.observe(this, {

            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Log.d(TAG, "${resource.status}")
                        progress_detail.visibility = View.INVISIBLE
                        tv_overview_label.visibility = View.VISIBLE
                        resource.data?.let { it -> displayMovieData(it) }
                    }

                    Status.ERROR -> {
                        Log.d(TAG, "${resource.status}")
                        tv_overview_label.visibility = View.INVISIBLE
                        progress_detail.visibility = View.INVISIBLE
                        resource.message?.let { it1 -> showSnackBar(it1) }
                    }

                    Status.LOADING -> {
                        tv_overview_label.visibility = View.INVISIBLE
                        Log.d(TAG, "${resource.status}")
                        progress_detail.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun displayMovieData(movie: Movie) {
        Glide.with(this)
            .load(IMG_BASE_URL + movie.posterPath)
            .placeholder(getDrawable(R.drawable.ic_movie_placeholder))
            .error(getDrawable(R.drawable.ic_movie_img_error))
            .into(img_poster)
        tv_movie_title.text = movie.title
        tv_movie_date.text = movie.releaseDate
        tv_overview_detail.text = movie.overview

        movie.genres?.forEach {
            val tagRelativeLayout =
                layoutInflater.inflate(R.layout.tag_layout, flexBoxLayout, false) as RelativeLayout
            tagRelativeLayout.tag_name.text = it.name
            flexBoxLayout.addView(tagRelativeLayout)
        }
    }

    private fun showSnackBar(message: String) {
        val snackBar = Snackbar.make(
            scroll_view, message,
            Snackbar.LENGTH_LONG
        )
        snackBar.show()
    }
}