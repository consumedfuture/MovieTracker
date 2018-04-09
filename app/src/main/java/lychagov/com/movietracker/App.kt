package lychagov.com.movietracker

import android.app.Application
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.migration.Migration

class App : Application() {
    lateinit var database: FilmDatabase

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, FilmDatabase::class.java, "films")
                .addMigrations(MIGRATION_1_2)
                .build()

    }
    companion object {
        @JvmField
        val MIGRATION_1_2 = Migration1To2()
    }
}

class Migration1To2 : Migration(1,2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE FilmInfo "
                + " ADD COLUMN user_mark INTEGER DEFAULT 0 NOT NULL");

    }
}