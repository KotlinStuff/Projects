package es.javiercarrasco.mydialogs

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class MyDialogFragment : DialogFragment() {
    // Se crea la estructura del diálogo.
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setMessage(R.string.my_first_dialog)
                .setPositiveButton(
                    android.R.string.yes
                ) { dialog, which ->
                    // Acciones si se pulsa SÍ.
                    Log.d("DEBUG", "Acciones si SÍ.")
                    Toast.makeText(
                        it,
                        "Acciones si SÍ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .setNegativeButton(
                    android.R.string.no
                ) { dialog, which ->
                    // Acciones si se pulsa NO.
                    Log.d("DEBUG", "Acciones si NO.")
                    Toast.makeText(
                        it,
                        "Acciones si NO",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            builder.create()
        } ?: throw IllegalStateException("La Activity no puede ser nula")
    }
}