package es.javiercarrasco.adivinalapalabra.ui.puntuacion

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PuntuacionViewModel(puntuacionFinal: Int) : ViewModel() {
    // Puntuación final.
    private val _puntuacion = MutableLiveData<Int>()
    val puntuacion: LiveData<Int>
        get() = _puntuacion

    // Evento jugar otra vez.
    private val _eventoJugarAgain = MutableLiveData<Boolean>()
    val eventoJugarAgain: LiveData<Boolean>
        get() = _eventoJugarAgain

    init {
        Log.i("PuntuacionViewModel", "PuntuacionViewModel creado!!")
        _puntuacion.value = puntuacionFinal
    }

    fun onJugarAgain() {
        _eventoJugarAgain.value = true
    }

    fun onJugarAgainCompletado() {
        _eventoJugarAgain.value = false
    }
}