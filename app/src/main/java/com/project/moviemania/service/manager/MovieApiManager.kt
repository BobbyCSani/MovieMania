package com.project.moviemania.service.manager

import com.google.gson.Gson
import com.project.moviemania.model.CastResult
import com.project.moviemania.model.GenreResult
import com.project.moviemania.model.MovieDetail
import com.project.moviemania.model.MovieResult
import com.project.moviemania.service.api.IMovieApi
import com.project.moviemania.utility.StaticValues
import io.reactivex.schedulers.Schedulers

class MovieApiManager {

    companion object {
        val instance by lazy { MovieApiManager() }
        private val movieApi by lazy { BaseApiManager.buildService(IMovieApi::class.java) }
    }

    fun getMovieGenre(onFinished: (response: GenreResult?, error: Throwable?) -> Unit){
        movieApi.getMovieGenre(StaticValues.API_KEY)
            .subscribeOn(Schedulers.io())
            .subscribe({
                when (it.isSuccessful) {
                    true -> onFinished(it.body(), null)
                    false -> {
                        val error = it.errorBody()
                        if (error != null) {
                            val response = Gson().fromJson(
                                error.charStream(),
                                GenreResult::class.java
                            )
                            onFinished(response, null)
                        }
                    }
                }
            },{
                onFinished(null, it)
            })
    }

    fun getMovieCredits(movieId: Int, onFinished: (response: CastResult?, error: Throwable?) -> Unit){
        movieApi.getMovieCredits(movieId, StaticValues.API_KEY)
            .subscribeOn(Schedulers.io())
            .subscribe({
                when (it.isSuccessful) {
                    true -> onFinished(it.body(), null)
                    false -> {
                        val error = it.errorBody()
                        if (error != null) {
                            val response = Gson().fromJson(
                                error.charStream(),
                                CastResult::class.java
                            )
                            onFinished(response, null)
                        }
                    }
                }
            },{
                onFinished(null, it)
            })
    }

    fun getMovieDetail(movieId: Int, onFinished: (response: MovieDetail?, error: Throwable?) -> Unit){
        movieApi.getMovieDetail(movieId, StaticValues.API_KEY)
            .subscribeOn(Schedulers.io())
            .subscribe({
                when (it.isSuccessful) {
                    true -> onFinished(it.body(), null)
                    false -> {
                        val error = it.errorBody()
                        if (error != null) {
                            val response = Gson().fromJson(
                                error.charStream(),
                                MovieDetail::class.java
                            )
                            onFinished(response, null)
                        }
                    }
                }
            },{
                onFinished(null, it)
            })
    }

    fun getMovieList(page: Int, genreIds: ArrayList<Int>?, onFinished: (response: MovieResult?, error: Throwable?) -> Unit){
        val params = mutableMapOf("api_key" to StaticValues.API_KEY)
        params["page"] = page.toString()
        genreIds?.let { params["with_genres"] = it.joinToString() }
        movieApi.getMovieList(params)
            .subscribeOn(Schedulers.io())
            .subscribe({
                when (it.isSuccessful) {
                    true -> onFinished(it.body(), null)
                    false -> {
                        val error = it.errorBody()
                        if (error != null) {
                            val response = Gson().fromJson(
                                error.charStream(),
                                MovieResult::class.java
                            )
                            onFinished(response, null)
                        }
                    }
                }
            },{
                onFinished(null, it)
            })
    }
}