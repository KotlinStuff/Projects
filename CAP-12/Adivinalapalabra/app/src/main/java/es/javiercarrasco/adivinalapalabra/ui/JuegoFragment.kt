package es.javiercarrasco.adivinalapalabra.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import es.javiercarrasco.adivinalapalabra.R
import es.javiercarrasco.adivinalapalabra.databinding.FragmentJuegoBinding
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import org.xml.sax.SAXException
import java.io.IOException
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

class JuegoFragment : Fragment() {

    private lateinit var binding: FragmentJuegoBinding

    // Palabra actual.
    private var palabra = ""

    // Puntuación actual.
    private var puntuacion = 0

    // Lista de palabras.
    private val listaPalabras: MutableList<String> = ArrayList<String>()

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
        try {
            listaPalabras.clear()

            val factory: DocumentBuilderFactory =
                DocumentBuilderFactory.newInstance()
            val builder: DocumentBuilder = factory.newDocumentBuilder()
            val doc: Document = builder.parse(
                resources.openRawResource(R.raw.palabras)
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