package com.project.moviemania.utility

import com.project.moviemania.model.Cast
import com.project.moviemania.model.Genre

fun ArrayList<Cast>.producer() : String {
    val list = arrayListOf<String>()
    forEach {
        if (it.job == "Producer") it.name?.let { it1 -> list.add(it1) }
    }
    return list.joinToString()
}

fun ArrayList<Cast>.writer() : String {
    val list = arrayListOf<String>()
    forEach {
        if (it.job == "Story") it.name?.let { it1 -> list.add(it1) }
    }
    return list.joinToString()
}

fun ArrayList<Cast>.director() : String {
    val list = arrayListOf<String>()
    forEach {
        if (it.job == "Director") it.name?.let { it1 -> list.add(it1) }
    }
    return list.joinToString()
}

fun ArrayList<Genre>.name() : String {
    val list = arrayListOf<String>()
    forEach {
        it.name?.let { it1 -> list.add(it1) }
    }
    return list.joinToString()
}