package lychagov.com.movietracker

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.MenuItem
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.newSingleThreadContext

class FilmActivity : AppCompatActivity() {
    private var selectedFilm: Int = 0
    private var user_mark: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedFilm = intent.getIntExtra("film_id",0)
        user_mark = intent.getIntExtra("user_mark", 0)
        title = "Film Details"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        launch(UI){
            val film_context = newSingleThreadContext("film_context")
            val filmDetails = loadFilmDetails(selectedFilm,film_context).await()
            FilmActivityUI(filmDetails, user_mark).setContentView(this@FilmActivity)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

class FilmActivityUI(
        filmDetails: FilmDetails,
        user_mark: Int
): AnkoComponent<FilmActivity> {
    val film = filmDetails
    val mark = user_mark
    override fun createView(ui: AnkoContext<FilmActivity>) = with(ui) {
        scrollView {
            verticalLayout {
                textView {
                    text = film.title
                    textSize = dip(10).toFloat()
                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    typeface = Typeface.DEFAULT_BOLD
                }
                textView {
                    text = film.original_title
                    textSize = dip(6).toFloat()
                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    typeface = Typeface.defaultFromStyle(Typeface.ITALIC)
                }
                textView {
                    text = film.release_date?.substring(0, 4) ?: ""
                    textSize = dip(8).toFloat()
                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    typeface = Typeface.DEFAULT_BOLD
                }
                linearLayout{
                    this.gravity = Gravity.CENTER
                    if (mark > 0) {
                        for (i in 1..mark) {
                            imageView {
                                imageResource = R.drawable.ic_star_border_black_24dp
                            }.lparams {
                                //gravity = Gravity.CENTER_HORIZONTAL
                            }
                        }
                    } else {
                        textView {
                            text = "Not yet watched"
                            textSize = dip(8).toFloat()
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            typeface = Typeface.DEFAULT_BOLD
                        }
                    }
                }

                val img = imageView {
                    setPadding(dip(50), 10, dip(50), 10)
                }
                Glide.with(this)
                        .load("https://image.tmdb.org/t/p/w500${film.poster_path}")
                        .into(img)
                val overview = textView {
                    setPadding(0, dip(10), 0, 0)
                    text = film.overview
                    textSize = dip(6).toFloat()
                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    typeface = Typeface.DEFAULT
                }

            }.lparams {
                setPadding(dip(10), dip(10), dip(10), 0)
            }
        }
    }
}


