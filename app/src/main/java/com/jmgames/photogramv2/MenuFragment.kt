package com.jmgames.photogramv2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var mensaje:String? = arguments?.getString("mensaje")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            mensaje = arguments?.getString("mensaje")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        val btnCreditos = view.findViewById<Button>(R.id.btnCreditos)
        btnCreditos.setOnClickListener {
            val bundle = Bundle().apply {
                putString("mensaje", mensaje)
            }
            findNavController().navigate(R.id.action_menuFragment_to_creditFragment, bundle)
        }

        val btnUsuario = view.findViewById<Button>(R.id.btnUsuario)
        btnUsuario.setOnClickListener {
            val bundle = Bundle().apply {
                putString("mensaje", mensaje)
            }
            findNavController().navigate(R.id.action_menuFragment_to_usuarioFragment, bundle)
        }

        val btnFotos = view.findViewById<Button>(R.id.btnFotos)
        btnFotos.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_fotoFragment)
        }

        val btnSalir = view.findViewById<Button>(R.id.btnSalir)
        btnSalir.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_pop)
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}