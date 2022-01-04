package es.javiercarrasco.captureandsave.utils

import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import es.javiercarrasco.captureandsave.R

class ManagePermissions(
    val activity: Activity,
    val permission: String,
    val code: Int
) {

    fun checkPermissions(): Boolean {
        // Se comprueba si el permiso en cuestión está concedido.
        if (ContextCompat.checkSelfPermission(
                activity,
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) { // Si no está concedido el permiso se entra.
            Log.d("DEBUG", "No tienes permiso para esta acción: $permission")

            // Si el usuario ya lo ha rechazado al menos una vez (TRUE),
            // se puede mostrar una explicación.
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                Log.d("DEBUG", "Se da una explicación")
                showPermissionAlert()
            } else {
                // No requiere explicación, se pregunta por el permiso.
                Log.d("DEBUG", "No se da una explicación.")
                ActivityCompat.requestPermissions(
                    activity, arrayOf(permission), code
                )
            }
        } else Log.d("DEBUG", "Permiso ($permission) concedido!")

        return ContextCompat.checkSelfPermission(
            activity, permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    // Dialog encargado de mostrar información adicional para los permisos.
    fun showPermissionAlert() {
        AlertDialog.Builder(activity).apply {
            setTitle(R.string.dialog_permissions_title)
            setMessage(R.string.dialog_permissions_text)
            setPositiveButton(android.R.string.ok, null)
        }.show()
    }
}