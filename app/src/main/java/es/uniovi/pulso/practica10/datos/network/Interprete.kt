package es.uniovi.pulso.practica10.datos.network

import com.google.gson.annotations.SerializedName

/*
    Los atributos importantes son:
        -name -> Nombre.
        - character -> Personaje.
        - profilePath -> Ruta parcial a la imagen.

 */
data class Interprete(

    @SerializedName("name")
    val name : String,

    @SerializedName("character")
    val character : String,

    @SerializedName("profile_path")
    val profilePath : String,

    @SerializedName("adult")
    val adult : Boolean,

    @SerializedName("gender")
    val gender : Int,

    @SerializedName("id")
    val id : Int,

    @SerializedName("known_for_department")
    val knownForDepartment : String,

    @SerializedName("original_name")
    val originalName : String,

    @SerializedName("popularity")
    val popularity : Double,



    @SerializedName("cast_id")
    val castId : Int,

    @SerializedName("credit_id")
    val creditId : String,

    @SerializedName("order")
    val order : Int
)
