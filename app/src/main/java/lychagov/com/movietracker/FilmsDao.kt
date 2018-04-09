package lychagov.com.movietracker

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface FilmsDao {
    @Query("SELECT * FROM FilmInfo")
    fun getAll(): List<FilmInfo>

    @Insert
    fun insertAll(films: List<FilmInfo>)
}