package com.project.moviemania.activities.movie.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.project.moviemania.R
import com.project.moviemania.activities.movie.adapter.MovieAdapter
import com.project.moviemania.activities.movie.contract.IMovieContract
import com.project.moviemania.activities.movie.dialog.GenreDialog
import com.project.moviemania.activities.movie.presenter.MoviePresenter
import com.project.moviemania.databinding.ActivityMainBinding
import com.project.moviemania.model.Genre
import com.project.moviemania.model.Movie
import com.project.moviemania.utility.StaticValues
import com.project.moviemania.utility.visible

class MainActivity : AppCompatActivity(), IMovieContract.View, MovieAdapter.Listener, GenreDialog.Listener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: IMovieContract.Presenter
    private lateinit var adapter: MovieAdapter

    private val listMovie = arrayListOf<Movie>()
    private var listGenre = arrayListOf<Genre>()
    private var page = 1
    private var genresId = arrayListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = MoviePresenter(this)
        adapter = MovieAdapter(listMovie, this)

        binding.movieRecycler.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.movieRecycler.adapter = adapter
        binding.movieRefresh.isRefreshing = true
        presenter.getMovieList(page, genresId)

        binding.movieGenreSort.setOnClickListener {
            if (listGenre.isNotEmpty()){
                GenreDialog(listGenre).show(supportFragmentManager, "")
            } else {
                binding.movieRefresh.isRefreshing = true
                presenter.getGenres()
            }
        }

        binding.movieRefresh.setOnRefreshListener {
            presenter.getMovieList(page, genresId)
        }
    }

    override fun showMessage(msg: String?) {
        runOnUiThread {
            binding.movieRefresh.isRefreshing = false
            if (page == 1) {
                AlertDialog.Builder(this).apply {
                    setTitle("Sorry")
                    setMessage(msg ?: getString(R.string.failed_fetch_data))
                    setPositiveButton(R.string.ok) { dialog, _ ->
                        dialog.dismiss()
                    }
                    create().show()
                }
            } else page--
        }
    }

    override fun openGenreDialog(list: ArrayList<Genre>) {
        super.openGenreDialog(list)
        runOnUiThread {
            listGenre = list
            binding.movieRefresh.isRefreshing = false
            GenreDialog(listGenre).show(supportFragmentManager, "")
        }
    }

    override fun showMovieList(list: ArrayList<Movie>) {
        super.showMovieList(list)
        runOnUiThread {
            binding.movieRefresh.isRefreshing = false
            if (page == 1) listMovie.clear()
            listMovie.addAll(list)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onSelectMovie(movieId: Int) {
        Intent(this, MovieDetailActivity::class.java).apply {
            putExtra(StaticValues.MOVIE_ID, movieId)
            startActivity(this)
        }
    }

    override fun loadMore() {
        page+=1
        binding.movieRefresh.isRefreshing = true
        presenter.getMovieList(page, genresId)
    }

    override fun selectGenre(list: ArrayList<Int>) {
        genresId = list
        page = 1
        binding.movieRefresh.isRefreshing = true
        presenter.getMovieList(page, genresId)
    }
}