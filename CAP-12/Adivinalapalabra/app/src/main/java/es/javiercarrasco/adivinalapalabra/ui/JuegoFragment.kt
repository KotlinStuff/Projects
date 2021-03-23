package es.javiercarrasco.adivinalapalabra.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import es.javiercarrasco.adivinalapalabra.R
import es.javiercarrasco.adivinalapalabra.databinding.FragmentJuegoBinding

class JuegoFragment : Fragment() {

    private lateinit var binding: FragmentJuegoBinding

    // Palabra actual.
    private var palabra = ""

    // Puntuación actual.
    private var puntuacion = 0

    // Lista de palabras.
    private lateinit var listaPalabras: MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJuegoBinding.inflate(inflater, container, false)

        iniciaListaPalabras()
        siguientePalabra()

        binding.btnPasar.setOnClickListener { onPasar() }
        binding.btnAcertar.setOnClickListener { onAcierto() }
        binding.btnFin.setOnClickListener { onFin() }

        return binding.root
    }

    // Avanza a la siguiente palabra de la lista.
    private fun siguientePalabra() {
        if (!listaPalabras.isEmpty()) {
            // Selecciona la palabra y la elimina de la lista.
            palabra = listaPalabras.removeAt(0)
        }
        updateSigPalabra()
        updatePuntuacion()
    }

    private fun iniciaListaPalabras() {
        listaPalabras = resources.getStringArray(
            R.array.array_nombres
        ).toMutableList()
        listaPalabras.shuffle()
    }

    private fun onPasar() {
        puntuacion--
        siguientePalabra()
    }

    private fun onAcierto() {
        puntuacion++
        siguientePalabra()
    }

    private fun onFin() {
        findNavController().navigate(
            JuegoFragmentDirections.actionToPuntuacion(puntuacion)
        )
    }

    private fun updateSigPalabra() {
        binding.tvPalabra.text = palabra
    }

    private fun updatePuntuacion() {
        binding.tvPuntuacion.text = getString(
            R.string.lbl_txt_puntuacion, puntuacion
        )
    }
}