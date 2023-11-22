package es.uniovi.pulso.practica10.datos.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAll(): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE id = (:movieId)")
    fun findById(movieId: Int): MovieEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(movie: MovieEntity)

    @Delete
    fun delete(movie : MovieEntity)
}