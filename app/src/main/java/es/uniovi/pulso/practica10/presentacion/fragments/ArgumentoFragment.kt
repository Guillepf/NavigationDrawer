package es.uniovi.pulso.practica10.presentacion.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import es.uniovi.pulso.practica10.R


class ArgumentoFragment : Fragment() {


    companion object {
        const val MOVIE_ARGUMENTO = "ARGUMENTO"

        //Esta anotación indica que tiene que ser sí o sí un método estático.
        //Más info en:https://kotlinlang.org/docs/java-to-kotlin-interop.html#static-methods
        @JvmStatic
        fun newInstance(argumento: String) =
            ArgumentoFragment().apply {
                arguments = Bundle().apply {
                    putString(MOVIE_ARGUMENTO, argumento)
                }
            }
    }


    private var argumento: String? = null

    private lateinit var tvArgumento : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Si arguments no es null, entonces ->
        //Obviamente, podéis hacerlo como siempre hacéis.

        arguments?.let {
            argumento = it.getString(MOVIE_ARGUMENTO)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_movie_argumento, container, false)


        root.let {
            tvArgumento = it.findViewById(R.id.text_argumento)
        }

        tvArgumento.text = argumento.orEmpty()

        return root

    }
}