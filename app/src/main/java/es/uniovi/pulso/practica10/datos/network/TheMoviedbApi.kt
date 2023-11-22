package es.uniovi.pulso.practica10.datos.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMoviedbApi {

    // https://api.themoviedb.org/3/movie/popular?api_key={codigo}&language=es-ES&page=1
    @GET("movie/{type}")
    suspend fun listMovies(
        @Path("type") type: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): MovieListResult



    // https://api.themoviedb.org/3/movie/{id}?api_key={código}&language=es-ES&append_to_response=videos
    @GET("movie/{id}")
    suspend fun getMovie(
        @Path("id") type : Int,
        @Query("api_key") apiKey : String,
        @Query("language") language: String
    ) : Movie


    /**
     * Devuelve el cast de una película (Entidad: MovieCreditsResult), que está compuesto por:
     * - Una lista de intérpetes (reparto: List<Interprete>)
     * - Una lista de personal (personal: List<CrewMember>)
     *
     * https://api.themoviedb.org/3/movie/{id}/credits?api_key={código}&language=es-ES
     */
    @GET("movie/{id}/credits")
    suspend fun getMovieCredits(
        @Path("id") type : Int,
        @Query("api_key") apiKey : String,
        @Query("language") language: String
    ): MovieCreditsResult
}

object RetrofitServiceFactory {

       fun makeTheMoviedbApi(): TheMoviedbApi {
        //Se crea el interceptor
        // Esto os permite ver el log de peticiones en el logcat.
        val logging  = HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        var httpClient : OkHttpClient.Builder = OkHttpClient.Builder();
        //Se establece el interceptor en el cliente HTTP
        httpClient.addInterceptor(logging)

        //Se añade una línea al builder para el logg:
            //.client(httpClient.build())
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(TheMoviedbApi::class.java)
    }
}