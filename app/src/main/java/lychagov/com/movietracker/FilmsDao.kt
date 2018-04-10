package lychagov.com.movietracker

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE

@Dao
interface FilmsDao {
    @Query("SELECT * FROM FilmInfo")
    fun getAll(): List<FilmInfo>

    @Insert
    fun insertAll(films: List<FilmInfo>)

    @Insert
    fun insertFilm(film: FilmInfo)

    @Delete
    fun deleteFilm(film: FilmInfo)

    @Update(onConflict = REPLACE)
    fun update(film: FilmInfo)
}