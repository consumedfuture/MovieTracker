package lychagov.com.movietracker

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.customView

class FilmActivity : AppCompatActivity() {
    private var selectedFilm: TMDBFilmInfo? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        selectedFilm = intent.getSerializableExtra(FILM_KEY) as TMDBFilmInfo

        toast("${selectedFilm?.title}")
        Log.v("film", selectedFilm?.poster_path)
        relativeLayout {
            background = ColorDrawable(Color.parseColor("#F8F2F2"))
            val text = textView {
                text = "${selectedFilm?.title}"
                typeface = Typeface.DEFAULT_BOLD
                textSize = 22f
                textColor = Color.BLACK
                textAlignment = View.TEXT_ALIGNMENT_GRAVITY
                gravity = Gravity.TOP
            }.lparams {
                width = matchParent
                height = matchParent
                bottomPadding = dip(10)
                topPadding = dip(30)
                leftPadding = dip(20)
            }

            val im = imageView {
            }.lparams {
                //below(text)
            }
            Picasso.get().load("https://image.tmdb.org/t/p/w500${selectedFilm?.poster_path}").into(im)

        }
    }
    companion object {
        private val FILM_KEY = "film"
    }
}