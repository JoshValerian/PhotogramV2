package com.jmgames.photogramv2

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jmgames.photogramv2.databinding.FragmentLoginBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class LoginFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var dataStore: DataStore

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        // Acceder a la instancia única de DataStore desde la actividad principal
        dataStore = (requireActivity() as MainActivity).dataStore

        binding.btnNext.setOnClickListener {
            val usuario = binding.etUser.text.toString()
            val iniciarSesion = binding.cbLog.isChecked
            val registrar = binding.cbReg.isChecked

            if ((iniciarSesion && registrar) || (!iniciarSesion && !registrar)) {
                // Si ambos están seleccionados o ninguno está seleccionado, mostrar un mensaje de error
                binding.etUser.error = getString(R.string.loginSeleccionarOpcion)
                return@setOnClickListener
            }

            if (usuario.isNotEmpty()) {
                lifecycleScope.launch {
                    val currentUsers = dataStore.userNamesFlow.first()
                    if (iniciarSesion) {
                        if (usuario in currentUsers) {
                            (activity as? MainActivity)?.username = usuario
                            // Verificar si el checkbox está seleccionado para el usuario actual
                            val checkBoxState = dataStore.getCheckBoxStateForUser(usuario)
                            if (checkBoxState != null && checkBoxState) {
                                // El checkbox del usuario está seleccionado, navegar directamente al menú
                                findNavController().navigate(R.id.action_loginFragment_to_menuFragment)
                            } else {
                                // El checkbox del usuario no está seleccionado, navegar al fragmento de información
                                findNavController().navigate(R.id.action_loginFragment_to_informacionFragment)
                            }
                        } else {
                            binding.etUser.error = getString(R.string.loginUsuarioIncorrecto)
                        }
                    } else if (registrar) {
                        if (usuario !in currentUsers) {
                            dataStore.saveUserName(usuario)
                            (activity as? MainActivity)?.username = usuario
                            findNavController().navigate(R.id.action_loginFragment_to_informacionFragment)

                        } else {
                            binding.etUser.error = getString(R.string.registrarUsuarioIcorrecto)
                        }
                    } else {
                        binding.etUser.error = getString(R.string.loginSeleccionarOpcion)
                    }
                }
            } else {
                binding.etUser.error = getString(R.string.loginEr)
            }
        }




        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
