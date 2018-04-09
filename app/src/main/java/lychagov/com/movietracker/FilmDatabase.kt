package lychagov.com.movietracker

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [FilmInfo::class], version = 1)
abstract class FilmDatabase : RoomDatabase() {
    abstract fun filmsDao(): FilmsDao
}