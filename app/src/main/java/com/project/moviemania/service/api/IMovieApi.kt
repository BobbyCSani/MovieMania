package com.project.moviemania.service.api

import com.project.moviemania.model.CastResult
import com.project.moviemania.model.GenreResult
import com.project.moviemania.model.MovieDetail
import com.project.moviemania.model.MovieResult
import com.project.moviemania.utility.StaticValues
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface IMovieApi {

    @GET("genre/movie/list")
    @Headers("Authorization: ${StaticValues.AUTHORIZATION}")
    fun getMovieGenre(@Query("api_key") key: String): Observable<Response<GenreResult?>>

    @GET("movie/{movie_id}/credits")
    @Headers("Authorization: ${StaticValues.AUTHORIZATION}")
    fun getMovieCredits(@Path("movie_id") movieId: Int,
                        @Query("api_key") key: String): Observable<Response<CastResult?>>

    @GET("discover/movie")
    @Headers("Authorization: ${StaticValues.AUTHORIZATION}")
    fun getMovieList(@QueryMap params: MutableMap<String, String>): Observable<Response<MovieResult?>>

    @GET("movie/{movie_id}")
    @Headers("Authorization: ${StaticValues.AUTHORIZATION}")
    fun getMovieDetail(@Path("movie_id") movieId: Int,
                       @Query("api_key") key: String): Observable<Response<MovieDetail?>>

}