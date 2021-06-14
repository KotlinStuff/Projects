package es.javiercarrasco.adivinalapalabra.ui.juego

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import es.javiercarrasco.adivinalapalabra.databinding.FragmentJuegoBinding

class JuegoFragment : Fragment() {

    private lateinit var binding: FragmentJuegoBinding

    private lateinit var viewModel: JuegoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJuegoBinding.inflate(inflater, container, false)

        // Instanciación de viewModel.
        Log.i("JuegoFragment", "Llamada a ViewModelProvider.get()")
        viewModel = ViewModelProvider(this).get(JuegoViewModel::class.java)

        // Observador para el evento fin de juego.
        viewModel.eventoFinJuego.observe(viewLifecycleOwner, { terminado ->
            if (terminado) juegoFinalizado()
        })

        // Se asocia el observer a la puntuación.
        viewModel.puntuacion.observe(viewLifecycleOwner, { nuevaPuntuacion ->
            binding.tvPuntuacion.text = nuevaPuntuacion.toString()
        })

        // Se asocia el observer a la puntuación.
        viewModel.palabra.observe(viewLifecycleOwner, { nuevaPalabra ->
            binding.tvPalabra.text = nuevaPalabra.toString()
        })

        binding.btnPasar.setOnClickListener { onPasar() }
        binding.btnAcertar.setOnClickListener { onAcierto() }
        binding.btnFin.setOnClickListener { onFin() }

        return binding.root
    }

    private fun onPasar() {
        viewModel.onPasar()
    }

    private fun onAcierto() {
        viewModel.onAcierto()
    }

    private fun onFin() {
        juegoFinalizado()
    }

    private fun juegoFinalizado() {
        Toast.makeText(activity, "Juego finalizado!!", Toast.LENGTH_SHORT).show()

        // puntuación se pasa como parámetro del actionToPuntuacion, ya que es un argumento del fragment
        // destino, fíjate en el app_navigation.xml.
//        findNavController().navigate(
//            JuegoFragmentDirections.actionToPuntuacion(
//                viewModel.puntuacion.value ?: 0
//            )
//        )

        val action = JuegoFragmentDirections.actionToPuntuacion(
            viewModel.puntuacion.value ?: 0
        )
        NavHostFragment.findNavController(this).navigate(action)

        viewModel.onFinJuegoCompletado()
    }
}