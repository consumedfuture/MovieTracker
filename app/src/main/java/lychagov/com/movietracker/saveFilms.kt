package lychagov.com.movietracker

import android.app.AppOpsManager
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlin.coroutines.experimental.CoroutineContext

fun saveFilms(
        app: App,
        films: List<FilmInfo>,
        coroutineContext: CoroutineContext = CommonPool
): Deferred<Unit> = async(coroutineContext) {
    app.database.filmsDao().insertAll(films)
}

fun addFilm(
        app: App,
        film: FilmInfo,
        coroutineContext: CoroutineContext = CommonPool
): Deferred<Unit> = async(coroutineContext) {
    app.database.filmsDao().insertFilm(film)
}

fun updateFilm(
        app: App,
        film: FilmInfo,
        coroutineContext: CoroutineContext = CommonPool
): Deferred<Unit> = async(coroutineContext) {
    app.database.filmsDao().update(film)
}

fun deleteFilm(
        app: App,
        film: FilmInfo,
        coroutineContext: CoroutineContext = CommonPool
): Deferred<Unit> = async(coroutineContext) {
    app.database.filmsDao().deleteFilm(film)
}