package com.jmgames.photogramv2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jmgames.photogramv2.adapter.FotoAdapter
import com.jmgames.photogramv2.databinding.ActivityMainBinding

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FotoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño del fragmento
        val view = inflater.inflate(R.layout.fragment_foto, container, false)

        // Obtener el RecyclerView del diseño
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerFotos)

        // Configurar el LayoutManager
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        // Configurar el Adapter para el RecyclerView
        val fotoAdapter = FotoAdapter(FotoProvider.listaFotos) { }
        recyclerView.adapter = fotoAdapter

        val btnVolverF = view.findViewById<Button>(R.id.btnVolverF)
        btnVolverF.setOnClickListener {
            findNavController().navigate(R.id.action_fotoFragment_pop)
        }

        return view
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