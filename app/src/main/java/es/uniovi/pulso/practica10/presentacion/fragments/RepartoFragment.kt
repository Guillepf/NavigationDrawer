package es.uniovi.pulso.practica10.presentacion.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.uniovi.pulso.practica10.R
import es.uniovi.pulso.practica10.presentacion.adapters.RepartoAdapter
import es.uniovi.pulso.practica10.datos.network.RetrofitServiceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RepartoFragment : Fragment() {

    companion object {
        const val MOVIE_ID = "MOVIE_ID"

        @JvmStatic
        fun newInstance(movieId: Int) =
            RepartoFragment().apply {
                arguments = Bundle().apply {
                    putInt(MOVIE_ID, movieId)
                }
            }
    }


    private var movieId: Int = -1

    private lateinit var recylerInterpretes : RecyclerView
    private lateinit var repartoAdapter : RepartoAdapter

    private val service =  RetrofitServiceFactory.makeTheMoviedbApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Si arguments no es null, entonces ->
        arguments?.let {
            movieId = it.getInt(MOVIE_ID)
        }

        repartoAdapter = RepartoAdapter()

        //Llamada a la corrutina.
        lifecycleScope.launch(Dispatchers.IO) {
            val creditos = service.getMovieCredits(movieId,"6bc4475805ebbc4296bcfa515aa8df08","es-ES")
            withContext(Dispatchers.Main) {
                repartoAdapter.update(creditos.reparto)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_movie_reparto, container, false)

        root.let {
            recylerInterpretes = it.findViewById(R.id.reciclerViewReparto)
        }
        recylerInterpretes.layoutManager = LinearLayoutManager(context)
        recylerInterpretes.setHasFixedSize(true)
        recylerInterpretes.adapter = repartoAdapter

        return root

    }
}