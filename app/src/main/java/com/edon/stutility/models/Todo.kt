package com.edon.stutility.models

//use done: 0 for false and 1 for true due to SQLite
data class Todo(
    var id: Int,
    var message: String,
    var priority: Int,
    var done: Int = 0
)
