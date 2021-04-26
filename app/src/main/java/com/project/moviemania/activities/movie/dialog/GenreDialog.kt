package com.project.moviemania.activities.movie.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.moviemania.activities.movie.adapter.GenreAdapter
import com.project.moviemania.databinding.DialogGenreBinding
import com.project.moviemania.model.Genre
import java.lang.Exception

class GenreDialog(private val listGenre: ArrayList<Genre>, private val listSelectedGenre: ArrayList<Int>): DialogFragment(), GenreAdapter.Listener {

    private lateinit var adapter: GenreAdapter
    private lateinit var listener: Listener
    private var _binding: DialogGenreBinding? = null
    private val binding get() = _binding!!

    interface Listener {
        fun selectGenre(list: ArrayList<Int>)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null){
            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as Listener
        }catch (e: Exception){
            dismiss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        adapter = GenreAdapter(listGenre, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = DialogGenreBinding.inflate(inflater, container, false)

        binding.genreRecycler.layoutManager = LinearLayoutManager(activity)
        binding.genreRecycler.adapter = adapter

        binding.genreBack.setOnClickListener { dismiss() }
        binding.genreSort.setOnClickListener {
            listener.selectGenre(listSelectedGenre)
        }

        return binding.root
    }

    override fun check(id: Int?, isAdd: Boolean) {
        if (isAdd) {
            if (!listSelectedGenre.contains(id)) id?.let { listSelectedGenre.add(it) }
        } else {
            if (!listSelectedGenre.contains(id)) id?.let { listSelectedGenre.remove(id) }
        }
    }
}