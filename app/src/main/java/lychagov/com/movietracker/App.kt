package lychagov.com.movietracker

import android.app.Application
import android.arch.persistence.room.Room

class App : Application() {
    lateinit var database: FilmDatabase

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, FilmDatabase::class.java, "films").build()
    }
}