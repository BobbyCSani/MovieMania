package com.project.moviemania.activities.movie.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.moviemania.R
import com.project.moviemania.databinding.AdapterCastBinding
import com.project.moviemania.model.Cast

class CastAdapter(private val list: ArrayList<Cast>): RecyclerView.Adapter<CastAdapter.ViewHolder>() {

    class ViewHolder(binding: AdapterCastBinding): RecyclerView.ViewHolder(binding.root){
        private val image: ImageView = binding.castImage
        private val name: TextView = binding.castName
        private val imageLoader = Glide.with(binding.root)

        fun bind(cast: Cast){
            imageLoader.load(cast.getProfilePath()).error(ResourcesCompat.getDrawable(itemView.context.resources, R.drawable.ic_user, itemView.context.theme)).circleCrop().override(200,200).into(image)
            name.text = cast.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = AdapterCastBinding.inflate(inflater, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}