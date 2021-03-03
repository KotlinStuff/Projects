package es.javiercarrasco.myimageviewer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import es.javiercarrasco.myimageviewer.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.io.FileNotFoundException
import java.io.InputStream
import java.lang.Exception
import java.net.URL
import kotlin.coroutines.startCoroutine

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Contiene las URLs de las imágenes a descargar.
    private val urlImagenes = mutableListOf(
        "https://live.staticflickr.com/7801/47562604261_4ff8522918_k.jpg",
        "https://live.staticflickr.com/8345/28338120516_40e0c40f65_k.jpg",
        "https://live.staticflickr.com/7449/28346482855_3d1b745518_k.jpg",
        "https://live.staticflickr.com/3856/14648036959_72973ac0d3_k.jpg",
        "https://live.staticflickr.com/5587/14648212427_b3bf236e4c_k.jpg"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var tarea: Job? = null

        binding.button.setOnClickListener {
            if (binding.button.text == getString(R.string.btn_imageDownloader)) {
                // Se comprueba la existencia de ImageViews, si existen se eliminan.
                if (binding.myLinearLayout.childCount > 3) {
                    binding.myLinearLayout.removeViews(
                        3,
                        binding.myLinearLayout.childCount - 3
                    )
                }

                tarea = descargarImagenes()
            } else {
                tarea?.let {
                    tarea?.cancel()
                    binding.button.text = getString(R.string.btn_imageDownloader)
                    binding.tvInfo.text = getString(R.string.txt_descargaCancelada)
                }
            }
        }
    }

    /**
     * Corrutina encargada de la descarga de las imágenes e inflado de la vista.
     */
    private fun descargarImagenes() = GlobalScope.launch(Dispatchers.Main) {
        binding.button.text = getString(android.R.string.cancel)
        binding.tvInfo.text = getString(R.string.txt_descargando)
        binding.progressBar.progress = 0

        val imagenes = ArrayList<Bitmap>()

        urlImagenes.forEach {
            Log.d("URLs", it)

            // Tarea asíncronta, se descargan las imágenes y se almacenan en un
            // ArrayList<Bitmatp>().
            withContext(Dispatchers.IO) {
                try {
                    val inputStream = URL(it).openStream()
                    imagenes.add(BitmapFactory.decodeStream(inputStream))
                } catch (e: Exception) {
                    Log.e("DOWNLOAD", e.message.toString())
                }
            }
            binding.progressBar.progress = (imagenes.size * 100) / urlImagenes.size
        }

        // Se añaden las imágenes a la vista una vez descargadas.
        imagenes.forEach {
            addImagen(it)
        }

        // Fin de la tarea.
        binding.button.text = getString(R.string.btn_imageDownloader)
        binding.tvInfo.text = getString(
            R.string.txt_descargaCompleta,
            imagenes.size
        )
    }

    /**
     * Método encargado de cargar la imagen en un ImageView
     * e inflar el LinearLayout con el nuevo componente.
     */
    fun addImagen(image: Bitmap) {
        val img = ImageView(this)

        // Se carga la imagen en el ImageView mediante Glide y se ajusta el tamaño.
        Glide.with(this)
            .load(image)
            .override(binding.myLinearLayout.width - 100)
            .into(img)

        img.setPadding(0, 0, 0, 10)

        // Se infla el LinearLayout con una imagen nueva.
        binding.myLinearLayout.addView(img)
    }

}