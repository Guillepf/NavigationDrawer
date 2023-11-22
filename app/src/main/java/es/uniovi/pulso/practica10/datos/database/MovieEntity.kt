package es.uniovi.pulso.practica10.datos.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="movies")
data class MovieEntity (

    @PrimaryKey(autoGenerate = false)
    val id :Int,

    val titulo : String,

    val argumento : String,

    val fechaEstreno : String,

    @ColumnInfo(name = "url_caratula")
    val urlCaratula: String,


    @ColumnInfo(name = "url_fondo")
    val urlFondo: String,

)