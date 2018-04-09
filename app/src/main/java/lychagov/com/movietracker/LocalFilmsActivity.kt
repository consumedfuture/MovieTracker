package lychagov.com.movietracker

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import com.bumptech.glide.Glide
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI
import org.jetbrains.anko.*
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.design.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onFocusChange
import org.jetbrains.anko.sdk25.coroutines.onItemLongClick
import android.graphics.drawable.VectorDrawable

class LocalFilmsActivity : AppCompatActivity(){
    val PICK_FILM = 100
    var filmList = Films()
    var adapter=LocalFilmsAdapter(filmList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.v("action: ", "activity local created")
        Log.v("action: ", filmList.toString())

        title = "Сохраненные фильмы"
        LocalFilmsActivityUI(adapter).setContentView(this)


        launch(UI){
            val films = loadLocalFilms(application as App).await()
            if (films.isNotEmpty()){
                filmList.addAll(films)
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onResume() {
        super.onResume()
//        Log.v("action", "activity resumed")
//        Log.v("action: ", filmList.toString())
//        adapter.notifyDataSetChanged()

    }

    override fun onStart() {
        super.onStart()
        //Log.v("action", "started")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FILM && resultCode == Activity.RESULT_OK) {
            if (data == null) return
            val film = data.getIntExtra("film_id", 0)
            //filmList.add(film)
            launch(UI) {
                val filmInfo = loadFilmInfo(film).await()
                saveFilms(application as App, adapter.getAll())
                adapter.add(filmInfo)
                //toast("film ${film} added")
            }

        }
    }
}

class LocalFilmsAdapter(
        private var filmList: Films
): BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return with(parent!!.context){
            linearLayout {
                lparams(width = matchParent, height = dip(100))
                //padding = dip(10)

                val img = imageView {
                    setPadding(dip(5), 0, dip(5), 0)
                }.lparams {
                    width=(parent.width * 0.20).toInt()
                    gravity = Gravity.CENTER
                }
                Glide.with(this)
                        .load("https://image.tmdb.org/t/p/w500${filmList[position].poster_path}")
                        .into(img)


                textView {
                    text = filmList[position].title + " (${filmList[position].release_date?.substring(0,4)})"
                    textSize = 20f
                    typeface = Typeface.DEFAULT
                    padding = dip(5)
                    //gravity = Gravity.CENTER_VERTICAL and Gravity.START
                }.lparams {
                    gravity = Gravity.CENTER_VERTICAL
                    width = (parent.width * 0.60).toInt()
                }

                val status = imageView {
                    if (filmList[position].user_mark > 0)
                        imageResource = R.drawable.ic_done_black
                    else
                        imageResource = R.drawable.ic_playlist_play_black

                }.lparams {
                    width = (parent.width * 0.20).toInt()
                    gravity = Gravity.CENTER
                }

//                Glide.with(this)
//                        .load(openFileInput("@drawable/"))

            }
        }
    }

    override fun getCount(): Int {
        return filmList.size
    }

    override fun getItem(position: Int): FilmInfo {
        return filmList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0L
    }

    fun add(film: FilmInfo){
        filmList.add(film)
        notifyDataSetChanged()
    }

    fun swapMark(position: Int){
        if (filmList[position].user_mark == 0)
            filmList[position].user_mark = 1
        else
            filmList[position].user_mark = 0
        notifyDataSetChanged()
    }

    fun getAll() : Films {
        return filmList
    }
    fun remove(i: Int){
        filmList.removeAt(i)
        notifyDataSetChanged()
    }

}

class LocalFilmsActivityUI(
        val filmAdapter: LocalFilmsAdapter
): AnkoComponent<LocalFilmsActivity> {
    override fun createView(ui: AnkoContext<LocalFilmsActivity>) = with(ui) {
        relativeLayout {
            //declaring the ListView
            var filmList: ListView? = null

            //textView displayed when there is no task
            val hintListView = textView("Вы недавно посмотрели...") {
                textSize = 20f
            }.lparams {
                centerInParent()
            }

            fun showHideHintListView(listView: ListView?) {
                if (listView?.adapter?.count ?: 0 > 0)
                    hintListView.visibility = View.GONE
                else
                    hintListView.visibility = View.VISIBLE
            }

            verticalLayout {
                lparams(width = matchParent)
                filmList = listView {
                    adapter = filmAdapter
                    onFocusChange { v, hasFocus ->
                        if (hasFocus)
                            showHideHintListView(filmList)
                    }
                    onItemLongClick { adapterView, view, i, l ->
                        val film = filmAdapter.getItem(i)
                        var options: List<String>? = null
                        if (film.user_mark == 0)
                            options = listOf("Watched", "Delete")
                        else
                            options = listOf("Not watched", "Delete")

                        selector("Film options", options) { DialogInterface, j ->
                            if (j == 1) {
                                filmAdapter.remove(i)
                                saveFilms(ui.owner.application as App, filmAdapter.getAll())
                                showHideHintListView(filmList)
                                toast("Film ${film.title} has been deleted.")
                            }
                            else {
                                filmAdapter.swapMark(i)
                                saveFilms(ui.owner.application as App, filmAdapter.getAll())
                                toast("Task ${film.title} has been marked as \"${options[j]}\"")
                            }
                        }
                        true
                    }
                }.lparams {
                    margin = dip(5)
                    width = matchParent
                }
            }

            floatingActionButton {
                imageResource = android.R.drawable.ic_input_add
                onClick {
                    ui.owner.startActivityForResult<MainActivity>(ui.owner.PICK_FILM)
                }
            }.lparams {
                //setting button to bottom right of the screen
                margin = dip(10)
                alignParentBottom()
                alignParentEnd()
                alignParentRight()
                gravity = Gravity.BOTTOM or Gravity.END
            }
        }
    }
}