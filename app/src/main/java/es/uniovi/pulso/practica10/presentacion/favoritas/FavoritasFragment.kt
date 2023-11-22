package es.uniovi.pulso.practica10.presentacion.favoritas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.uniovi.pulso.practica10.R
import es.uniovi.pulso.practica10.datos.database.MoviesDataBase
import es.uniovi.pulso.practica10.datos.network.Movie
import es.uniovi.pulso.practica10.datos.network.MovieListResult
import es.uniovi.pulso.practica10.datos.network.RetrofitServiceFactory
import es.uniovi.pulso.practica10.datos.network.TheMoviedbApi
import es.uniovi.pulso.practica10.presentacion.ShowMovieActivity
import es.uniovi.pulso.practica10.presentacion.adapters.MovieAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FavoritasFragment : Fragment() {

    private lateinit var recyclerViewMovies : RecyclerView

    private lateinit var movieAdapter : MovieAdapter

    private lateinit var moviesDatabase : MoviesDataBase

    private val service = RetrofitServiceFactory.makeTheMoviedbApi()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val root = inflater.inflate(R.layout.fragment_populares, container, false)

        recyclerViewMovies = root.findViewById(R.id.recyclerMovies)
        recyclerViewMovies.layoutManager = LinearLayoutManager(context)
        recyclerViewMovies.setHasFixedSize(true)
        movieAdapter = MovieAdapter{mostrarMovie(it)}
        recyclerViewMovies.adapter = movieAdapter

        moviesDatabase = MoviesDataBase.getDB(context)


        //Cargamos las películas mediante un servicio web (usando retrofit).
        //EJERCICIO: carga del servicio las películas:
        //  type: "popular"
        //  apiKey: "6bc4475805ebbc4296bcfa515aa8df08"
        //  lang: "es-ES"
        //  page: 1
        //Debes actualizar el adapter con las películas.
        //
        //OPCIONAL: ¿Serías capaz de cargar las páginas 1 y 2 en el mismo Recycler?
        //          De forma adecuada, claro.

        lifecycleScope.launch(Dispatchers.IO) {
            val listMovies = moviesDatabase.movieDao().getAll().map {
                Movie(false, it.urlCaratula, listOf(), it.id, "", "",
                    it.argumento, 0.0, it.urlFondo, it.fechaEstreno, it.titulo,
                    false, 0.0, 0)
            }

            // Coge el ámbito del padre
            withContext(Dispatchers.Main){
                movieAdapter.update(listMovies)

            }
        }

        return root
    }

    private fun mostrarMovie(movie : Movie) {
        val intent = Intent(context, ShowMovieActivity::class.java)
        intent.putExtra("MOVIE_ID", movie.id);
        startActivity(intent)
    }
}