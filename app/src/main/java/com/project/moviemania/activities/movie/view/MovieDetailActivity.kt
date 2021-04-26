package com.project.moviemania.activities.movie.view

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.project.moviemania.R
import com.project.moviemania.activities.movie.adapter.CastAdapter
import com.project.moviemania.activities.movie.contract.IMovieContract
import com.project.moviemania.activities.movie.presenter.MoviePresenter
import com.project.moviemania.databinding.ActivityMovieDetailBinding
import com.project.moviemania.model.Cast
import com.project.moviemania.model.MovieDetail
import com.project.moviemania.utility.StaticValues
import com.project.moviemania.utility.hide
import com.project.moviemania.utility.name
import com.project.moviemania.utility.visible

class MovieDetailActivity: AppCompatActivity(), IMovieContract.View, AppBarLayout.OnOffsetChangedListener {

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var presenter: IMovieContract.Presenter
    private lateinit var castAdapter: CastAdapter

    private val listCast = arrayListOf<Cast>()
    private var movieId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        castAdapter = CastAdapter(listCast)
        presenter = MoviePresenter(this)

        binding.movieDetailCast.layoutManager = GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false)
        binding.movieDetailCast.adapter = castAdapter

        binding.movieDetailAppBar.addOnOffsetChangedListener(this)
        binding.movieDetailRefresh.isRefreshing = true
        movieId = intent.getIntExtra(StaticValues.MOVIE_ID, 0)
        presenter.getMovieDetail(movieId)
        presenter.getMovieCredits(movieId)
        binding.movieDetailBack.setOnClickListener { onBackPressed() }
        binding.movieDetailRefresh.setOnRefreshListener {
            presenter.getMovieDetail(movieId)
            presenter.getMovieCredits(movieId)
        }

        binding.movieDetailTopRefresh.setOnRefreshListener {
            presenter.getMovieDetail(movieId)
            presenter.getMovieCredits(movieId)
        }
    }

    override fun showMovieDetail(movieDetail: MovieDetail) {
        super.showMovieDetail(movieDetail)
        runOnUiThread {
            binding.movieDetailRefresh.isRefreshing = false
            binding.movieDetailTitle.text = movieDetail.title
            binding.movieTitle.text = movieDetail.title
            binding.movieRating.text = movieDetail.vote_average.toString()
            binding.movieDetailReleaseStatus.text = movieDetail.status
            binding.movieDetailReleaseDate.text = movieDetail.release_date
            Glide.with(this).load(movieDetail.getPoster()).override(200,300).into(binding.movieDetailImage)
            Glide.with(this).load(movieDetail.getBackDrop()).into(binding.movieDetailCover)
            binding.movieDetailDescription.text = movieDetail.overview
            binding.movieDetailWebsite.text = movieDetail.homepage
            binding.movieDetailGenre.text = movieDetail.genres.name()
            binding.movieDetailRuntime.text = movieDetail.runtime.toString() +" Minutes"
        }
    }

    override fun showMessage(msg: String?) {
        runOnUiThread {
            binding.movieDetailRefresh.isRefreshing = false
            binding.movieDetailTopRefresh.isRefreshing = false
            AlertDialog.Builder(this).apply {
                setTitle("Sorry")
                setMessage(msg ?: getString(R.string.failed_fetch_data))
                setPositiveButton(R.string.ok) { dialog, _ ->
                    dialog.dismiss()
                }
                create().show()
            }
        }
    }

    override fun showCredits(
        listCast: ArrayList<Cast>,
        director: String?,
        producer: String?,
        writer: String?
    ) {
        super.showCredits(listCast, director, producer, writer)
        runOnUiThread {
            binding.movieDetailRefresh.isRefreshing = false
            binding.movieDetailTopRefresh.isRefreshing = false
            binding.movieDetailDirector.text = director
            binding.movieDetailProducer.text = producer
            binding.movieDetailWriter.text = writer
            this.listCast.clear()
            this.listCast.addAll(listCast)
            castAdapter.notifyDataSetChanged()
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        binding.movieDetailTopRefresh.isEnabled = verticalOffset == 0
        if (verticalOffset < -250) {
            binding.movieDetailToolbar.background = ResourcesCompat.getDrawable(resources, R.color.white, theme)
            binding.movieDetailTitle.visible()
        }
        else {
            binding.movieDetailToolbar.background = ResourcesCompat.getDrawable(resources, R.color.transparent, theme)
            binding.movieDetailTitle.hide()
        }
    }
}