package com.project.moviemania.model

import com.project.moviemania.utility.StaticValues

data class CastResult(var id: String?, var cast: ArrayList<Cast>?, var crew: ArrayList<Cast>?, var status_message: String?)

data class Cast(var name: String?,
                var known_for_department: String?,
                var profile_path: String?,
                var job: String?){

    fun getProfilePath(): String{
        return StaticValues.BASE_IMAGE_URL+profile_path
    }
}