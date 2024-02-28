package com.jmgames.photogramv2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.jmgames.photogramv2.databinding.FragmentUsuarioBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class UsuarioFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentUsuarioBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsuarioBinding.inflate(inflater, container, false)
        val view = binding.root

        val usuario = (activity as? MainActivity)?.username
        binding.tvNUsu.text = usuario

        binding.btnVolverU.setOnClickListener {
            val bundle = Bundle().apply {
                putString("mensaje", usuario)
            }
            findNavController().navigate(R.id.action_usuarioFragment_pop, bundle)
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UsuarioFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
