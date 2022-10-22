package com.edon.stutility.models

//model class for each Note
data class Note(
    var id: Int,
    var title: String,
    var description: String,
    var lastUpdate: String = "Last update: dd/mm/yyyy 00:00:00",
    var imgDelete: Int = 0
)
