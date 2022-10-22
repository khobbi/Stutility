package com.edon.stutility.models

data class Schedule(
    var scheduleId: Int = 0,
    var classroom: String = "SWLT 55",
    var lecturer: String = "Lecture's name",
    var timeStart: String = "00:00",
    var timeEnd: String = "01:59",
    var dayOfSchedule: String = "monday",
    var type: String = "",
    var courseId: Int
)
