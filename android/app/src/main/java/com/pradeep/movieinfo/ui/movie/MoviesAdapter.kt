package com.pradeep.movieinfo.ui.movie

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pradeep.movieinfo.R
import com.pradeep.movieinfo.data.models.Movie
import com.pradeep.movieinfo.ui.details.DetailActivity
import com.pradeep.movieinfo.util.ConnectivityInterceptor
import com.pradeep.movieinfo.util.Constants.Companion.IMG_BASE_URL
import com.pradeep.movieinfo.util.Constants.Companion.PUT_EXTRA_KEY_SELECTED_MOVIE_ID_PUT
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesAdapter(private val movies : ArrayList<Movie>) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.movie_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra(PUT_EXTRA_KEY_SELECTED_MOVIE_ID_PUT, movies[position].id)
            }
            if(ConnectivityInterceptor(context).isInternetConnection()){
                context.startActivity(intent)
            }else{
               Snackbar.make(it, context.getString(R.string.no_internet_connection),
                    Snackbar.LENGTH_LONG).show()
            }
        }
    }


    override fun getItemCount() = movies.size

   inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       fun bind(movie: Movie){
           Glide.with(itemView)
               .load(IMG_BASE_URL+movie.posterPath)
               .placeholder(context.getDrawable(R.drawable.ic_movie_placeholder))
               .error(context.getDrawable(R.drawable.ic_movie_img_error))
               .diskCacheStrategy(DiskCacheStrategy.DATA)
               .into(itemView.poster)
           itemView.title.text = movie.title
           itemView.releaseDate.text = movie.releaseDate
           val percent = movie.rating?.times(10) // multiply by 10
           percent?.let { itemView.rating.setRatingProgress(it.toInt()) }
       }
    }

    fun updateList(newMovies: MutableList<Movie>){
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }
}