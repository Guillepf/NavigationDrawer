package es.uniovi.pulso.practica10.datos.network

import com.google.gson.annotations.SerializedName


/**
 * Esta clase representa a una persona del staff de la película.
 * No se utiliza en la práctica pero existe al ser parte de la entidad MovieCreditsResult
 */
data class CrewMember(
    @SerializedName("adult")
    val adult: Boolean,

    @SerializedName("gender")
    val gender: Int,

    @SerializedName("id")
    val id: Int,

    @SerializedName("known_for_department")
    val knownForDepartment: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("original_name")
    val originalName: String,

    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("profile_path")
    val profilePath: String,

    @SerializedName("credit_id")
    val creditId: String,

    @SerializedName("department")
    val department: String,

    @SerializedName("job")
    val job: String
)
