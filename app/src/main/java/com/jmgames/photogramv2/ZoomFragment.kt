package com.jmgames.photogramv2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.jmgames.photogramv2.databinding.FragmentZoomBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ZoomFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var autor: String? = null
    private var descripcion: String? = null
    private var foto: String? = null
    private var origen: String = "normal"
    private var favorito: Boolean = false

    private var _binding: FragmentZoomBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentZoomBinding.inflate(inflater, container, false)
        val view = binding.root

        // Recuperar los datos del bundle
        arguments?.let { bundle ->
            autor = bundle.getString("autor")
            descripcion = bundle.getString("descripcion")
            foto = bundle.getString("foto")
            favorito = bundle.getBoolean("favorito") // Obtener el valor de favorito del bundle
            origen = bundle.getString("origen").toString()
        }

        // Actualizar la UI con los datos recuperados
        binding.tv1.text = autor
        binding.tv2.text = descripcion
        Glide.with(this).load(foto).into(binding.ivFoto)

        // Cambiar el icono de favorito según el valor de la propiedad favorito
        val favIconResource = if (favorito) R.drawable.lleno else R.drawable.vacio
        binding.ivFav.setImageResource(favIconResource)

        binding.btnVolverZ.setOnClickListener {
            val message = when (origen) {
                "normal" -> "Ha regresado a descubrir"
                "favoritos" -> "Ha regresado a favoritos"
                else -> ""
            }
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

            val actionId = when (origen) {
                "normal" -> R.id.action_zoomFragment_pop
                "favoritos" -> R.id.action_zoomFragment_pop2
                else -> -1
            }
            if (actionId != -1) {
                findNavController().navigate(actionId)
            }
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ZoomFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}