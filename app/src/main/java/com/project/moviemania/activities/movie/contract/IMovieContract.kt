package com.project.moviemania.activities.movie.contract

import com.project.moviemania.model.Cast
import com.project.moviemania.model.Genre
import com.project.moviemania.model.Movie
import com.project.moviemania.model.MovieDetail

class IMovieContract {

    interface Presenter {
        fun getMovieList(page: Int, genreIds: ArrayList<Int>?)
        fun getMovieDetail(movieId: Int)
        fun getMovieCredits(movieId: Int)
        fun getGenres()
    }

    interface View {
        fun showMovieList(list: ArrayList<Movie>){}
        fun showMovieDetail(movieDetail: MovieDetail){}
        fun showCredits(listCast: ArrayList<Cast>, director: String?, producer: String?, writer: String?){}
        fun showMessage(msg: String?)
        fun openGenreDialog(list: ArrayList<Genre>){}
    }
}