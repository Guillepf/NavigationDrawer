package es.uniovi.pulso.practica10.presentacion

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import es.uniovi.pulso.practica10.R
import es.uniovi.pulso.practica10.datos.database.MovieEntity
import es.uniovi.pulso.practica10.datos.database.MoviesDataBase
import es.uniovi.pulso.practica10.datos.network.Movie
import es.uniovi.pulso.practica10.datos.network.RetrofitServiceFactory
import es.uniovi.pulso.practica10.presentacion.fragments.ArgumentoFragment
import es.uniovi.pulso.practica10.presentacion.fragments.InfoFragment
import es.uniovi.pulso.practica10.presentacion.fragments.RepartoFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ShowMovieActivity : AppCompatActivity() {

    private lateinit var ivFondo : ImageView
    private lateinit var navView : BottomNavigationView
    private val service= RetrofitServiceFactory.makeTheMoviedbApi()
    private lateinit var movie : Movie
    private lateinit var toolBarLayout : CollapsingToolbarLayout
    private lateinit var moviesDatabase : MoviesDataBase

    private var movieId : Int = -1

    //Podemos asegurar que, si la película está en la base de datos. Es favorita.
    //Si no lo está, podemos asegurar que NO es favorita.
    private var esFavorita : Boolean = false

    private lateinit var menu : Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_movie)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolBarLayout = findViewById(R.id.toolbar_layout)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        moviesDatabase = MoviesDataBase.getDB(this)

        ivFondo = findViewById(R.id.ivFondo)
        navView = findViewById(R.id.nav_view)

        val intentMovie = intent
        movieId = intentMovie.getIntExtra("MOVIE_ID", -1)

        lifecycleScope.launch(Dispatchers.IO) {
            val movie = moviesDatabase.movieDao().findById(movieId)

            // Coge el ámbito del padre
            withContext(Dispatchers.Main){
                if(movie != null){
                    esFavorita = true
                }

                cargarMenu()
                cargarMovie(movieId)
            }
        }




    }

    private fun toggleCorazonMenu() {
        if (esFavorita)
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this@ShowMovieActivity, R.drawable.ic_corazon_on))
        else
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this@ShowMovieActivity, R.drawable.ic_corazon_off))
    }


    private fun cargarMenu() {
        //Esto es el listener. Recuerda, el when es similar al switch.
        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_argumento -> {
                    mostrarArgumento()
                }
                R.id.navigation_info -> {
                    mostrarInfo()
                }
                R.id.navigation_reparto -> {
                    mostrarReparto()
                }
            }
            true
        }
    }

    private fun cargarMovie(movieId : Int) {
        //Cargamos los datos de una película mediante un servicio web (usando retrofit).
        lifecycleScope.launch(Dispatchers.IO) {
            movie = RetrofitServiceFactory.makeTheMoviedbApi().getMovie(movieId, "311767f19cd65545829960163781cd4c", "es-ES")

            // Coge el ámbito del padre
            withContext(Dispatchers.Main){
                toolBarLayout.title = movie.title


                ivFondo.load("${"https://image.tmdb.org/t/p/original/"}${movie.backdropPath}") {
                    crossfade(true)
                    crossfade(500)
                }

                mostrarInfo()
            }
        }

        //EJERCICIO: carga del servicio la info de la película y guárdala en el atributo movie:
        //  La id de la película la estás recibiendo.
        //  apiKey: "6bc4475805ebbc4296bcfa515aa8df08"
        // lang: "es-ES"
        //
        //Debes cambiar el título del toolbarLayout.
        //La imagen de fondo (atributo: backdropPath) tiene como url inicial: https://image.tmdb.org/t/p/original/


       //Cuando se obtenga la película, se llama a este método para mostrar el fragment por defecto.

    }

    private fun mostrarInfo() {
        val infoFragment = InfoFragment.newInstance(movie.releaseDate,"",movie.posterPath)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, infoFragment)
            .commit()
    }

    private fun mostrarReparto() {
        val repartoFragment = RepartoFragment.newInstance(movie.id)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, repartoFragment)
            .commit()
    }

    private fun mostrarArgumento() {
        val argumentoFragment = ArgumentoFragment.newInstance(movie.overview)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, argumentoFragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.menu_show_movie, menu)
        toggleCorazonMenu()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.movie_fav -> {
                esFavorita = !esFavorita

                if(esFavorita)
                    guardarFavorita()
                else
                    borrarFavorita()
                toggleCorazonMenu()

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun borrarFavorita() {
        //Borra la película actual en la base de datos.
        //Debe enviarse una MovieEntity.
        lifecycleScope.launch(Dispatchers.IO) {
            val movieEntity = MovieEntity(movie.id, movie.title, movie.overview, movie.releaseDate, movie.backdropPath, movie.posterPath)


            moviesDatabase.movieDao().delete(movieEntity)
        }
    }
    private fun guardarFavorita() {
        //Guarda la película actual en la base de datos.
        //Debe enviarse una MovieEntity.

        lifecycleScope.launch(Dispatchers.IO) {
            val movieEntity = MovieEntity(movie.id, movie.title, movie.overview, movie.releaseDate, movie.backdropPath, movie.posterPath)


            moviesDatabase.movieDao().add(movieEntity)
        }


    }


}