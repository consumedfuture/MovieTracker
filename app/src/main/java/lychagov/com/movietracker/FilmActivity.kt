package lychagov.com.movietracker

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.newSingleThreadContext

class FilmActivity : AppCompatActivity() {
    private var selectedFilm: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedFilm = intent.getIntExtra("film_id",0)
        launch(UI){
            val film_context = newSingleThreadContext("film_context")
            val filmDetails = loadFilmDetails(selectedFilm,film_context).await()
            FilmActivityUI(filmDetails).setContentView(this@FilmActivity)
        }

        /*val filmPicture = ImageView(this)



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
            //Picasso.get().load("https://image.tmdb.org/t/p/w500${selectedFilm?.poster_path}").into(im)

        }*/
    }
}

class FilmActivityUI(
        filmDetails: FilmDetails
): AnkoComponent<FilmActivity> {
    val film = filmDetails
    override fun createView(ui: AnkoContext<FilmActivity>) = with(ui) {
        scrollView {
            verticalLayout {
                textView {
                    text = film.title
                    textSize = dip(14).toFloat()
                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    typeface = Typeface.DEFAULT_BOLD
                }
                textView {
                    text = film.original_title
                    textSize = dip(8).toFloat()
                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    typeface = Typeface.defaultFromStyle(Typeface.ITALIC)
                }
                textView {
                    text = film.release_date?.substring(0, 4) ?: ""
                    textSize = dip(10).toFloat()
                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    typeface = Typeface.DEFAULT_BOLD
                }
                val img = imageView {
                    setPadding(dip(50), 0, dip(50), 0)
                }
                Glide.with(this)
                        .load("https://image.tmdb.org/t/p/w500${film.poster_path}")
                        .into(img)
                val overview = textView {
                    text = film.overview
                    textSize = dip(8).toFloat()
                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    typeface = Typeface.DEFAULT
                }
                /*button("Say Hello") {
               onClick { ctx.toast("Hello, ${name.text}!") }
           }*/
            }.lparams{
                padding = dip(20)
            }
        }
    }
}
