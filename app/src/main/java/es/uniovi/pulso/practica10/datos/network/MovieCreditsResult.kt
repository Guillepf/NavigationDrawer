package es.uniovi.pulso.practica10.datos.network

import com.google.gson.annotations.SerializedName
import es.uniovi.pulso.practica10.datos.network.CrewMember
import es.uniovi.pulso.practica10.datos.network.Interprete

/*
    Representa el JSON serializado para una determinada película.
 */
data class MovieCreditsResult (
    @SerializedName("id")
    val id : Int,

    @SerializedName("cast")
    val reparto : List<Interprete>,

    @SerializedName("crew")
    val personal : List<CrewMember>
)