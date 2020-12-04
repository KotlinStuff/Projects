package es.javiercarrasco.usodeficheros

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.usodeficheros.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Comprueba si ya existe el fichero, si existe
        // se muestra su contenido al abrir la app.
        readFile()

        binding.btnSaveFileAppend.setOnClickListener {
            if (binding.etDatos.text.isNotEmpty()) {
                // Se separan las líneas del EditText para escribirlas
                // una a una, en realidad no sería necesario.
                val lineas: List<String> = binding.etDatos.text.split("\n")
                if (lineas.size > 0) {
                    writeFile(lineas, false)
                }
                binding.etDatos.text.clear()
                readFile()
            } else {
                Toast.makeText(
                    this,
                    R.string.msg_warning1,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.btnSaveFileNew.setOnClickListener {
            if (binding.etDatos.text.isNotEmpty()) {
                val lineas: List<String> = binding.etDatos.text.split("\n")
                if (lineas.size > 0) {
                    writeFile(lineas, true)
                }
                binding.etDatos.text.clear()
                readFile()
            } else {
                Toast.makeText(
                    this,
                    R.string.msg_warning1,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    /**
     * Muestra el contenido del fichero de texto.
     */
    fun readFile() {
        // Se comprueba si existe el fichero.
        if (fileList().contains(getString(R.string.filename))) {
            binding.tvShowFile.text = ""
            try {
                val entrada = InputStreamReader(
                    openFileInput(getString(R.string.filename))
                )
                val br = BufferedReader(entrada)
                var linea = br.readLine()

                while (!linea.isNullOrEmpty()) {
                    binding.tvShowFile.append(linea + "\n")
                    linea = br.readLine()
                }

                br.close()
                entrada.close()

            } catch (e: IOException) {
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()

            }
        } else {
            binding.tvShowFile.text = getString(
                R.string.msg_warning2,
                getString(R.string.filename)
            )
        }
    }

    /**
     * Escribe el fichero de texto.
     * @tipo TRUE fichero nuevo, FALSE añade.
     */
    fun writeFile(datos: List<String>, tipo: Boolean) {
        try {
            val salida: OutputStreamWriter
            if (tipo) {
                // Si el fichero no existe se crea,
                // si existe se sobrescribe.
                salida = OutputStreamWriter(
                    openFileOutput(
                        getString(R.string.filename),
                        Activity.MODE_PRIVATE
                    )
                )
            } else {
                // Si el fichero no existe se crea,
                // si existe se añade la información.
                salida = OutputStreamWriter(
                    openFileOutput(
                        getString(R.string.filename),
                        Activity.MODE_APPEND
                    )
                )
            }

            // Se escribe en el fichero línea a línea.
            for (d in datos) {
                salida.write(d + '\n')
            }

            // Se confirma la escritura.
            salida.flush()
            salida.close()

            Toast.makeText(
                this,
                getString(R.string.msg_correct),
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: IOException) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }
}