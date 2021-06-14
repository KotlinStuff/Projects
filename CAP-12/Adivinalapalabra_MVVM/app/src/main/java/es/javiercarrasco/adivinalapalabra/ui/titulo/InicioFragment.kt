package es.javiercarrasco.adivinalapalabra.ui.titulo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.solver.state.State
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import es.javiercarrasco.adivinalapalabra.databinding.FragmentInicioBinding

class InicioFragment : Fragment() {
    private lateinit var binding: FragmentInicioBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInicioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.btnPlay.setOnClickListener {
            findNavController().navigate(
                InicioFragmentDirections.actionToJuego()
            )
        }
    }
}