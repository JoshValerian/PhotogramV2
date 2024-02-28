package com.jmgames.photogramv2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.jmgames.photogramv2.adapter.FotoAdapter
import com.jmgames.photogramv2.databinding.FragmentFotoBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FotoFragment : Fragment() {
    private var _binding: FragmentFotoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFotoBinding.inflate(inflater, container, false)
        val view = binding.root

        // Configurar el RecyclerView
        binding.recyclerFotos.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerFotos.adapter = FotoAdapter(FotoProvider.listaFotos) { foto ->  onItemSelected(foto)}

        // Manejar el clic del botón
        binding.btnVolverF.setOnClickListener {
            findNavController().navigate(R.id.action_fotoFragment_pop)
        }

        return view
    }

    private fun onItemSelected(foto: Foto) {
        // Mostrar Snackbar
        view?.let { Snackbar.make(it, getString(R.string.foto) + " " + foto.autor, Snackbar.LENGTH_SHORT).show() }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FotoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
