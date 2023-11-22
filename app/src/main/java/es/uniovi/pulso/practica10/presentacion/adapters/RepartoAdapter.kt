package es.uniovi.pulso.practica10.presentacion.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import es.uniovi.pulso.practica10.R
import es.uniovi.pulso.practica10.datos.network.Interprete

/**
 * Fíjate que en este caso, no pasamos un listener como hacíamos en MovieAdapter.
 * Simplemente no hacemos nada cuando se hace una selección.
 */
class RepartoAdapter(
    private var reparto: List<Interprete> = emptyList(),

    ) : RecyclerView.Adapter<RepartoAdapter.RepartoViewHolder>() {


    private lateinit var  context : Context


    class RepartoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imgInterprete = itemView.findViewById<ImageView>(R.id.imagen_interprete)
        private val tvInterprete = itemView.findViewById<TextView>(R.id.nombre_interprete);
        private val tvPersonaje = itemView.findViewById<TextView>(R.id.nombre_personaje);


        fun bindView(interprete: Interprete) {

            val baseURL = "https://image.tmdb.org/t/p/original/"
            imgInterprete.load("${baseURL}${interprete.profilePath}") {
                crossfade(true)
                crossfade(500)
            }
            tvInterprete.text = interprete.name
            tvPersonaje.text = interprete.character

        }
    }


    /*
     *  Notificación de cambios.
     */
    fun update(reparto : List<Interprete>) {
        this.reparto = reparto
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepartoViewHolder {

        context = parent.context

        return RepartoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_reparto, parent, false)
        )
    }

    override fun getItemCount(): Int = reparto.size

    override fun onBindViewHolder(holder: RepartoViewHolder, position: Int) =
        holder.bindView(reparto[position])

}