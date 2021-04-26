package com.project.moviemania.activities.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.project.moviemania.databinding.AdapterMovieBinding
import com.project.moviemania.model.Movie

class MovieAdapter(private val list: ArrayList<Movie>, private val listener: Listener): RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    interface Listener {
        fun onSelectMovie(movieId: Int)
        fun loadMore()
    }

    class ViewHolder(binding: AdapterMovieBinding): RecyclerView.ViewHolder(binding.root){
        private val image: ShapeableImageView = binding.movieImage
        private val title: TextView = binding.movieTitle
        private val rating: TextView = binding.movieRating
        private val imageLoader = Glide.with(binding.root)

        fun bind(movie: Movie, listener: Listener){
            imageLoader.load(movie.getPoster()).override(200, 300).into(image)
            title.text = movie.title
            rating.text = movie.vote_average.toString()
            itemView.setOnClickListener { movie.id?.let { it1 -> listener.onSelectMovie(it1) } }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = AdapterMovieBinding.inflate(inflater, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item, listener)
        if (position == list.lastIndex) listener.loadMore()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}