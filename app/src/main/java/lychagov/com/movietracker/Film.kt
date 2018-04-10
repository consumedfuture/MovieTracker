package lychagov.com.movietracker

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


/**
 * Created by LychagovAN on 19.03.2018.
 */
@Entity
data class FilmInfo(
        @PrimaryKey
        val id: Int,
        val title : String,
        val original_title: String,
        val poster_path : String,
        val release_date: String? = null,
        var user_mark: Int = 0
)


data class FilmDetails(
        val adult: Boolean,
        val title: String,
        val original_title: String,
        val overview: String,
        val poster_path: String,
        val release_date: String? = null,
        val runtime: Int,
        val tagline: String
)

class Films : ArrayList<FilmInfo>()