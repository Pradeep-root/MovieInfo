package com.pradeep.movieinfo.ui.movie

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pradeep.movieinfo.R
import com.pradeep.movieinfo.util.Constants.Companion.REQUEST_PAGE_RESULT_SIZE
import com.pradeep.movieinfo.util.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.title_heading_layout.view.*

@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {


    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var posterAdapter: PosterAdapter


    private lateinit var viewModel : MovieViewModel

    private val TAG = MovieActivity::class.qualifiedName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        viewModel.getNowPlayingMovies()
        viewModel.getPopularMovies()

        in_heading_popular.tv_title.text = getText(R.string.heading_text_popular)

        setupPosterRecyclerView()
        setupPopularMoviesRecyclerView()

        setupNowPlayingObserver()
        setupPopularMoviesObserver()

    }


    private fun setupPosterRecyclerView() {
        rv_playing_horizontal.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        posterAdapter = PosterAdapter(arrayListOf())
        rv_playing_horizontal.adapter = posterAdapter
    }

    private fun setupPopularMoviesRecyclerView() {
        moviesAdapter = MoviesAdapter(arrayListOf())
        rv_popular_vertical.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            val dividerItemDecorator = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            getDrawable(R.drawable.movie_item_devider)?.let { dividerItemDecorator.setDrawable(it) }
            addItemDecoration(dividerItemDecorator)
            adapter = moviesAdapter
            addOnScrollListener(this@MovieActivity.scrollListener) // for pagination
        }

    }

    private fun setupNowPlayingObserver(){
        viewModel.nowPlayingMovies.observe(this, Observer {
            it?.let { resource ->
                when(resource.status){
                    Status.SUCCESS ->{
                        Log.d(TAG, "${resource.status}")
                        tv_error_msg.visibility = View.INVISIBLE
                        progress_on_poster.visibility = View.INVISIBLE
                        resource.data?.results?.let { it -> posterAdapter.updateList(it) }
                    }

                    Status.ERROR ->{
                        Log.d(TAG, "${resource.status}")
                        tv_error_msg.visibility = View.VISIBLE
                        tv_error_msg.text = resource.message
                        progress_on_poster.visibility = View.INVISIBLE
                        resource.message?.let { it -> showSnackBar(it) }
                    }

                    Status.LOADING ->{
                        Log.d(TAG, "${resource.status}")
                        tv_error_msg.visibility = View.INVISIBLE
                        progress_on_poster.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun setupPopularMoviesObserver() {
        viewModel.popularMovies.observe(this, {
            it?.let {  resource ->
                when(resource.status){
                    Status.SUCCESS ->{
                        isLoading = false
                        Log.d(TAG, "${resource.status}")
                        progress_on_movie.visibility = View.INVISIBLE
                        resource.data?.results?.let { it -> moviesAdapter.updateList(it) }
                       // val totalPages = resource.data?.totalPages?.div(20 +2)
                        isLastPage = viewModel.popularMoviePageNumber == resource.data?.totalPages
                    }

                    Status.ERROR ->{
                        isLoading = false
                        Log.d(TAG, "${resource.status}")
                        progress_on_movie.visibility = View.INVISIBLE
                        resource.message?.let { it -> showSnackBar(it) }
                    }

                    Status.LOADING ->{
                        isLoading = true
                        progress_on_movie.visibility = View.VISIBLE
                        Log.d(TAG, "${resource.status}")
                    }
                }
            }
        })
    }

    private fun showSnackBar(message : String){
        val snackBar = Snackbar.make(
            main_movie_layout, message,
            Snackbar.LENGTH_LONG
        )
        snackBar.show()
    }

    var isLastPage = false
    var isLoading = false
    var isScrolling = false

   private val scrollListener = object : RecyclerView.OnScrollListener(){

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isLastPageItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThenVisible = totalItemCount >= REQUEST_PAGE_RESULT_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isLastPageItem && isNotAtBeginning &&
                    isTotalMoreThenVisible && isScrolling

            if(shouldPaginate){
                viewModel.getPopularMovies()
                isScrolling = false
            }

        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }
    }
}
