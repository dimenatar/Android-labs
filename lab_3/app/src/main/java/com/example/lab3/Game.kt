package com.example.lab3

import java.io.Serializable

class Game (var name: String, var type: String, var author: String, var price: Int) : Serializable{

    var isMultiplayer = false
    var producer = ""
    var email = ""
    var telephone = ""
    var link = ""

    fun getGameData() : String
    {
        return "name: ${name}, type: $type, author: $author, price: $price, isMultiplayer: $isMultiplayer, producer: $producer"
    }
}