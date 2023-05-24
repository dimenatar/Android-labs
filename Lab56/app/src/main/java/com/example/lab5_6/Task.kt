package com.example.lab5_6

import java.io.Serializable

data class Task (
    val name: String,
    var description: String,
    var difficulty: String,
    var urgency: String,
    var deadline: String,
    var photoPath: String
): Serializable