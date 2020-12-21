package es.javiercarrasco.mydialogs

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.mydialogs.databinding.ActivityMainBinding
import es.javiercarrasco.mydialogs.databinding.DialogLayoutBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val namesArray: Array<String> = resources.getStringArray(R.array.array_nombres)

        with(binding) {
            btnWithDialogFragment.setOnClickListener {
                val myDialogFragment = MyDialogFragment()
                myDialogFragment.show(supportFragmentManager, "teGusta")
            }

            btnAlertDialog.setOnClickListener {
                myAlertDialog(
                    "Este es el segundo cuadro de diálogo, " +
                            "se utiliza la clase AlertDialog para mostrarlo."
                )
            }

            btnAlertDialogList.setOnClickListener {
                myAlertDialogList(namesArray)
            }

            btnAlertDialogSinglePersistentList.setOnClickListener {
                myAlertDialogSinglePersistentList(namesArray)
            }

            btnAlertDialogMultiPersistentList.setOnClickListener {
                myAlertDialogMultiPersistentList(namesArray)
            }

            btnCustomAlertDialog.setOnClickListener {
                myCustomAlertDialog()
            }

            btnTimePicker.setOnClickListener {
                val cal = Calendar.getInstance()
                val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                    cal.set(Calendar.HOUR_OF_DAY, hour)
                    cal.set(Calendar.MINUTE, minute)
                    tvTimePicker.text = SimpleDateFormat("HH:mm").format(cal.time)
                }

                // Al estar dentro del with(binding), se debe especificar el contexto
                // con this@MainActivity.
                TimePickerDialog(
                    this@MainActivity,
                    timeSetListener,
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    true
                ).show()
            }

            btnDatePicker.setOnClickListener {
                val cal = Calendar.getInstance()
                val dateSetListener = DatePickerDialog.OnDateSetListener { _, i, i2, i3 ->
                    cal.set(Calendar.YEAR, i)
                    cal.set(Calendar.MONTH, i2)
                    cal.set(Calendar.DAY_OF_MONTH, i3)

                    tvDatePicker.text = "${cal.get(Calendar.DAY_OF_MONTH)}" +
                            "/${cal.get(Calendar.MONTH)}" +
                            "/${cal.get(Calendar.YEAR)}"
                }

                // Al estar dentro del with(binding), se debe especificar el contexto
                // con this@MainActivity.
                DatePickerDialog(
                    this@MainActivity,
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

            btnStartProgressBar.setOnClickListener {
                var progressBarStatus = 0
                var seccion = 0

                progressBar.progress = 0

                // Se inicia el Thread.
                Thread(Runnable { // Se crea un hilo ficticio imitando una tarea.
                    while (progressBarStatus < 100) {
                        // Se actualiza el estado del Progress Bar.
                        progressBarStatus = seccion
                        progressBar.progress = progressBarStatus

                        // Operación que se realizará.
                        try {
                            seccion += 10
                            Thread.sleep(1000)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }

                    // Acciones que se realizarán al finalizar la tarea.
                    this@MainActivity.runOnUiThread {
                        Toast.makeText(
                            this@MainActivity,
                            "Tarea finalizada!!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }).start()
            }
        }
    }

    private fun myCustomAlertDialog() {
        val builder = AlertDialog.Builder(this)

        builder.apply {
            val bindDialogLayout: DialogLayoutBinding = DialogLayoutBinding.inflate(layoutInflater)
            setView(bindDialogLayout.root)

            setPositiveButton(android.R.string.ok) { dialog, _ ->
                // Se accede a los elementos del layout
                // haciendo uso de View Binding.
                val name = bindDialogLayout.username.text
                val pass = bindDialogLayout.password.text
                Toast.makeText(
                    context,
                    "User: $name\nPass: $pass",
                    Toast.LENGTH_SHORT
                ).show()
            }
            setNegativeButton(android.R.string.no) { dialog, _ ->
                Toast.makeText(
                    context,
                    android.R.string.no,
                    Toast.LENGTH_SHORT
                ).show()
                dialog.dismiss()
            }
        }
        builder.show()
    }

    private fun myAlertDialogMultiPersistentList(names: Array<String>) {
        val builder = AlertDialog.Builder(this)
        val selectedItems = ArrayList<Int>()

        builder.apply {
            setTitle("My AlertDialog con lista multiple")
            setMultiChoiceItems(R.array.array_nombres, null) { _, which, isChecked ->
                if (isChecked) {
                    selectedItems.add(which)
                    Log.d("DEBUG", "Checked: " + names[which])
                } else if (selectedItems.contains(which)) {
                    selectedItems.remove(which)
                    Log.d("DEBUG", "UnChecked: " + names[which])
                }
            }
            setPositiveButton(android.R.string.yes) { _, _ ->
                var textToShow = "Checked: "
                if (selectedItems.size > 0) {
                    for (item in selectedItems) {
                        textToShow = textToShow + names[item] + " "
                    }
                } else textToShow = "No items checked!"
                Toast.makeText(
                    context,
                    textToShow,
                    Toast.LENGTH_SHORT
                ).show()
            }
            setNegativeButton(android.R.string.no) { _, _ ->
                Toast.makeText(
                    context,
                    android.R.string.no,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        builder.show()
    }

    private fun myAlertDialogSinglePersistentList(names: Array<String>) {
        val builder = AlertDialog.Builder(this)

        builder.apply {
            setTitle("My AlertDialog con lista simple")
            setSingleChoiceItems(R.array.array_nombres, -1) { _, which ->
                Log.d("DEBUG", names[which])
            }
            setPositiveButton(android.R.string.yes) { dialog, _ ->
                val selectedPosition = (dialog as AlertDialog)
                    .listView.checkedItemPosition
                Toast.makeText(
                    context,
                    names[selectedPosition],
                    Toast.LENGTH_SHORT
                ).show()
            }
            setNegativeButton(android.R.string.no) { _, _ ->
                Toast.makeText(
                    context,
                    android.R.string.no,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        builder.show()
    }

    private fun myAlertDialogList(names: Array<String>) {
        val builder = AlertDialog.Builder(this)

        builder.apply {
            setTitle("My AlertDialog con lista")
            setItems(R.array.array_nombres) { _, which ->
                Toast.makeText(
                    context,
                    names[which],
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        builder.show()
    }

    private val actionButton = { dialog: DialogInterface, which: Int ->
        Toast.makeText(
            this,
            android.R.string.ok,
            Toast.LENGTH_SHORT
        ).show()
        binding.root.setBackgroundColor(Color.GREEN)
    }

    private fun myAlertDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        // Se crea el AlertDialog.
        builder.apply {
            // Se asigna un título.
            setTitle("My AlertDialog!!")

            // Se asgina el cuerpo del mensaje.
            setMessage(message)

            // Se define el comportamiento de los botones.
            setPositiveButton(
                android.R.string.ok,
                DialogInterface.OnClickListener(function = actionButton)
            )

            setNegativeButton(android.R.string.no) { _, _ ->
                Toast.makeText(
                    context,
                    android.R.string.no,
                    Toast.LENGTH_SHORT
                ).show()
                binding.root.setBackgroundColor(Color.RED)
            }

            setNeutralButton("No sé") { _, _ ->
                Toast.makeText(
                    context,
                    "No sé",
                    Toast.LENGTH_SHORT
                ).show()
                binding.root.setBackgroundColor(Color.WHITE)
            }
        }
        // Se muestra el AlertDialog.
        builder.show()
    }
}