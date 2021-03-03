package es.javiercarrasco.mycoroutines

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import es.javiercarrasco.mycoroutines.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCancelarUno.isEnabled = false
        binding.btnCancelarDos.isEnabled = false

        // Las corrutinas devuelven un objeto Job al crearse.
        var task1: Job? = null
        binding.btnCoroutineUno.setOnClickListener {
            task1 = makeTask(
                10,
                binding.btnCoroutineUno,
                binding.btnCancelarUno,
                binding.progressBarUno
            )
        }

        binding.btnCancelarUno.setOnClickListener {
            // LET ejecutará el contenido si task1 existe y/o no es nulo.
            task1.let {
                task1?.cancel().apply {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.btn_coroutineUno) + " cancelada!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                binding.progressBarUno.progress = 0
                binding.btnCancelarUno.isEnabled = false
                binding.btnCoroutineUno.isEnabled = true
            }
        }

        var task2: Job? = null
        binding.btnCoroutineDos.setOnClickListener {
            task2 = makeTask(
                20,
                binding.btnCoroutineDos,
                binding.btnCancelarDos,
                binding.progressBarDos
            )
        }

        binding.btnCancelarDos.setOnClickListener {
            task2.let {
                task2?.cancel().apply {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.btn_coroutineDos) + " cancelada!!",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                binding.progressBarDos.progress = 0
                binding.btnCancelarDos.isEnabled = false
                binding.btnCoroutineDos.isEnabled = true
            }
        }
    }

    /**
     * Método encargado de crear una corrutina simulando una tarea.
     */
    private fun makeTask(
        duracion: Int, btnStart: Button,
        btnCancel: Button, progressBar: ProgressBar
    ) = GlobalScope.launch(Dispatchers.Main) {
        // Preparación de la corrutina.
        btnStart.isEnabled = false
        btnCancel.isEnabled = true
        progressBar.progress = 0

        // Tarea principal.
        withContext(Dispatchers.IO) {
            var contador = 0
            while (contador < duracion) {
                if(task((duracion * 50).toLong())) {
                    contador++
                    progressBar.progress = (contador * 100) / duracion
                }
            }
        }

        // Finaliza la corrutina.
        btnStart.isEnabled = true
        btnCancel.isEnabled = false
        progressBar.progress = 0
        Toast.makeText(
            this@MainActivity,
            "${btnStart.text} finalizada!!",
            Toast.LENGTH_SHORT
        ).show()
    }

    /**
     * Función de suspensión que detiene la ejecución de la corrutina
     * hasta que finaliza.
     */
    suspend fun task(duracion: Long): Boolean {
        Log.d("SUSPEND FUN", "Simulando una tarea!")
        delay(duracion)
        return true
    }
}