package es.uniovi.pulso.practica10.datos.network


import com.google.gson.annotations.SerializedName
import es.uniovi.pulso.practica10.datos.network.Movie

/*
    Resultados para la página X del listado de películas.
    Importa el atributo results (Listado de películas).
 */
data class MovieListResult(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)