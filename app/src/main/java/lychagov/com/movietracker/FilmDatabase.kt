package lychagov.com.movietracker

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.migration.Migration

@Database(entities = [FilmInfo::class], version = 2)
abstract class FilmDatabase : RoomDatabase() {
    abstract fun filmsDao(): FilmsDao

}
