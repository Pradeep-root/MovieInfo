package com.pradeep.movieinfo.ui.movie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pradeep.movieinfo.R
import com.pradeep.movieinfo.data.models.Movie
import com.pradeep.movieinfo.util.Constants.Companion.IMG_BASE_URL
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.poster_item.view.*

class PosterAdapter(private val movies: ArrayList<Movie>) : RecyclerView.Adapter<PosterAdapter.ViewHolder>(){


   private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterAdapter.ViewHolder {
        return PosterAdapter.ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.poster_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PosterAdapter.ViewHolder, position: Int) {
      holder.bind(movies[position])
    }

     class ViewHolder(private val itemView : View) : RecyclerView.ViewHolder(itemView)  {
            fun bind(movie: Movie){
              Glide.with(itemView)
                  .load(IMG_BASE_URL+movie.posterPath)
                  .placeholder(itemView.context.getDrawable(R.drawable.ic_movie_placeholder))
                  .error(itemView.context.getDrawable(R.drawable.ic_movie_img_error))
                  .diskCacheStrategy(DiskCacheStrategy.DATA)
                  .into(itemView.img_poster)
            }
    }

    override fun getItemCount(): Int {
       return movies.size
    }

    fun updateList(newMovies: MutableList<Movie>){
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }
}