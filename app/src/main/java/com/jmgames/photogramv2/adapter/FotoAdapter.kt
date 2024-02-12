package com.jmgames.photogramv2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jmgames.photogramv2.Foto
import com.jmgames.photogramv2.R

class FotoAdapter(private val fotosLista:List<Foto>, private val onClickListener: (Foto) -> Unit) : RecyclerView.Adapter<FotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FotoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FotoViewHolder(layoutInflater.inflate(R.layout.item_foto,parent,false))
    }
    /**retornamos el tamaño de la lista del provider*/
    override fun getItemCount(): Int = fotosLista.size


    override fun onBindViewHolder(holder: FotoViewHolder, position: Int) {
        val foto = fotosLista[position]
        holder.render(foto) {
            onClickListener(foto)
        }
    }

}