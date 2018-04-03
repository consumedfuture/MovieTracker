package lychagov.com.movietracker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import org.jetbrains.anko.*
import org.jetbrains.anko.design.*

class LocalFilmsActivity : AppCompatActivity(){
    val filmList = Films()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var adapter=LocalFilmsAdapter(filmList)
        LocalFilmsActivityUI(adapter).setContentView(this)

    }
}

class LocalFilmsAdapter(
        val filmList: Films
): BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCount(): Int {
        return filmList.size
    }

    override fun getItem(position: Int): Any {
        return filmList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0L
    }

    fun add(film: FilmInfo){
        filmList.add(film)
        notifyDataSetChanged()
    }

    fun remove(i: Int){
        filmList.removeAt(i)
        notifyDataSetChanged()
    }

}

class LocalFilmsActivityUI(
        adapter: LocalFilmsAdapter
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

            floatingActionButton {
                imageResource = android.R.drawable.ic_input_add
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