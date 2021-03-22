package es.javiercarrasco.adivinalapalabra.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import es.javiercarrasco.adivinalapalabra.databinding.FragmentPuntuacionBinding

class PuntuacionFragment : Fragment() {
    private lateinit var binding: FragmentPuntuacionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPuntuacionBinding.inflate(inflater, container, false)

        binding.tvPuntuacionFinal.text =
            PuntuacionFragmentArgs.fromBundle(requireArguments()).puntuacion.toString()

        binding.btnJugarAgain.setOnClickListener {
            findNavController().navigate(PuntuacionFragmentDirections.actionBackToJuego())
        }

        return binding.root
    }
}