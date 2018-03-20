package lychagov.com.movietracker

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.net.URLEncoder

class FilmActivity : AppCompatActivity() {
    private var selectedFilm: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedFilm = intent.getIntExtra("film_id",0)
        Thread({
            val filmDetails = loadFilmDetails(selectedFilm)
            runOnUiThread {
                val film = filmDetails
                FilmActivityUI(film).setContentView(this)
            }
        }).start()

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
        verticalLayout {
            val name = textView().apply {
                text = film.title
                textSize = 80f
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                typeface = Typeface.DEFAULT_BOLD
            }
            val img = imageView().apply {
                setPadding(dip(20),0,dip(20),0)
            }
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500${film.poster_path}")
                    .into(img)

            /*button("Say Hello") {
                onClick { ctx.toast("Hello, ${name.text}!") }
            }*/
        }
    }
}
