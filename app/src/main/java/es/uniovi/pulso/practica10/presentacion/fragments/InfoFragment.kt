package es.uniovi.pulso.practica10.presentacion.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import coil.load
import es.uniovi.pulso.practica10.R


class InfoFragment : Fragment() {


    companion object {
        const val MOVIE_FECHA_ESTRENO = "FECHA_ESTRENO"
        const val MOVIE_DURACION = "DURACION"
        const val MOVIE_CARATULA = "CARATULA"


        @JvmStatic
        fun newInstance(fechaEstreno: String, duracion: String, caratula : String) =
            InfoFragment().apply {
                arguments = Bundle().apply {
                    putString(MOVIE_FECHA_ESTRENO, fechaEstreno)
                    putString(MOVIE_DURACION, duracion)
                    putString(MOVIE_CARATULA, caratula)
                }
            }
    }


    private var fechaEstreno: String? = null
    private var duracion: String? = null
    private var caratula: String? = null

    private lateinit var tvEstreno : TextView
    private lateinit var tvDuracion : TextView
    private lateinit var ivCaratula : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Si arguments no es null, entonces ->
        arguments?.let {
            fechaEstreno = it.getString(MOVIE_FECHA_ESTRENO)
            duracion = it.getString(MOVIE_DURACION)
            caratula = it.getString(MOVIE_CARATULA)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_movie_info, container, false)

        root.let {
            tvEstreno = it.findViewById(R.id.estreno)
            tvDuracion = it.findViewById(R.id.duracion)
            ivCaratula = it.findViewById(R.id.caratula)
        }

        tvEstreno.text = fechaEstreno.orEmpty()
        tvDuracion.text = duracion.orEmpty()
        ivCaratula.load("https://image.tmdb.org/t/p/original/${caratula}")

        return root

    }


}