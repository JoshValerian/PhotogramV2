package com.jmgames.photogramv2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.jmgames.photogramv2.adapter.FotoAdapter2
import com.jmgames.photogramv2.databinding.FragmentFavoritosBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FavoritosFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentFavoritosBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritosBinding.inflate(inflater, container, false)
        val view = binding.root

        // Obtener la lista de fotos favoritas
        val fotosFavoritas = FotoProvider.listaFotos.filter { it.favorito }

        // Obtener el RecyclerView del diseño utilizando View Binding
        val recyclerView: RecyclerView = binding.recyclerFotos

        // Configurar el LayoutManager
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Configurar el Adapter para el RecyclerView con las fotos favoritas
        val fotoAdapter = FotoAdapter2(fotosFavoritas){foto ->  onItemSelected(foto)}
        recyclerView.adapter = fotoAdapter

        return view
    }

    private fun onItemSelected(foto: Foto) {
        // Mostrar Snackbar
        view?.let { Snackbar.make(it, getString(R.string.foto) + " " + foto.autor, Snackbar.LENGTH_SHORT).show() }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoritosFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
