package com.project.moviemania.activities.movie.presenter

import com.project.moviemania.activities.movie.contract.IMovieContract
import com.project.moviemania.service.manager.MovieApiManager
import com.project.moviemania.utility.director
import com.project.moviemania.utility.producer
import com.project.moviemania.utility.writer

class MoviePresenter(private val view: IMovieContract.View): IMovieContract.Presenter {

    private val movieApi by lazy { MovieApiManager.instance }

    override fun getMovieList(page: Int, genreIds: ArrayList<Int>?) {
        movieApi.getMovieList(page, genreIds){response, error ->
            if (error == null){
                val result = response?.results
                if (result?.isNullOrEmpty() == false) view.showMovieList(result)
                else view.showMessage(response?.status_message)
            } else view.showMessage(response?.status_message)
        }
    }

    override fun getMovieDetail(movieId: Int) {
        movieApi.getMovieDetail(movieId){response, error ->
            if (error == null){
                if (response != null) view.showMovieDetail(response)
                else view.showMessage(response?.status_message)
            } else view.showMessage(response?.status_message)
        }
    }

    override fun getMovieCredits(movieId: Int) {
        movieApi.getMovieCredits(movieId){response, error ->
            if (error == null){
                val cast = response?.cast
                val crew = response?.crew
                if (cast.isNullOrEmpty() || crew.isNullOrEmpty()) view.showMessage(response?.status_message)
                else view.showCredits(cast, crew.director(), crew.producer(), crew.writer())
            } else view.showMessage(null)
        }
    }

    override fun getGenres() {
        movieApi.getMovieGenre{response, error ->
            if (error == null){
                val result = response?.genres
                if (result.isNullOrEmpty()) view.showMessage(response?.status_message)
                else view.openGenreDialog(result)
            } else view.showMessage(null)
        }
    }
}