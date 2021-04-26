package com.project.moviemania.model

import com.project.moviemania.utility.StaticValues

data class MovieResult(var page: Int?, var results: ArrayList<Movie>?, var status_message: String?)

data class Movie (var adult: Boolean?,
                  var backdrop_path: String?,
                  var genre_ids: ArrayList<Int>,
                  var id: Int?,
                  var original_language: String?,
                  var original_title: String?,
                  var overview: String?,
                  var poster_path: String?,
                  var release_date: String?,
                  var title: String?,
                  var vote_average: Float?,
                  var vote_count: Int){
    fun getPoster(): String {
        return StaticValues.BASE_IMAGE_URL+poster_path
    }
    fun getBackDrop(): String{
        return StaticValues.BASE_IMAGE_URL+backdrop_path
    }
}

data class MovieDetail(var adult: Boolean?,
                       var backdrop_path: String?,
                       var genres: ArrayList<Genre>,
                       var id: Int?,
                       var original_language: String?,
                       var original_title: String?,
                       var overview: String?,
                       var poster_path: String?,
                       var homepage: String?,
                       var production_companies: ArrayList<Company>,
                       var production_country: ArrayList<Country>?,
                       var release_date: String?,
                       var runtime: Int?,
                       var title: String?,
                       var status: String?,
                       var tagline: String?,
                       var vote_average: Float?,
                       var vote_count: Int,
                       var status_message: String?){

    fun getPoster(): String {
        return StaticValues.BASE_IMAGE_URL+poster_path
    }
    fun getBackDrop(): String{
        return StaticValues.BASE_IMAGE_URL+backdrop_path
    }
}

data class GenreResult(var genres: ArrayList<Genre>, var status_message: String?)

data class Genre(var id: Int?, var name: String?)

data class Language(var english_name: String?, var iso_639_1: String?)

data class Country(var iso_3166_1: String?, var name: String?)

data class Company(var id: String?, var logo_path: String?, var name: String?, var origin_country: String?)