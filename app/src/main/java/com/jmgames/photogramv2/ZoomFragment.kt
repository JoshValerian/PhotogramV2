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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.jmgames.photogramv2.adapter.CommentAdapter
import com.jmgames.photogramv2.databinding.FragmentZoomBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

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

    private lateinit var dataStore: DataStore

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentZoomBinding.inflate(inflater, container, false)
        val view = binding.root

        dataStore = (requireActivity() as MainActivity).dataStore

        // Recuperar los datos del bundle
        arguments?.let { bundle ->
            autor = bundle.getString("autor")
            descripcion = bundle.getString("descripcion")
            foto = bundle.getString("foto")
            favorito = bundle.getBoolean("favorito") // Obtener el valor de favorito del bundle
            origen = bundle.getString("origen").toString()
        }

        // Obtener los comentarios del DataStore y mostrarlos en el RecyclerView
        lifecycleScope.launch {
            val comments = foto?.let { dataStore.getCommentsForUser(it).first() }
            if (comments != null) {
                initRecyclerView(comments)
            }
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

        // Manejar la acción de enviar un nuevo comentario
        binding.btnAddComment.setOnClickListener {
            val username = (activity as? MainActivity)?.username
            val comment = binding.etComment.text.toString().trim()
            if (comment.isNotEmpty()) {
                // Guardar el comentario en el DataStore
                lifecycleScope.launch {
                    if (username != null) {
                        foto?.let { it1 -> dataStore.saveCommentForUser(username, it1, comment) }
                    }
                    // Actualizar la lista de comentarios y el RecyclerView
                    val updatedComments = foto?.let { it1 -> dataStore.getCommentsForUser(it1).first() }
                    if (updatedComments != null) {
                        updateRecyclerView(updatedComments)
                    }
                    // Limpiar el campo de texto del comentario
                    binding.etComment.text.clear()
                }
            }
        }

        return view
    }

    private fun displayComments(comments: List<String>) {
        // Limpiar la vista de comentarios
        binding.recyclerComments.removeAllViews()
        // Mostrar cada comentario en la vista
        comments.forEach { comment ->
            val textView = TextView(requireContext()).apply {
                text = comment
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
            binding.recyclerComments.addView(textView)
        }
    }

    private fun initRecyclerView(comments: List<String>) {
        val adapter = CommentAdapter(comments)
        binding.recyclerComments.adapter = adapter
        binding.recyclerComments.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun updateRecyclerView(comments: List<String>) {
        val adapter = binding.recyclerComments.adapter as CommentAdapter
        adapter.comments = comments
        adapter.notifyDataSetChanged()
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