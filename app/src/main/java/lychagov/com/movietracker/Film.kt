package lychagov.com.movietracker


/**
 * Created by LychagovAN on 19.03.2018.
 */

data class FilmInfo(
        val title : String,
        val poster_path : String
)

class Films : ArrayList<FilmInfo>()