package es.javiercarrasco.adivinalapalabra.ui.puntuacion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import es.javiercarrasco.adivinalapalabra.databinding.FragmentPuntuacionBinding

class PuntuacionFragment : Fragment() {
    private lateinit var binding: FragmentPuntuacionBinding

    private lateinit var viewModel: PuntuacionViewModel
    private lateinit var viewModelFactory: PuntuacionViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPuntuacionBinding.inflate(inflater, container, false)

        viewModelFactory = PuntuacionViewModelFactory(
            PuntuacionFragmentArgs.fromBundle(
                requireArguments()
            ).puntuacion
        )
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(PuntuacionViewModel::class.java)

        // Observador para la puntuación final.
        viewModel.puntuacion.observe(viewLifecycleOwner, { nuevaPuntuacion ->
            binding.tvPuntuacionFinal.text = nuevaPuntuacion.toString()
        })

        binding.btnJugarAgain.setOnClickListener {
            viewModel.onJugarAgain()
        }

        viewModel.eventoJugarAgain.observe(viewLifecycleOwner, { jugarAgain ->
            if (jugarAgain) {
                findNavController().navigate(
                    PuntuacionFragmentDirections.actionBackToJuego()
                )
                viewModel.onJugarAgainCompletado()
            }
        })

        return binding.root
    }
}