package lychagov.com.movietracker


/**
 * Created by LychagovAN on 19.03.2018.
 */

data class FilmInfo(
        val id: Int,
        val title : String,
        val poster_path : String
)

data class FilmDetails(
        val adult: Boolean,
        val title: String,
        val overview: String,
        val poster_path: String,
        val release_date: String,
        val runtime: Int,
        val tagline: String
)

class Films : ArrayList<FilmInfo>()