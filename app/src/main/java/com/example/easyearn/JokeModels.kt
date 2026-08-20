package com.example.easyearn

data class Joke(
    val id: Int,
    val type: String,
    val setup: String,
    val delivery: String
)

data class JokeResponse(
    val error: Boolean,
    val joke: String? = null,
    val setup: String? = null,
    val delivery: String? = null
)
