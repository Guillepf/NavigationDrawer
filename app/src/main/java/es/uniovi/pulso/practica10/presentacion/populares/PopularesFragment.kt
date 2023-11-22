package es.uniovi.pulso.practica10.presentacion.populares

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.uniovi.pulso.practica10.R
import es.uniovi.pulso.practica10.datos.database.MoviesDataBase
import es.uniovi.pulso.practica10.datos.network.Movie
import es.uniovi.pulso.practica10.datos.network.RetrofitServiceFactory
import es.uniovi.pulso.practica10.datos.network.TheMoviedbApi
import es.uniovi.pulso.practica10.presentacion.ShowMovieActivity
import es.uniovi.pulso.practica10.presentacion.adapters.MovieAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PopularesFragment : Fragment() {

    private lateinit var recyclerViewMovies : RecyclerView

    private lateinit var movieAdapter : MovieAdapter



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
            val listMovies = RetrofitServiceFactory.makeTheMoviedbApi().listMovies("popular", "311767f19cd65545829960163781cd4c", "es-ES", 1)


                // Coge el ámbito del padre
            withContext(Dispatchers.Main){
                movieAdapter.update(listMovies.results)

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