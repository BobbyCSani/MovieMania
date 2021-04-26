package com.project.moviemania.activities.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.recyclerview.widget.RecyclerView
import com.project.moviemania.databinding.AdapterGenreBinding
import com.project.moviemania.model.Genre

class GenreAdapter(private val list: ArrayList<Genre>, private val listener: Listener): RecyclerView.Adapter<GenreAdapter.ViewHolder>() {

    interface Listener {
        fun check(id: Int?, isAdd: Boolean)
    }

    class ViewHolder(binding: AdapterGenreBinding): RecyclerView.ViewHolder(binding.root){
        private val genreText: AppCompatCheckBox = binding.genreCheck

        fun bind(genre: Genre, listener: Listener){
            genreText.text = genre.name
            genreText.setOnClickListener {
                listener.check(genre.id, genreText.isChecked)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = AdapterGenreBinding.inflate(inflater, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item, listener)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}