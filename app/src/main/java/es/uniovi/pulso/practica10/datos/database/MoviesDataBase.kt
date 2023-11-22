package es.uniovi.pulso.practica10.datos.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.uniovi.pulso.practica10.presentacion.populares.PopularesFragment

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MoviesDataBase : RoomDatabase() {

    abstract fun movieDao() : MovieDao

    companion object
    {
        @Volatile
        private var INSTANCE : MoviesDataBase? = null

        fun getDB(context: Context?) : MoviesDataBase {

            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(context!!.applicationContext, MoviesDataBase::class.java, "moviesdb")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}