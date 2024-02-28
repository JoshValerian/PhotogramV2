package com.jmgames.photogramv2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.jmgames.photogramv2.databinding.FragmentMenuBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private var mensaje: String = ""

class MenuFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val view = binding.root

        mensaje = arguments?.getString("mensaje").toString()

        binding.btnCreditos.setOnClickListener {
            val bundle = Bundle().apply {
                putString("mensaje", mensaje)
            }
            findNavController().navigate(R.id.action_menuFragment_to_creditFragment, bundle)
        }

        binding.btnUsuario.setOnClickListener {
            val bundle = Bundle().apply {
                putString("mensaje", mensaje)
            }
            findNavController().navigate(R.id.action_menuFragment_to_usuarioFragment, bundle)
        }

        binding.btnFotos.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_fotoFragment)
        }

        binding.btnSalir.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_pop)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
