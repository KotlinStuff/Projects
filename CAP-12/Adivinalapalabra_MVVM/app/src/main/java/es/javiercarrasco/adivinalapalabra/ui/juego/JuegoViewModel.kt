package es.javiercarrasco.adivinalapalabra.ui.juego

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.javiercarrasco.adivinalapalabra.MainActivity
import es.javiercarrasco.adivinalapalabra.R
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import org.xml.sax.SAXException
import java.io.IOException
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

class JuegoViewModel : ViewModel() {

    // Control del evento de fin de juego.
    private var _eventoFinJuego = MutableLiveData<Boolean>()
    val eventoFinJuego: LiveData<Boolean>
        get() = _eventoFinJuego

    // Palabra actual. Backing.
    private var _palabra = MutableLiveData<String>()
    val palabra: LiveData<String>
        get() = _palabra

    // Puntuación actual. Backing.
    private var _puntuacion = MutableLiveData<Int>()
    val puntuacion: LiveData<Int>
        get() = _puntuacion

    // Lista de palabras.
    private val listaPalabras: MutableList<String> = ArrayList<String>()

    init {
        Log.i("JuegoViewModel", "JuegoViewModel creado!!")

        _palabra.value = ""
        _puntuacion.value = 0

        iniciaListaPalabras()
        siguientePalabra()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("JuegoViewModel", "JuegoViewModel destruido!!")
    }

    // Avanza a la siguiente palabra de la lista.
    private fun siguientePalabra() {
        if (listaPalabras.isEmpty()) {
            onFinJuego()
        } else {
            // Selecciona la palabra y la elimina de la lista.
            _palabra.value = listaPalabras.removeAt(0)
        }
    }

    private fun iniciaListaPalabras() {
        try {
            listaPalabras.clear()

            val factory: DocumentBuilderFactory =
                DocumentBuilderFactory.newInstance()
            val builder: DocumentBuilder = factory.newDocumentBuilder()
            val doc: Document = builder.parse(
                MainActivity.mContext.resources.openRawResource(R.raw.palabras)
            )
            val raiz: Element = doc.getDocumentElement()
            val items: NodeList = raiz.getElementsByTagName("palabra")

            for (indice in 0..items.length - 1) {
                Log.i("ITEMS", items.item(indice).textContent)
                listaPalabras.add(items.item(indice).textContent)
            }

            listaPalabras.shuffle()

        } catch (e: ParserConfigurationException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: SAXException) {
            e.printStackTrace()
        }
    }

    fun onPasar() {
        _puntuacion.value = (_puntuacion.value)?.minus(1)
        siguientePalabra()
    }

    fun onAcierto() {
        _puntuacion.value = (_puntuacion.value)?.plus(1)
        siguientePalabra()
    }

    fun onFinJuego() {
        _eventoFinJuego.value = true
    }

    // Método para restablecer el juego.
    fun onFinJuegoCompletado() {
        _eventoFinJuego.value = false
    }
}