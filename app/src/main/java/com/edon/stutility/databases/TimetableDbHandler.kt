package com.edon.stutility.databases

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.edon.stutility.models.Schedule
import com.edon.stutility.models.Course

class TimetableDbHandler private constructor(context: Context):
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    //companion object containing static variables
    companion object{
        //static instance of this
        private var instance: TimetableDbHandler? = null
        fun getInstance(context: Context): TimetableDbHandler{
            if(instance == null){
                instance = TimetableDbHandler(context)
            }
            return instance!!
        }

        private const val DB_NAME = "timetable_db"
        private const val DB_VERSION = 5

        private const val TABLE_COURSES = "courses"
        private const val CS_COL_ID = "_id"
        private const val CS_COL_CS_NAME = "course_name"

        private const val TABLE_TIMETABLE = "timetable"
        private const val TTB_COL_ID = "_id"
        private const val TTB_COL_CLASS = "classroom"
        private const val TTB_COL_LECTURER = "lecturer"
        private const val TTB_COL_TIME_START = "time_start"
        private const val TTB_COL_TIME_END = "time_end"
        private const val TTB_COL_DAY = "lecture_day"
        private const val TTB_COL_TYPE = "sch_type"
        private const val TTB_COL_COURSEID = "course_id"
    }

    //implement methods
    override fun onCreate(db: SQLiteDatabase?) {
        //create courses table
        val CREATE_TABLE_COURSES = "CREATE TABLE " + TABLE_COURSES + "(" +
                CS_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CS_COL_CS_NAME + " TEXT" + ");"
        db?.execSQL(CREATE_TABLE_COURSES)

        //create timetable table
        val CREATE_TABLE_TIMETABLE = "CREATE TABLE " + TABLE_TIMETABLE + "("+
                TTB_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TTB_COL_CLASS + " TEXT," +
                TTB_COL_LECTURER + " TEXT," +
                TTB_COL_TIME_START + " TEXT," +
                TTB_COL_TIME_END + " TEXT," +
                TTB_COL_DAY + " TEXT," +
                TTB_COL_TYPE + " TEXT," +
                TTB_COL_COURSEID + " INTEGER, " +
                "FOREIGN KEY (" + TTB_COL_COURSEID + ") " +
                "REFERENCES " + TABLE_COURSES + " (" + CS_COL_ID + ") " +
                "ON UPDATE CASCADE ON DELETE CASCADE" + ");"
        db?.execSQL(CREATE_TABLE_TIMETABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES)
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMETABLE)

        onCreate(db)
    }

    //to allow the constraint to work
    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        if(!db!!.isReadOnly){
            //enable foreign key constraint
            db.execSQL("PRAGMA foreign_keys=ON;")
        }
    }

    //call this if user adds new course
    fun populateTableCourses(course: Course){
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(CS_COL_CS_NAME, course.courseName)

        db.insert(TABLE_COURSES, null, values)
        db.close()
    }

    //edit / update course Name
    fun updateCourseName(course: Course){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CS_COL_CS_NAME, course.courseName)

        db.update(TABLE_COURSES, values, CS_COL_ID + "=" + course.id, null)
        db.close()
    }

    //for deleting course name
    fun deleteCourseName(course: Course): Int{
        val db = this.writableDatabase
        val value = ContentValues()
        value.put(CS_COL_ID, course.id)
        val success = db.delete(TABLE_COURSES, CS_COL_ID + "=" + course.id, null)
        db.close()
        return success
    }

    //view all Courses names only
    fun viewAllCourseNames(): ArrayList<Course>{
        val courseNames: ArrayList<Course> = ArrayList() // to contain the days

        val selectQuery = "SELECT * FROM $TABLE_COURSES" //sqlite query

        val db = this.readableDatabase
        var cursor: Cursor? = null

        //execute the sql
        try{
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException){
            db.execSQL(selectQuery)
            return ArrayList()
        }

        //step through cursor, adding each record(note) to array list
        if(cursor.moveToFirst()){
            do{
                val courseId = cursor.getInt(cursor.getColumnIndex(CS_COL_ID))
                val courseName = cursor.getString(cursor.getColumnIndex(CS_COL_CS_NAME))

                courseNames.add(Course(courseId, courseName))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return courseNames
    }

    //view all timetable
    fun viewAllCoursesForADay(day: String): ArrayList<Schedule>{
        val courses: ArrayList<Schedule> = ArrayList() // to contain the days

        val selectQuery = "SELECT * FROM $TABLE_TIMETABLE WHERE $TTB_COL_DAY " + "=?" + " ORDER BY $TTB_COL_TIME_START" //sqlite query

        val db = this.readableDatabase
        var cursor: Cursor? = null

        //execute the sql
        try{
            cursor = db.rawQuery(selectQuery, arrayOf(day)) // the array will replace the ? in the sql string
        } catch (e: SQLiteException){
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var scheduleId: Int; var classroom: String; var lecturer: String
        var timeStart: String; var timeEnd: String; var day_: String; var type: String; var courseId: Int
        //step through cursor, adding each record(note) to array list
        if(cursor.moveToFirst()){
            do{
                scheduleId = cursor.getInt(cursor.getColumnIndex(TTB_COL_ID))
                classroom = cursor.getString(cursor.getColumnIndex(TTB_COL_CLASS))
                lecturer = cursor.getString(cursor.getColumnIndex(TTB_COL_LECTURER))
                timeStart = cursor.getString(cursor.getColumnIndex(TTB_COL_TIME_START))
                timeEnd = cursor.getString(cursor.getColumnIndex(TTB_COL_TIME_END))
                day_ = cursor.getString(cursor.getColumnIndex(TTB_COL_DAY))
                type = cursor.getString(cursor.getColumnIndex(TTB_COL_TYPE))
                courseId = cursor.getInt(cursor.getColumnIndex(TTB_COL_COURSEID))

                courses.add(Schedule(scheduleId, classroom, lecturer, timeStart, timeEnd, day_, type, courseId))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return courses
    }

    //inserting into the timetable table
    fun insertIntoTimetable(schedule: Schedule): Long{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TTB_COL_CLASS, schedule.classroom)
        values.put(TTB_COL_LECTURER, schedule.lecturer)
        values.put(TTB_COL_TIME_START, schedule.timeStart)
        values.put(TTB_COL_TIME_END, schedule.timeEnd)
        values.put(TTB_COL_DAY, schedule.dayOfSchedule)
        values.put(TTB_COL_TYPE, schedule.type)
        values.put(TTB_COL_COURSEID, schedule.courseId)

        val success = db.insert(TABLE_TIMETABLE, null, values)
        db.close()
        return success;
    }

    //update course /schedule in timetable table
    fun updateTimetableCourse(schedule: Schedule): Int{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TTB_COL_CLASS, schedule.classroom)
        values.put(TTB_COL_LECTURER, schedule.lecturer)
        values.put(TTB_COL_TIME_START, schedule.timeStart)
        values.put(TTB_COL_TIME_END, schedule.timeEnd)
        values.put(TTB_COL_DAY, schedule.dayOfSchedule)
        values.put(TTB_COL_TYPE, schedule.type)
        values.put(TTB_COL_COURSEID, schedule.courseId)

        val success = db.update(TABLE_TIMETABLE, values, TTB_COL_ID + "=" + schedule.scheduleId, null)
        db.close()
        return success
    }

    //delete timetable course
    fun deleteScheduleFromTimetable(schedule: Schedule): Int{
        val db = this.writableDatabase
        val value = ContentValues()
        value.put(TTB_COL_ID, schedule.scheduleId)

        val success = db.delete(TABLE_TIMETABLE, TTB_COL_ID + "=" + schedule.scheduleId, null)
        db.close()
        return success
    }

    //truncate tables: delete statement without where claus
    fun truncateCourseNames(){
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_COURSES;")
        db.close()
    }

    fun truncateTimetable(){
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_TIMETABLE;")
        db.close()
    }

    fun getCourseName(id: Int): String{
        val db = this.readableDatabase
        val selectQuerry = "SELECT " + CS_COL_CS_NAME + " FROM " + TABLE_COURSES + " WHERE " + CS_COL_ID + "=?"
        var cursor: Cursor? = null

        try{
            cursor = db.rawQuery(selectQuerry, arrayOf(id.toString()))
        } catch (e: SQLiteException){
            db.execSQL(selectQuerry)
            return String()
        }

        var courseName: String = ""
        if(cursor.moveToFirst()){
            do{
                courseName = cursor.getString(cursor.getColumnIndex(CS_COL_CS_NAME))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return courseName
    }
}