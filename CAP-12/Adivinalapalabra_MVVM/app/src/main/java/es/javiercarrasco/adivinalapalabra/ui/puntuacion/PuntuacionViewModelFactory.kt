package es.javiercarrasco.adivinalapalabra.ui.puntuacion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PuntuacionViewModelFactory(private val puntuacionFinal: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PuntuacionViewModel::class.java)) {
            return PuntuacionViewModel(puntuacionFinal) as T
        }
        throw IllegalArgumentException("Clase ViewModel desconocida")
    }
}