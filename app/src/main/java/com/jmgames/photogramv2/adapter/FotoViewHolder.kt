package com.jmgames.photogramv2.adapter

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jmgames.photogramv2.Foto
import com.jmgames.photogramv2.R
import com.jmgames.photogramv2.databinding.ItemFotoBinding

class FotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = ItemFotoBinding.bind(view)

    fun render(fotoModel: Foto, onClickListener: (Foto) -> Unit){
        binding.tv1.text = fotoModel.autor
        binding.tv2.text = fotoModel.descripcion
        Glide.with(binding.ivFoto.context).load(fotoModel.foto).into(binding.ivFoto)

        // Cambiar la imagen de ivFav según el estado de favorito
        val favIconResource = if (fotoModel.favorito) R.drawable.lleno else R.drawable.vacio
        binding.ivFav.setImageResource(favIconResource)

        // Establecer el clic en ivFav
        binding.ivFav.setOnClickListener {
            // Cambiar el estado de favorito de la foto
            fotoModel.favorito = !fotoModel.favorito
            // Cambiar la imagen de ivFav según el estado de favorito
            val newFavIconResource = if (fotoModel.favorito) R.drawable.lleno else R.drawable.vacio
            // Actualizar la imagen de ivFav
            binding.ivFav.setImageResource(newFavIconResource)

            // Notificar al adaptador que los datos han cambiado
            onClickListener(fotoModel)
        }

        binding.ivFoto.setOnClickListener {
            // Obtener el NavController
            val navController = Navigation.findNavController(binding.root)

            val bundle = Bundle().apply {
                putString("autor", fotoModel.autor)
                putString("descripcion", fotoModel.descripcion)
                putString("foto", fotoModel.foto)
            }

            // Navegar al fragmento de destino cuando se haga clic en la imagen
            navController.navigate(R.id.action_fotoFragment_to_zoomFragment3, bundle)
        }


    }
}
