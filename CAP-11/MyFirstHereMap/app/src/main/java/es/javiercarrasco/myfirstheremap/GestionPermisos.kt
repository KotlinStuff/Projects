package es.javiercarrasco.myfirstheremap

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class GestionPermisos(private val activity: Activity) {
    private val PERMISSIONS_REQUEST_CODE = 1234
    private var resultListener: ResultListener? = null

    // Interface para implementar en la clase que los solicite.
    interface ResultListener {
        fun permissionsGranted()
        fun permissionsDenied()
    }

    // Método encargado de solicitar permiso al usuario.
    fun solicitarPermisos(resultListener: ResultListener) {
        this.resultListener = resultListener
        val permisosNoConcedidos = obtenerPermisos()
        if (permisosNoConcedidos.isEmpty()) {
            resultListener.permissionsGranted()
        } else {
            ActivityCompat.requestPermissions(
                activity,
                permisosNoConcedidos,
                PERMISSIONS_REQUEST_CODE
            )
        }
    }

    // Método encargado de recoger los permisos necesarios del manifest.
    private fun obtenerPermisos(): Array<String> {
        // Array para los permisos que deben concederse.
        val permisosList: ArrayList<String> = ArrayList()
        try {
            // Se recoge información del paquete de aplicación instalado,
            // concretamente se recogen los permisos necesarios.
            val packageInfo = activity.packageManager.getPackageInfo(
                activity.packageName, PackageManager.GET_PERMISSIONS
            )

            // Se crea el array de permisos.
            if (packageInfo.requestedPermissions != null) {
                for (permiso in packageInfo.requestedPermissions) {
                    if (ContextCompat.checkSelfPermission(
                            activity, permiso
                        ) != PackageManager.PERMISSION_GRANTED
                    ) permisosList.add(permiso)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return permisosList.toArray(arrayOfNulls(0))
    }

    fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
        if (resultListener == null) {
            // No hay permisos que solicitar.
            return
        }
        if (grantResults.isEmpty()) {
            // Petición cancelada.
            return
        }
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            var todosConcedidos = true
            for (result in grantResults) {
                todosConcedidos = todosConcedidos and (
                        result == PackageManager.PERMISSION_GRANTED
                        )
            }
            if (todosConcedidos) resultListener!!.permissionsGranted()
            else resultListener!!.permissionsDenied()
        }
    }
}